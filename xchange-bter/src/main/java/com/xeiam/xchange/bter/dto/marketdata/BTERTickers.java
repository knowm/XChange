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
package com.xeiam.xchange.bter.dto.marketdata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.bter.BTERAdapters;
import com.xeiam.xchange.bter.dto.BTERBaseResponse;
import com.xeiam.xchange.bter.dto.marketdata.BTERTicker.BTERTickerTickerDeserializer;
import com.xeiam.xchange.bter.dto.marketdata.BTERTickers.BTERTickersDeserializer;
import com.xeiam.xchange.currency.CurrencyPair;

@JsonDeserialize(using = BTERTickersDeserializer.class)
public class BTERTickers extends BTERBaseResponse {

  private final Map<CurrencyPair, BTERTicker> tickerMap;

  private BTERTickers(final Map<CurrencyPair, BTERTicker> tickerMap, boolean result, String message) {

    super(result, message);
    this.tickerMap = tickerMap;
  }

  public Map<CurrencyPair, BTERTicker> getTickerMap() {

    return tickerMap;
  }

  @Override
  public String toString() {

    return "BTERTickers [tickerMap=" + tickerMap + "]";
  }

  static class BTERTickersDeserializer extends JsonDeserializer<BTERTickers> {

    @Override
    public BTERTickers deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      Map<CurrencyPair, BTERTicker> tickerMap = new HashMap<CurrencyPair, BTERTicker>();
      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      if (node.isObject()) {

        Iterator<Entry<String, JsonNode>> tickerEntryIter = node.fields();
        while (tickerEntryIter.hasNext()) {
          Entry<String, JsonNode> tickerEntryNode = tickerEntryIter.next();

          String pairString = tickerEntryNode.getKey();
          CurrencyPair pair = BTERAdapters.adaptCurrencyPair(pairString);

          JsonNode tickerNode = tickerEntryNode.getValue();
          BTERTicker ticker = BTERTickerTickerDeserializer.deserializeFromNode(tickerNode);

          tickerMap.put(pair, ticker);
        }
      }

      boolean result = true;
      String message = "";
      JsonNode resultNode = node.path("result");
      if (resultNode.isBoolean()) {
        result = resultNode.asBoolean();
        message = node.path("message").asText();
      }
      return new BTERTickers(tickerMap, result, message);
    }
  }
}
