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
import java.math.BigDecimal;

import org.joda.money.BigMoney;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseRate.CoibaseRateDeserializer;
import com.xeiam.xchange.currency.MoneyUtils;

@JsonDeserialize(using = CoibaseRateDeserializer.class)
public class CoinbaseRate {

  private final BigMoney rate;

  public CoinbaseRate(final BigMoney rate) {

    this.rate = rate;
  }

  public BigMoney getRate() {

    return rate;
  }

  @Override
  public String toString() {

    return "CoinbaseRate [rate=" + rate + "]";
  }

  static class CoibaseRateDeserializer extends JsonDeserializer<CoinbaseRate> {

    @Override
    public CoinbaseRate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      return new CoinbaseRate(getBigMoneyFromNode(node));
    }

    public BigMoney getBigMoneyFromNode(JsonNode node) {

      String amount = node.path("amount").asText();
      String currency = node.path("currency").asText();

      return MoneyUtils.parseMoney(currency, new BigDecimal(amount));
    }
  }
}