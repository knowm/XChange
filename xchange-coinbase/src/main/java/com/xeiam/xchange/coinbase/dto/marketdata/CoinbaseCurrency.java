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
package com.xeiam.xchange.coinbase.dto.marketdata;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseCurrency.CoinbaseCurrencyDeserializer;

/**
 * @author jamespedwards42
 */
@JsonDeserialize(using = CoinbaseCurrencyDeserializer.class)
public class CoinbaseCurrency {

  private final String name;
  private final String isoCode;

  private CoinbaseCurrency(final String name, final String isoCode) {

    this.name = name;
    this.isoCode = isoCode;
  }

  public String getName() {

    return name;
  }

  public String getIsoCode() {

    return isoCode;
  }

  @Override
  public String toString() {

    return "CoinbaseCurrency [name=" + name + ", isoCode=" + isoCode + "]";
  }

  static class CoinbaseCurrencyDeserializer extends JsonDeserializer<CoinbaseCurrency> {

    @Override
    public CoinbaseCurrency deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      if (node.isArray()) {
        String name = node.path(0).asText();
        String isoCode = node.path(1).asText();
        return new CoinbaseCurrency(name, isoCode);
      }
      return null;
    }
  }
}
