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
package com.xeiam.xchange.kraken.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenFee.KrakenFeeDeserializer;

@JsonDeserialize(using = KrakenFeeDeserializer.class)
public class KrakenFee {

  private final BigDecimal volume;
  private final BigDecimal percentFee;

  public KrakenFee(BigDecimal volume, BigDecimal percentFee) {

    this.volume = volume;
    this.percentFee = percentFee;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getPercentFee() {

    return percentFee;
  }

  @Override
  public String toString() {

    return "KrakenFee [volume=" + volume + ", percentFee=" + percentFee + "]";
  }

  static class KrakenFeeDeserializer extends JsonDeserializer<KrakenFee> {

    @Override
    public KrakenFee deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      BigDecimal volume = new BigDecimal(node.path(0).asText());
      BigDecimal fee = new BigDecimal(node.path(1).asText());

      return new KrakenFee(volume, fee);
    }
  }
}
