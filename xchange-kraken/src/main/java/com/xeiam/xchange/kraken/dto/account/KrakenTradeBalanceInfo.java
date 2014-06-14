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
package com.xeiam.xchange.kraken.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenTradeBalanceInfo {

  private final BigDecimal tradeBalance; // trade balance (combined balance of all currencies)
  private final BigDecimal margin; // initial margin amount of open positions
  private final BigDecimal unrealizedGainsLosses; // unrealized net profit/loss of open positions
  private final BigDecimal costBasis; // cost basis of open positions
  private final BigDecimal floatingValuation; // current floating valuation of open positions
  private final BigDecimal equity; // trade balance + unrealized net profit/loss
  private final BigDecimal freeMargin; // equity - initial margin (maximum margin available to open new positions)
  private final BigDecimal marginLevel; // (equity / initial margin) * 100

  /**
   * Constructor
   * 
   * @param tradeBalance
   * @param margin
   * @param unrealizedGainsLosses
   * @param costBasis
   * @param floatingValuation
   * @param equity
   * @param freeMargin
   * @param marginLevel
   */
  public KrakenTradeBalanceInfo(@JsonProperty("tb") BigDecimal tradeBalance, @JsonProperty("m") BigDecimal margin, @JsonProperty("n") BigDecimal unrealizedGainsLosses,
      @JsonProperty("c") BigDecimal costBasis, @JsonProperty("v") BigDecimal floatingValuation, @JsonProperty("e") BigDecimal equity, @JsonProperty("mf") BigDecimal freeMargin,
      @JsonProperty("ml") BigDecimal marginLevel) {

    this.tradeBalance = tradeBalance;
    this.margin = margin;
    this.unrealizedGainsLosses = unrealizedGainsLosses;
    this.costBasis = costBasis;
    this.floatingValuation = floatingValuation;
    this.equity = equity;
    this.freeMargin = freeMargin;
    this.marginLevel = marginLevel;
  }

  public BigDecimal getTradeBalance() {

    return tradeBalance;
  }

  public BigDecimal getMargin() {

    return margin;
  }

  public BigDecimal getUnrealizedGainsLosses() {

    return unrealizedGainsLosses;
  }

  public BigDecimal getCostBasis() {

    return costBasis;
  }

  public BigDecimal getFloatingValuation() {

    return floatingValuation;
  }

  public BigDecimal getEquity() {

    return equity;
  }

  public BigDecimal getFreeMargin() {

    return freeMargin;
  }

  public BigDecimal getMarginLevel() {

    return marginLevel;
  }

  @Override
  public String toString() {

    return "KrakenTradeBalanceInfo [tradeBalance=" + tradeBalance + ", margin=" + margin + ", unrealizedGainsLosses=" + unrealizedGainsLosses + ", costBasis=" + costBasis + ", floatingValuation="
        + floatingValuation + ", equity=" + equity + ", freeMargin=" + freeMargin + ", marginLevel=" + marginLevel + "]";
  }

}
