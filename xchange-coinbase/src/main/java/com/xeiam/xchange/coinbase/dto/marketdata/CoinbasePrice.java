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

import org.joda.money.BigMoney;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice.CoibasePriceDeserializer;
import com.xeiam.xchange.coinbase.dto.serialization.CoinbaseBigMoneyDeserializer;

/**
 * @author jamespedwards42
 */
@JsonDeserialize(using = CoibasePriceDeserializer.class)
public class CoinbasePrice {

  private final BigMoney coinbaseFee;
  private final BigMoney bankFee;
  private final BigMoney total;
  private final BigMoney subTotal;
  
  private CoinbasePrice(final BigMoney coinbaseFee, final BigMoney bankFee, final BigMoney total, final BigMoney subTotal) {
    
    this.coinbaseFee = coinbaseFee;
    this.bankFee = bankFee;
    this.total = total;
    this.subTotal = subTotal;
  }

  public BigMoney getCoinbaseFee() {

    return coinbaseFee;
  }

  public BigMoney getBankFee() {

    return bankFee;
  }

  public BigMoney getTotal() {

    return total;
  }

  public BigMoney getSubTotal() {

    return subTotal;
  }

  @Override
  public String toString() {

    return "CoinbasePrice [coinbaseFee=" + coinbaseFee + ", bankFee=" + bankFee + ", total=" + total + ", subTotal=" + subTotal + "]";
  }

  static class CoibasePriceDeserializer extends JsonDeserializer<CoinbasePrice> {

    @Override
    public CoinbasePrice deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode node = oc.readTree(jp);
      final BigMoney subTotal = CoinbaseBigMoneyDeserializer.getBigMoneyFromNode(node.path("subtotal"));
      final JsonNode feesNode = node.path("fees");
      final BigMoney coinbaseFee = CoinbaseBigMoneyDeserializer.getBigMoneyFromNode(feesNode.path(0).path("coinbase"));
      final BigMoney bankFee = CoinbaseBigMoneyDeserializer.getBigMoneyFromNode(feesNode.path(1).path("bank"));
      final BigMoney total = CoinbaseBigMoneyDeserializer.getBigMoneyFromNode(node.path("total"));
      return new CoinbasePrice(coinbaseFee, bankFee, total, subTotal);
    }
  }
}
