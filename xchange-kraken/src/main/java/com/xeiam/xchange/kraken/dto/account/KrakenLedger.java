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
package com.xeiam.xchange.kraken.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenLedger {

  private final String refId;
  private final double unixTime;
  private final LedgerType ledgerType;
  private final String assetClass;
  private final String asset;
  private final BigDecimal transactionAmount;
  private final BigDecimal fee;
  private final BigDecimal balance;

  /**
   * Constructor
   * 
   * @param refId
   * @param unixTime
   * @param ledgerType
   * @param assetClass
   * @param asset
   * @param transactionAmount
   * @param fee
   * @param balance
   */
  public KrakenLedger(@JsonProperty("refid") String refId, @JsonProperty("time") double unixTime, @JsonProperty("type") LedgerType ledgerType, @JsonProperty("aclass") String assetClass,
      @JsonProperty("asset") String asset, @JsonProperty("amount") BigDecimal transactionAmount, @JsonProperty("fee") BigDecimal fee, @JsonProperty("balance") BigDecimal balance) {

    this.refId = refId;
    this.unixTime = unixTime;
    this.ledgerType = ledgerType;
    this.assetClass = assetClass;
    this.asset = asset;
    this.transactionAmount = transactionAmount;
    this.fee = fee;
    this.balance = balance;
  }

  public String getRefId() {

    return refId;
  }

  public double getUnixTime() {

    return unixTime;
  }

  public LedgerType getLedgerType() {

    return ledgerType;
  }

  public String getAssetClass() {

    return assetClass;
  }

  public String getAsset() {

    return asset;
  }

  public BigDecimal getTransactionAmount() {

    return transactionAmount;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public BigDecimal getBalance() {

    return balance;
  }

  @Override
  public String toString() {

    return "KrakenLedgerInfo [refId=" + refId + ", unixTime=" + unixTime + ", ledgerType=" + ledgerType + ", assetClass=" + assetClass + ", asset=" + asset + ", transactionAmount="
        + transactionAmount + ", fee=" + fee + ", balance=" + balance + "]";
  }

}
