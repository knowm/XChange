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
package com.xeiam.xchange.bter.dto.marketdata;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.bter.dto.marketdata.BTERCurrencyPairs.BTERCurrencyPairsDeserializer;
import com.xeiam.xchange.currency.CurrencyPair;

@JsonDeserialize(using = BTERCurrencyPairsDeserializer.class)
public class BTERCurrencyPairs {

  private final Set<CurrencyPair> pairs;

  private BTERCurrencyPairs(Set<CurrencyPair> pairs) {

    this.pairs = pairs;
  }
  
  public Collection<CurrencyPair> getPairs() {

    return pairs;
  }

  @Override
  public String toString() {

    return "BTERCurrencyPairs [pairs=" + pairs + "]";
  }

  static class BTERCurrencyPairsDeserializer extends JsonDeserializer<BTERCurrencyPairs> {

    @Override
    public BTERCurrencyPairs deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final Set<CurrencyPair> pairs = new HashSet<CurrencyPair>();
      final ObjectCodec oc = jp.getCodec();
      final JsonNode node = oc.readTree(jp);
      if (node.isArray()) {
        for (JsonNode pairNode : node) {
          final String[] currencies = pairNode.asText().toUpperCase().split("_");
          final CurrencyPair pair = new CurrencyPair(currencies[0], currencies[1]);
          pairs.add(pair);
        }
      }
      return new BTERCurrencyPairs(pairs);
    }
  }
}
