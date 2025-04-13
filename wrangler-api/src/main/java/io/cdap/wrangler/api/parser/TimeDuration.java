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
 * Parses time duration values like "2s", "1500ms" into milliseconds.
 */
 public class TimeDuration implements Token {

   private final long milliseconds;
   private final String raw;
 
   public TimeDuration(String value) {
     this.raw = value;
     this.milliseconds = parse(value.trim().toLowerCase());
   }
 
   private long parse(String value) {
     double number = Double.parseDouble(value.replaceAll("[a-z]+", ""));
     if (value.endsWith("ms")) {
        return (long) number;
      }
      if (value.endsWith("s")) {
        return (long) (number * 1000);
      }
      if (value.endsWith("m")) {
        return (long) (number * 60 * 1000);
      }
      if (value.endsWith("h")) {
        return (long) (number * 60 * 60 * 1000);
      }
      
     throw new IllegalArgumentException("Invalid time duration: " + value);
   }
 
   public long getMilliseconds() {
     return milliseconds;
   }
 
   @Override
   public Object value() {
     return milliseconds;
   }
 
   @Override
   public TokenType type() {
     return TokenType.TIME_DURATION;
   }
 
   @Override
   public JsonElement toJson() {
     JsonObject json = new JsonObject();
     json.addProperty("value", milliseconds);
     json.addProperty("original", raw);
     return json;
   }

 }
