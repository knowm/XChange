package org.knowm.xchange.btctrade.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.btctrade.dto.BTCTradeResult;

public class BTCTradeBalance extends BTCTradeResult {

  private final Long uid;
  private final Integer nameauth;
  private final String moflag;
  private final BigDecimal btcBalance;
  private final BigDecimal btcReserved;
  private final BigDecimal ltcBalance;
  private final BigDecimal ltcReserved;
  private final BigDecimal dogeBalance;
  private final BigDecimal dogeReserved;
  private final BigDecimal ybcBalance;
  private final BigDecimal ybcReserved;
  private final BigDecimal cnyBalance;
  private final BigDecimal cnyReserved;

  public BTCTradeBalance(
      @JsonProperty("result") Boolean result,
      @JsonProperty("message") String message,
      @JsonProperty("uid") Long uid,
      @JsonProperty("nameauth") Integer nameauth,
      @JsonProperty("moflag") String moflag,
      @JsonProperty("btc_balance") BigDecimal btcBalance,
      @JsonProperty("btc_reserved") BigDecimal btcReserved,
      @JsonProperty("ltc_balance") BigDecimal ltcBalance,
      @JsonProperty("ltc_reserved") BigDecimal ltcReserved,
      @JsonProperty("doge_balance") BigDecimal dogeBalance,
      @JsonProperty("doge_reserved") BigDecimal dogeReserved,
      @JsonProperty("ybc_balance") BigDecimal ybcBalance,
      @JsonProperty("ybc_reserved") BigDecimal ybcReserved,
      @JsonProperty("cny_balance") BigDecimal cnyBalance,
      @JsonProperty("cny_reserved") BigDecimal cnyReserved) {

    super(result, message);
    this.uid = uid;
    this.nameauth = nameauth;
    this.moflag = moflag;
    this.btcBalance = btcBalance;
    this.btcReserved = btcReserved;
    this.ltcBalance = ltcBalance;
    this.ltcReserved = ltcReserved;
    this.dogeBalance = dogeBalance;
    this.dogeReserved = dogeReserved;
    this.ybcBalance = ybcBalance;
    this.ybcReserved = ybcReserved;
    this.cnyBalance = cnyBalance;
    this.cnyReserved = cnyReserved;
  }

  public Long getUid() {

    return uid;
  }

  public Integer getNameauth() {

    return nameauth;
  }

  public String getMoflag() {

    return moflag;
  }

  public BigDecimal getBtcBalance() {

    return btcBalance;
  }

  public BigDecimal getBtcReserved() {

    return btcReserved;
  }

  public BigDecimal getLtcBalance() {

    return ltcBalance;
  }

  public BigDecimal getLtcReserved() {

    return ltcReserved;
  }

  public BigDecimal getDogeBalance() {

    return dogeBalance;
  }

  public BigDecimal getDogeReserved() {

    return dogeReserved;
  }

  public BigDecimal getYbcBalance() {

    return ybcBalance;
  }

  public BigDecimal getYbcReserved() {

    return ybcReserved;
  }

  public BigDecimal getCnyBalance() {

    return cnyBalance;
  }

  public BigDecimal getCnyReserved() {

    return cnyReserved;
  }
}
