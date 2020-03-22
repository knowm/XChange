package org.knowm.xchange.bithumb.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

public class BithumbUserTransaction {
  public static final String SEARCH_BUY = "1";
  public static final String SEARCH_SELL = "2";

  private final String search;
  private final long transferDate;
  private final String orderCurrency;
  private final String paymentCurrency;
  private final BigDecimal units;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal fee;
  private final String feeCurrency;
  private final BigDecimal orderBalance;
  private final BigDecimal paymentBalance;

  public BithumbUserTransaction(
      @JsonProperty("search") String search,
      @JsonProperty("transfer_date") Long transferDate,
      @JsonProperty("order_currency") String orderCurrency,
      @JsonProperty("payment_currency") String paymentCurrency,
      @JsonProperty("units") String units,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("fee_currency") String feeCurrency,
      @JsonProperty("order_balance") BigDecimal orderBalance,
      @JsonProperty("payment_balance") BigDecimal paymentBalance) {
    this.search = search;
    this.transferDate = transferDate;
    this.orderCurrency = orderCurrency;
    this.paymentCurrency = paymentCurrency;
    String parse = StringUtils.remove(units, ' ');
    this.units = parse == null ? null : new BigDecimal(parse).abs();
    this.price = price;
    this.amount = amount;
    this.fee = fee;
    this.feeCurrency = feeCurrency;
    this.orderBalance = orderBalance;
    this.paymentBalance = paymentBalance;
  }

  public String getSearch() {
    return search;
  }

  public long getTransferDate() {
    return transferDate;
  }

  public String getOrderCurrency() {
    return orderCurrency;
  }

  public String getPaymentCurrency() {
    return paymentCurrency;
  }

  public BigDecimal getUnits() {
    return units;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getFeeCurrency() {
    return feeCurrency;
  }

  public BigDecimal getOrderBalance() {
    return orderBalance;
  }

  public BigDecimal getPaymentBalance() {
    return paymentBalance;
  }

  @Override
  public String toString() {
    return "BithumbUserTransaction{"
        + "search='"
        + search
        + '\''
        + ", transferDate="
        + transferDate
        + ", order_currency='"
        + orderCurrency
        + '\''
        + ", payment_currency='"
        + paymentCurrency
        + '\''
        + ", units='"
        + units
        + '\''
        + ", price="
        + price
        + ", amount="
        + amount
        + ", fee="
        + fee
        + ", order_balance="
        + orderBalance
        + ", payment_balance="
        + paymentBalance
        + "} "
        + super.toString();
  }

  public boolean isBuyOrSell() {
    return StringUtils.equalsAny(search, SEARCH_BUY, SEARCH_SELL);
  }
}
