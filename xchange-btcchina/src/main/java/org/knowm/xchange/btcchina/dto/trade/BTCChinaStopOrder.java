package org.knowm.xchange.btcchina.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A stop order allows you to put a “stop price” which will trigger an order to be placed when the stop price is reached.
 *
 * @see <a href="http://btcchina.org/api-trade-documentation-en#stop_order">Trade API(English)</a>
 * @see <a href="http://btcchina.org/api-trade-documentation-zh#stop_order">Trade API(Chinese)</a>
 */
public class BTCChinaStopOrder {

  private final int id;
  private final String type;
  private final BigDecimal stopPrice;
  private final BigDecimal trailingAmount;
  private final BigDecimal trailingPercentage;
  private final BigDecimal price;
  private final String market;
  private final BigDecimal amount;
  private final long date;
  private final String status;
  private final Integer orderId;

  public BTCChinaStopOrder(@JsonProperty("id") int id, @JsonProperty("type") String type, @JsonProperty("stop_price") BigDecimal stopPrice,
      @JsonProperty("trailing_amount") BigDecimal trailingAmount, @JsonProperty("trailing_percentage") BigDecimal trailingPercentage,
      @JsonProperty("price") BigDecimal price, @JsonProperty("market") String market, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("date") long date, @JsonProperty("status") String status, @JsonProperty("order_id") Integer orderId) {

    this.id = id;
    this.type = type;
    this.stopPrice = stopPrice;
    this.trailingAmount = trailingAmount;
    this.trailingPercentage = trailingPercentage;
    this.price = price;
    this.market = market;
    this.amount = amount;
    this.date = date;
    this.status = status;
    this.orderId = orderId;
  }

  /**
   * Returns the stop order id.
   *
   * @return the stop order id.
   */
  public int getId() {

    return id;
  }

  /**
   * Returns the stop order type.
   *
   * @return the stop order type, could be {@code bid} or {@code ask}.
   */
  public String getType() {

    return type;
  }

  /**
   * Returns the price to trigger the stop order.
   *
   * @return the price for 1 BTC/LTC to trigger the stop order. Can be dynamically set by the system if trailing amount/percentage is specified.
   */
  public BigDecimal getStopPrice() {

    return stopPrice;
  }

  /**
   * Returns the trailing amount to determine the stop price.
   *
   * @return the trailing amount to determine the stop price.
   */
  public BigDecimal getTrailingAmount() {

    return trailingAmount;
  }

  /**
   * Returns the trailing percentage to determine the stop price.
   *
   * @return the trailing percentage to determine the stop price.
   */
  public BigDecimal getTrailingPercentage() {

    return trailingPercentage;
  }

  /**
   * Returns the price for 1 BTC/LTC for the order to be placed.
   *
   * @return the price for 1 BTC/LTC for the order to be placed.
   */
  public BigDecimal getPrice() {

    return price;
  }

  /**
   * Returns the market.
   *
   * @return [BTCCNY|LTCCNY|LTCBTC]
   */
  public String getMarket() {

    return market;
  }

  /**
   * Returns the amount used for the order to be placed.
   *
   * @return the amount used for the order to be placed.
   */
  public BigDecimal getAmount() {

    return amount;
  }

  /**
   * Returns the create time in Unix time in seconds since 1 January 1970.
   *
   * @return the create time in Unix time in seconds since 1 January 1970.
   */
  public long getDate() {

    return date;
  }

  /**
   * Returns the status of the stop order.
   *
   * @return [ open | closed | cancelled | error ]
   */
  public String getStatus() {

    return status;
  }

  /**
   * Returns the order id of the order created from this stop order, or null if stop order still open or cancelled.
   *
   * @return the order id of the order created from this stop order, or null if stop order still open or cancelled.
   */
  public Integer getOrderId() {

    return orderId;
  }

}
