package org.knowm.xchange.bitmarket.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.bitmarket.BitMarketUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kfonal
 */
public class BitMarketOrder {

  private final long id;
  private final String market;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final BigDecimal fiat;
  private final String type;
  private final long time;
  private final CurrencyPair currencyPair;
  private final Date timestamp;

  /**
   * Constructor
   *
   * @param id
   * @param market
   * @param amount
   * @param rate
   * @param fiat
   * @param type
   * @param time
   */
  public BitMarketOrder(@JsonProperty("id") long id, @JsonProperty("market") String market, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("rate") BigDecimal rate, @JsonProperty("fiat") BigDecimal fiat, @JsonProperty("type") String type,
      @JsonProperty("time") long time) {

    this.id = id;
    this.market = market;
    this.amount = amount;
    this.rate = rate;
    this.fiat = fiat;
    this.type = type;
    this.time = time;
    this.timestamp = new Date(time * 1000);
    this.currencyPair = BitMarketUtils.bitMarketCurrencyPairToCurrencyPair(market);
  }

  public long getId() {
    return id;
  }

  public String getMarket() {
    return market;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getFiat() {
    return fiat;
  }

  public Order.OrderType getType() {
    return BitMarketUtils.bitMarketOrderTypeToOrderType(type);
  }

  public long getTime() {
    return time;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }
}
