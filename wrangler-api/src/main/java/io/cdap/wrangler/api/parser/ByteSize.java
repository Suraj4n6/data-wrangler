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


package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;



 
/**
 * Parses and converts byte size values like "1MB", "512KB" into raw bytes.
 */
public class ByteSize implements Token {

   private final long bytes;
   private final String raw;
 
   public ByteSize(String value) {
     this.raw = value;
     this.bytes = parse(value.trim().toUpperCase());
   }
 
   private long parse(String value) {
     double number = Double.parseDouble(value.replaceAll("[A-Z]+", ""));
     if (value.endsWith("KB")) {
        return (long) (number * 1024);
      }
      if (value.endsWith("MB")) {
        return (long) (number * 1024 * 1024);
      }
      if (value.endsWith("GB")) {
        return (long) (number * 1024 * 1024 * 1024);
      }
      if (value.endsWith("B")) {
        return (long) number;
      }
      
     throw new IllegalArgumentException("Invalid byte size format: " + value);
   }
 
   public long getBytes() {
     return bytes;
   }
 
   @Override
   public Object value() {
     return bytes;
   }
 
   @Override
   public TokenType type() {
     return TokenType.BYTE_SIZE;
   }
 
   @Override
   public JsonElement toJson() {
     JsonObject json = new JsonObject();
     json.addProperty("value", bytes);
     json.addProperty("original", raw);
     return json;
   }

 }


 

 
