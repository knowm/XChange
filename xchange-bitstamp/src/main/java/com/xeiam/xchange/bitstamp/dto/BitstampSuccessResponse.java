/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitstamp.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author gnandiga
 */
@JsonDeserialize(using = BitstampSuccessResponse.BitstampSuccessResponseDeserializer.class)
public class BitstampSuccessResponse {

  private final String error;
  private final boolean success;

  /**
   * Constructor
   * 
   * @param error
   * @param result
   */
  public BitstampSuccessResponse(String error, boolean result) {

    this.error = error;
    this.success = result;
  }

  public String getError() {

    return error;
  }

  public boolean getSuccess() {

    return success;
  }

  static class BitstampSuccessResponseDeserializer extends JsonDeserializer<BitstampSuccessResponse> {

    @Override
    public BitstampSuccessResponse deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.isBoolean()) {
        return new BitstampSuccessResponse(null, node.asBoolean());
      }
      else if (node.path("error") != null) {
        return new BitstampSuccessResponse(node.path("error").asText(), false);
      }
      return new BitstampSuccessResponse("Invalid response from Bitstamp Server.", false);
    }
  }

}
