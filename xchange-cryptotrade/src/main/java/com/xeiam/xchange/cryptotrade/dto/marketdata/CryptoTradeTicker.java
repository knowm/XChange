/**
Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do
so, subject to the following conditions:
 *
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
 *
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.xeiam.xchange.cryptotrade.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker.CoibasePriceDeserializer;

@JsonDeserialize(using = CoibasePriceDeserializer.class)
public class CryptoTradeTicker extends CryptoTradeBaseResponse {

  private final BigDecimal last;
  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal volumeTradeCurrency;
  private final BigDecimal volumePriceCurrency;
  private final BigDecimal minAsk;
  private final BigDecimal maxBid;

  private CryptoTradeTicker(BigDecimal last, BigDecimal low, BigDecimal high, BigDecimal volumeTradeCurrency, BigDecimal volumePriceCurrency, BigDecimal minAsk, BigDecimal maxBid, String status,
      String error) {

    super(status, error);
    this.last = last;
    this.low = low;
    this.high = high;
    this.volumeTradeCurrency = volumeTradeCurrency;
    this.volumePriceCurrency = volumePriceCurrency;
    this.minAsk = minAsk;
    this.maxBid = maxBid;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getVolumeTradeCurrency() {

    return volumeTradeCurrency;
  }

  public BigDecimal getVolumePriceCurrency() {

    return volumePriceCurrency;
  }

  public BigDecimal getMinAsk() {

    return minAsk;
  }

  public BigDecimal getMaxBid() {

    return maxBid;
  }

  @Override
  public String toString() {

    return "CryptoTradeTicker [last=" + last + ", low=" + low + ", high=" + high + ", volumeTradeCurrency=" + volumeTradeCurrency + ", volumePriceCurrency=" + volumePriceCurrency + ", minAsk="
        + minAsk + ", maxBid=" + maxBid + "]";
  }

  static class CoibasePriceDeserializer extends JsonDeserializer<CryptoTradeTicker> {

    private BigDecimal getNumberIfPresent(final String numberString) {
      
      return numberString.isEmpty() ? null : new BigDecimal(numberString);
    }
    
    @Override
    public CryptoTradeTicker deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode node = oc.readTree(jp);
      final JsonNode tickerDataParentNode = node.path("data");

      final BigDecimal last = getNumberIfPresent(tickerDataParentNode.path("last").asText());
      final BigDecimal low = getNumberIfPresent(tickerDataParentNode.path("low").asText());
      final BigDecimal high = getNumberIfPresent(tickerDataParentNode.path("high").asText());
      final BigDecimal minAsk = getNumberIfPresent(tickerDataParentNode.path("min_ask").asText());
      final BigDecimal maxBid = getNumberIfPresent(tickerDataParentNode.path("max_bid").asText());
      BigDecimal volumeTradeCurrency = null;
      BigDecimal volumePriceCurrency = null;
      if (tickerDataParentNode instanceof ObjectNode) {
        final ObjectNode tickerDataObjectNode = (ObjectNode) tickerDataParentNode;
        final Iterator<Entry<String, JsonNode>> tickerDataFields = tickerDataObjectNode.fields();
        while (tickerDataFields.hasNext()) {
          final Entry<String, JsonNode> tickerDataEntry = tickerDataFields.next();
          if (tickerDataEntry.getKey().startsWith("vol_")) {
            if (volumeTradeCurrency == null)
              volumeTradeCurrency = getNumberIfPresent(tickerDataEntry.getValue().asText());
            else {
              volumePriceCurrency = getNumberIfPresent(tickerDataEntry.getValue().asText());
              break;
            }
          }
        }
      }

      final String status = node.path("status").asText();
      final String error = node.path("error").asText();

      return new CryptoTradeTicker(last, low, high, volumeTradeCurrency, volumePriceCurrency, minAsk, maxBid, status, error);
    }

  }
}
