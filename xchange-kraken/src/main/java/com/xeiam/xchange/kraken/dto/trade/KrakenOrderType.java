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
package com.xeiam.xchange.kraken.dto.trade;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderType.KrakenOrderTypeDeserializer;

@JsonDeserialize(using = KrakenOrderTypeDeserializer.class)
public enum KrakenOrderType {

  MARKET, LIMIT, STOP_LOSS, TAKE_PROFIT, STOP_LOSS_PROFIT, STOP_LOSS_PROFIT_LIMIT, STOP_LOSS_LIMIT, TAKE_PROFIT_LIMIT, TRAILING_STOP, TRAILING_STOP_LIMIT, STOP_LOSS_AND_LIMIT;

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  public static KrakenOrderType fromString(final String orderTypeString) {

    return fromString.get(orderTypeString.replace('-', '_').toLowerCase());
  }

  private static final Map<String, KrakenOrderType> fromString = new HashMap<String, KrakenOrderType>();
  static {
    for (KrakenOrderType orderType : values())
      fromString.put(orderType.toString(), orderType);

    fromString.put("l", LIMIT);
    fromString.put("m", MARKET);
  }

  static class KrakenOrderTypeDeserializer extends JsonDeserializer<KrakenOrderType> {

    @Override
    public KrakenOrderType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String orderTypeString = node.textValue();
      return fromString(orderTypeString);
    }
  }
}