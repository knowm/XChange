package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class TradeResponseInner {

  private BigDecimal amount;
  private String date = "";
  private String total = "";
  private String market = "";
  private IdexBuySell type;
  private String price = "";
  private String orderHash = "";
  private String uuid = "";

  /** */
  public TradeResponseInner amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  /** */
  public TradeResponseInner date(String date) {
    this.date = date;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("date")
  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  /** */
  public TradeResponseInner total(String total) {
    this.total = total;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("total")
  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  /** */
  public TradeResponseInner market(String market) {
    this.market = market;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("market")
  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  /** */
  public TradeResponseInner type(IdexBuySell type) {
    this.type = type;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("type")
  public IdexBuySell getType() {
    return type;
  }

  public void setType(IdexBuySell type) {
    this.type = type;
  }

  /** */
  public TradeResponseInner price(String price) {
    this.price = price;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("price")
  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  /** */
  public TradeResponseInner orderHash(String orderHash) {
    this.orderHash = orderHash;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("orderHash")
  public String getOrderHash() {
    return orderHash;
  }

  public void setOrderHash(String orderHash) {
    this.orderHash = orderHash;
  }

  /** */
  public TradeResponseInner uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("uuid")
  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TradeResponseInner tradeResponseInner = (TradeResponseInner) o;
    return Objects.equals(amount, tradeResponseInner.amount)
        && Objects.equals(date, tradeResponseInner.date)
        && Objects.equals(total, tradeResponseInner.total)
        && Objects.equals(market, tradeResponseInner.market)
        && Objects.equals(type, tradeResponseInner.type)
        && Objects.equals(price, tradeResponseInner.price)
        && Objects.equals(orderHash, tradeResponseInner.orderHash)
        && Objects.equals(uuid, tradeResponseInner.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, date, total, market, type, price, orderHash, uuid);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TradeResponseInner {\n");

    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    market: ").append(toIndentedString(market)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    orderHash: ").append(toIndentedString(orderHash)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
