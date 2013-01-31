/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitcoincentral.dto.trade;

import java.math.BigDecimal;
import java.text.MessageFormat;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi
 */
public class BitcoinCentralTradeRequest extends BitcoinCentralTradeBase {

  @JsonProperty("type")
  private final Type type;

  public BitcoinCentralTradeRequest(@JsonProperty("amount") BigDecimal amount, @JsonProperty("category") Category category, @JsonProperty("currency") String currency,
      @JsonProperty("ppc") BigDecimal ppc, @JsonProperty("type") Type type) {

    super(category, currency, amount, ppc);
    this.type = type;
  }

  public Type getType() {

    return type;
  }

  @Override
  public String toString() {

    return MessageFormat.format("BitcoinCentralTradeBase[amount={0}, category={1}, currency={2}, ppc={3}, type={4}]", amount, category, currency, ppc, type);
  }

  public static enum Type {
    limit_order
  }
}
