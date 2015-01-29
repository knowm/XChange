package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trade;

/**
 * Data object representing a user trade
 */
public final class UserTrade extends Trade {

  /**
   * The id of the order responsible for execution of this trade
   */
  private final String orderId;

  /**
   * The fee that was charged by the exchange for this trade.
   */
  private final BigDecimal feeAmount;

  /**
   * The symbol of the currency in which the fee was charged.
   */
  private final String feeCurrency;

  /**
   * This constructor is called to construct user's trade objects (in
   * {@link com.xeiam.xchange.service.polling.trade.PollingTradeService#getTradeHistory(Object...)} implementations).
   *
   * @param type The trade type (BID side or ASK side)
   * @param tradableAmount The depth of this trade
   * @param tradableIdentifier The exchange identifier (e.g. "BTC/USD")
   * @param transactionCurrency The transaction currency (e.g. USD in BTC/USD)
   * @param price The price (either the bid or the ask)
   * @param timestamp The timestamp when the order was placed. Exchange matching is usually price first then timestamp asc to clear older orders
   * @param id The id of the trade
   * @param orderId The id of the order responsible for execution of this trade
   * @param feeAmount The fee that was charged by the exchange for this trade
   * @param feeCurrency The symbol of the currency in which the fee was charged
   */
  public UserTrade(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, BigDecimal price, Date timestamp, String id, String orderId,
      BigDecimal feeAmount, String feeCurrency) {

    super(type, tradableAmount, currencyPair, price, timestamp, id);

    this.orderId = orderId;
    this.feeAmount = feeAmount;
    this.feeCurrency = feeCurrency;
  }

  public String getOrderId() {

    return orderId;
  }

  public BigDecimal getFeeAmount() {

    return feeAmount;
  }

  public String getFeeCurrency() {

    return feeCurrency;
  }

  public static class Builder {

    private OrderType type;
    private BigDecimal tradableAmount;
    private CurrencyPair currencyPair;
    private BigDecimal price;
    private Date timestamp;
    private String id;
    private String orderId;
    private BigDecimal feeAmount;
    private String feeCurrency;

    public Builder() {

    }

    public static Builder from(UserTrade trade) {
      return new Builder().type(trade.getType()).tradableAmount(trade.getTradableAmount()).currencyPair(trade.getCurrencyPair()).price(trade.getPrice()).timestamp(trade.getTimestamp())
          .id(trade.getId()).orderId(trade.getOrderId()).feeAmount(trade.getFeeAmount()).feeCurrency(trade.getFeeCurrency());
    }

    public Builder type(OrderType type) {

      this.type = type;
      return this;
    }

    public Builder tradableAmount(BigDecimal tradableAmount) {

      this.tradableAmount = tradableAmount;
      return this;
    }

    public Builder currencyPair(CurrencyPair currencyPair) {

      this.currencyPair = currencyPair;
      return this;
    }

    public Builder price(BigDecimal price) {

      this.price = price;
      return this;
    }

    public Builder timestamp(Date timestamp) {

      this.timestamp = timestamp;
      return this;
    }

    public Builder id(String id) {

      this.id = id;
      return this;
    }

    public Builder orderId(String orderId) {

      this.orderId = orderId;
      return this;
    }

    public Builder feeAmount(BigDecimal feeAmount) {

      this.feeAmount = feeAmount;
      return this;
    }

    public Builder feeCurrency(String feeCurrency) {

      this.feeCurrency = feeCurrency;
      return this;
    }

    public UserTrade build() {

      return new UserTrade(type, tradableAmount, currencyPair, price, timestamp, id, orderId, feeAmount, feeCurrency);
    }
  }
}
