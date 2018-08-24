package org.knowm.xchange.coinsuper.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
  "orderNo",
  "priceLimit",
  "quantity",
  "symbol",
  "action",
  "orderType",
  "fee",
  "quantityRemaining",
  "amount",
  "amountRemaining",
  "state",
  "detail"
})
public class OrderDetail {

  @JsonProperty("orderNo")
  private long orderNo;

  @JsonProperty("priceLimit")
  private BigDecimal priceLimit;

  @JsonProperty("quantity")
  private String quantity;

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("action")
  private String action;

  @JsonProperty("orderType")
  private String orderType;

  @JsonProperty("fee")
  private double fee;

  @JsonProperty("quantityRemaining")
  private String quantityRemaining;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("amountRemaining")
  private String amountRemaining;

  @JsonProperty("state")
  private String state;

  @JsonProperty("detail")
  private List<Detail> detail = null;

  /** No args constructor for use in serialization */
  public OrderDetail() {}

  /**
   * @param amount
   * @param fee
   * @param detail
   * @param orderNo
   * @param symbol
   * @param priceLimit
   * @param orderType
   * @param state
   * @param action
   * @param quantityRemaining
   * @param quantity
   * @param amountRemaining
   */
  public OrderDetail(
      long orderNo,
      BigDecimal priceLimit,
      String quantity,
      String symbol,
      String action,
      String orderType,
      double fee,
      String quantityRemaining,
      BigDecimal amount,
      String amountRemaining,
      String state,
      List<Detail> detail) {
    super();
    this.orderNo = orderNo;
    this.priceLimit = priceLimit;
    this.quantity = quantity;
    this.symbol = symbol;
    this.action = action;
    this.orderType = orderType;
    this.fee = fee;
    this.quantityRemaining = quantityRemaining;
    this.amount = amount;
    this.amountRemaining = amountRemaining;
    this.state = state;
    this.detail = detail;
  }

  @JsonProperty("orderNo")
  public long getOrderNo() {
    return orderNo;
  }

  @JsonProperty("orderNo")
  public void setOrderNo(long orderNo) {
    this.orderNo = orderNo;
  }

  @JsonProperty("priceLimit")
  public BigDecimal getPriceLimit() {
    return priceLimit;
  }

  @JsonProperty("priceLimit")
  public void setPriceLimit(BigDecimal priceLimit) {
    this.priceLimit = priceLimit;
  }

  @JsonProperty("quantity")
  public String getQuantity() {
    return quantity;
  }

  @JsonProperty("quantity")
  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  @JsonProperty("symbol")
  public String getSymbol() {
    return symbol;
  }

  @JsonProperty("symbol")
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  @JsonProperty("action")
  public String getAction() {
    return action;
  }

  @JsonProperty("action")
  public void setAction(String action) {
    this.action = action;
  }

  @JsonProperty("orderType")
  public String getOrderType() {
    return orderType;
  }

  @JsonProperty("orderType")
  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  @JsonProperty("fee")
  public double getFee() {
    return fee;
  }

  @JsonProperty("fee")
  public void setFee(double fee) {
    this.fee = fee;
  }

  @JsonProperty("quantityRemaining")
  public String getQuantityRemaining() {
    return quantityRemaining;
  }

  @JsonProperty("quantityRemaining")
  public void setQuantityRemaining(String quantityRemaining) {
    this.quantityRemaining = quantityRemaining;
  }

  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  @JsonProperty("amount")
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @JsonProperty("amountRemaining")
  public String getAmountRemaining() {
    return amountRemaining;
  }

  @JsonProperty("amountRemaining")
  public void setAmountRemaining(String amountRemaining) {
    this.amountRemaining = amountRemaining;
  }

  @JsonProperty("state")
  public String getState() {
    return state;
  }

  @JsonProperty("state")
  public void setState(String state) {
    this.state = state;
  }

  @JsonProperty("detail")
  public List<Detail> getDetail() {
    return detail;
  }

  @JsonProperty("detail")
  public void setDetail(List<Detail> detail) {
    this.detail = detail;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("orderNo", orderNo)
        .append("priceLimit", priceLimit)
        .append("quantity", quantity)
        .append("symbol", symbol)
        .append("action", action)
        .append("orderType", orderType)
        .append("fee", fee)
        .append("quantityRemaining", quantityRemaining)
        .append("amount", amount)
        .append("amountRemaining", amountRemaining)
        .append("state", state)
        .append("detail", detail)
        .toString();
  }

  public static class Detail {

    @JsonProperty("dealNo")
    private long dealNo;

    @JsonProperty("matchType")
    private String matchType;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("volume")
    private BigDecimal volume;

    @JsonProperty("utcDeal")
    private long utcDeal;

    /** No args constructor for use in serialization */
    public Detail() {}

    /**
     * @param utcDeal
     * @param price
     * @param matchType
     * @param volume
     * @param dealNo
     */
    public Detail(
        long dealNo, String matchType, BigDecimal price, BigDecimal volume, long utcDeal) {
      super();
      this.dealNo = dealNo;
      this.matchType = matchType;
      this.price = price;
      this.volume = volume;
      this.utcDeal = utcDeal;
    }

    @JsonProperty("dealNo")
    public long getDealNo() {
      return dealNo;
    }

    @JsonProperty("dealNo")
    public void setDealNo(long dealNo) {
      this.dealNo = dealNo;
    }

    public Detail withDealNo(long dealNo) {
      this.dealNo = dealNo;
      return this;
    }

    @JsonProperty("matchType")
    public String getMatchType() {
      return matchType;
    }

    @JsonProperty("matchType")
    public void setMatchType(String matchType) {
      this.matchType = matchType;
    }

    public Detail withMatchType(String matchType) {
      this.matchType = matchType;
      return this;
    }

    @JsonProperty("price")
    public BigDecimal getPrice() {
      return price;
    }

    @JsonProperty("price")
    public void setPrice(BigDecimal price) {
      this.price = price;
    }

    public Detail withPrice(BigDecimal price) {
      this.price = price;
      return this;
    }

    @JsonProperty("volume")
    public BigDecimal getVolume() {
      return volume;
    }

    @JsonProperty("volume")
    public void setVolume(BigDecimal volume) {
      this.volume = volume;
    }

    public Detail withVolume(BigDecimal volume) {
      this.volume = volume;
      return this;
    }

    @JsonProperty("utcDeal")
    public long getUtcDeal() {
      return utcDeal;
    }

    @JsonProperty("utcDeal")
    public void setUtcDeal(long utcDeal) {
      this.utcDeal = utcDeal;
    }

    public Detail withUtcDeal(long utcDeal) {
      this.utcDeal = utcDeal;
      return this;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("dealNo", dealNo)
          .append("matchType", matchType)
          .append("price", price)
          .append("volume", volume)
          .append("utcDeal", utcDeal)
          .toString();
    }
  }
}
