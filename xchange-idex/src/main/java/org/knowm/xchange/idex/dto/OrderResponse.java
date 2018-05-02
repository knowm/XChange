package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class OrderResponse {

  private String error;
  private java.math.BigInteger orderNumber;
  private String orderHash = "";
  private String price = "";
  private String amount = "";
  private String total;
  private IdexBuySell type;
  private OrderResponseParams params;

  /** */
  public OrderResponse error(String error) {
    this.error = error;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("error")
  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  /** */
  public OrderResponse orderNumber(java.math.BigInteger orderNumber) {
    this.orderNumber = orderNumber;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("orderNumber")
  public java.math.BigInteger getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(java.math.BigInteger orderNumber) {
    this.orderNumber = orderNumber;
  }

  /** */
  public OrderResponse orderHash(String orderHash) {
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
  public OrderResponse price(String price) {
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
  public OrderResponse amount(String amount) {
    this.amount = amount;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("amount")
  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  /** */
  public OrderResponse total(String total) {
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
  public OrderResponse type(IdexBuySell type) {
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
  public OrderResponse params(OrderResponseParams params) {
    this.params = params;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("params")
  public OrderResponseParams getParams() {
    return params;
  }

  public void setParams(OrderResponseParams params) {
    this.params = params;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderResponse orderResponse = (OrderResponse) o;
    return Objects.equals(error, orderResponse.error)
        && Objects.equals(orderNumber, orderResponse.orderNumber)
        && Objects.equals(orderHash, orderResponse.orderHash)
        && Objects.equals(price, orderResponse.price)
        && Objects.equals(amount, orderResponse.amount)
        && Objects.equals(total, orderResponse.total)
        && Objects.equals(type, orderResponse.type)
        && Objects.equals(params, orderResponse.params);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, orderNumber, orderHash, price, amount, total, type, params);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderResponse {\n");

    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    orderNumber: ").append(toIndentedString(orderNumber)).append("\n");
    sb.append("    orderHash: ").append(toIndentedString(orderHash)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    params: ").append(toIndentedString(params)).append("\n");
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
