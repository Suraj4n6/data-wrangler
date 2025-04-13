/*
 * Copyright © 2025 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 */

 package io.cdap.wrangler.directives.custom;

 import io.cdap.wrangler.api.Arguments;
 import io.cdap.wrangler.api.Directive;
 import io.cdap.wrangler.api.ExecutorContext;
 import io.cdap.wrangler.api.Row;
 import io.cdap.wrangler.api.UsageDefinition;
 import io.cdap.wrangler.api.parser.ByteSize;
 import io.cdap.wrangler.api.parser.ColumnName;
 import io.cdap.wrangler.api.parser.TimeDuration;
 import io.cdap.wrangler.api.parser.TokenType;
 
 import java.util.ArrayList;
 import java.util.List;
 
 /**
  * A custom directive that aggregates byte size and time duration columns.
  */
 public class AggregateStats implements Directive {
   private String sizeColumn;
   private String timeColumn;
   private String outputSizeColumn;
   private String outputTimeColumn;
 
   private long totalBytes = 0;
   private long totalMillis = 0;
 
   @Override
   public UsageDefinition define() {
     return UsageDefinition.builder()
       .define("sizeColumn", TokenType.COLUMN_NAME)
       .define("timeColumn", TokenType.COLUMN_NAME)
       .define("outputSizeColumn", TokenType.COLUMN_NAME)
       .define("outputTimeColumn", TokenType.COLUMN_NAME)
       .build();
   }
 
   @Override
   public void initialize(Arguments args) {
     sizeColumn = ((ColumnName) args.value("sizeColumn")).value();
     timeColumn = ((ColumnName) args.value("timeColumn")).value();
     outputSizeColumn = ((ColumnName) args.value("outputSizeColumn")).value();
     outputTimeColumn = ((ColumnName) args.value("outputTimeColumn")).value();
   }
 
   @Override
   public List<Row> execute(List<Row> rows, ExecutorContext context) {
     for (Row row : rows) {
       String sizeStr = row.getValue(sizeColumn).toString();
       String timeStr = row.getValue(timeColumn).toString();
 
       ByteSize byteSize = new ByteSize(sizeStr);
       TimeDuration timeDuration = new TimeDuration(timeStr);
 
       totalBytes += byteSize.getBytes();
       totalMillis += timeDuration.getMilliseconds();
     }
 
     List<Row> output = new ArrayList<>();
     Row result = new Row();
     result.add(outputSizeColumn, totalBytes / (1024.0 * 1024));  // MB
     result.add(outputTimeColumn, totalMillis / 1000.0);          // seconds
     output.add(result);
 
     return output;
   }
 }
 