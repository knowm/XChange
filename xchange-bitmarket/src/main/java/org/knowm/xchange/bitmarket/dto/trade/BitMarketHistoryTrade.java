package org.knowm.xchange.bitmarket.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

/** @author kfonal */
public class BitMarketHistoryTrade {

  private final long id;
  private final String type;
  private final BigDecimal amountCrypto;
  private final String currencyCrypto;
  private final BigDecimal amountFiat;
  private final String currencyFiat;
  private final BigDecimal rate;
  private final long time;
  private final Date timestamp;

  /**
   * Constructor
   *
   * @param id
   * @param type
   * @param amountCrypto
   * @param currencyCrypto
   * @param amountFiat
   * @param currencyFiat
   * @param rate
   * @param time
   */
  public BitMarketHistoryTrade(
      @JsonProperty("id") long id,
      @JsonProperty("type") String type,
      @JsonProperty("amountCrypto") BigDecimal amountCrypto,
      @JsonProperty("currencyCrypto") String currencyCrypto,
      @JsonProperty("amountFiat") BigDecimal amountFiat,
      @JsonProperty("currencyFiat") String currencyFiat,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("time") long time) {

    this.id = id;
    this.type = type;
    this.amountCrypto = amountCrypto;
    this.currencyCrypto = currencyCrypto;
    this.amountFiat = amountFiat;
    this.currencyFiat = currencyFiat;
    this.rate = rate;
    this.time = time;
    this.timestamp = new Date(time * 1000);
  }

  public long getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getAmountCrypto() {
    return amountCrypto;
  }

  public String getCurrencyCrypto() {
    return currencyCrypto;
  }

  public BigDecimal getAmountFiat() {
    return amountFiat;
  }

  public String getCurrencyFiat() {
    return currencyFiat;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public long getTime() {
    return time;
  }

  public Date getTimestamp() {
    return timestamp;
  }
}
