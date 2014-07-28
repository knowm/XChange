/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.bitstamp.dto.account;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.bitstamp.dto.BitstampBaseResponse;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBooleanResponse.BitstampBooleanResponseDeserializer;

/**
 * @author gnandiga
 */
@JsonDeserialize(using = BitstampBooleanResponseDeserializer.class)
public class BitstampBooleanResponse extends BitstampBaseResponse {

  private final boolean response;

  /**
   * Constructor
   * 
   * @param error
   * @param response
   */
  public BitstampBooleanResponse(String error, boolean response) {

    super(error);
    this.response = response;
  }

  public boolean getResponse() {

    return response;
  }

  @Override
  public String toString() {

    return "BitstampWithdrawResponse [withdrawResult=" + response + "]";
  }

  static class BitstampBooleanResponseDeserializer extends JsonDeserializer<BitstampBooleanResponse> {

    @Override
    public BitstampBooleanResponse deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.isBoolean()) {
        return new BitstampBooleanResponse(null, node.asBoolean());
      }
      else if (node.get("error") != null) {
        return new BitstampBooleanResponse(node.path("error").asText(), false);
      }
      return new BitstampBooleanResponse("Invalid response from Bitstamp Server.", false);
    }
  }

}
