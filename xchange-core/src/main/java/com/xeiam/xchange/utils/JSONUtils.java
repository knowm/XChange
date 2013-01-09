/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.utils;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeException;

/**
 * Central place for common JSON operations
 */
public class JSONUtils {

  private static final Logger log = LoggerFactory.getLogger(JSONUtils.class);

  /**
   * Creates a POJO from a Jackson-annotated class given a jsonString
   * 
   * @param jsonString
   * @param returnType
   * @param objectMapper
   * @return TODO Refactor Change Signature from String to byte[]
   */
  public static <T> T getJsonObject(String jsonString, Class<T> returnType, ObjectMapper objectMapper) {

    Assert.notNull(jsonString, "jsonString cannot be null");
    Assert.notNull(objectMapper, "objectMapper cannot be null");
    try {
      return objectMapper.readValue(jsonString, returnType);
    } catch (IOException e) {
      // Rethrow as runtime exception
      log.error("Error unmarshalling from json: " + jsonString);
      throw new ExchangeException("Problem getting JSON object", e);
    }
  }

  /**
   * Get a generic map holding the raw data from the JSON string to allow manual type differentiation TODO Refactor Change Signature from String to byte[]
   * 
   * @param jsonString The JSON string
   * @param objectMapper The object mapper
   * @return The map
   */
  public static Map<String, Object> getJsonGenericMap(String jsonString, ObjectMapper objectMapper) {

    Assert.notNull(jsonString, "jsonString cannot be null");
    Assert.notNull(objectMapper, "objectMapper cannot be null");
    try {
      return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
      });
    } catch (IOException e) {
      // Rethrow as runtime exception
      throw new ExchangeException("Problem getting JSON generic map", e);
    }
  }

  /**
   * Given any object return the JSON String representation of it
   * 
   * @param object
   * @param objectMapper
   * @return TODO Refactor Change Signature to return byte[]
   */
  public static String getJSONString(Object object, ObjectMapper objectMapper) {

    Assert.notNull(object, "object cannot be null");
    Assert.notNull(objectMapper, "objectMapper cannot be null");
    try {
      return objectMapper.writeValueAsString(object);
    } catch (IOException e) {
      // Rethrow as runtime exception
      throw new ExchangeException("Problem getting JSON String", e);
    }
  }

}
