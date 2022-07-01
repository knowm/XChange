package org.knowm.xchange.bitflyer.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitflyerQueryChildOrderResult {
  private Long id;

  @JsonProperty("child_order_id")
  private String childOrderId;

  @JsonProperty("product_code")
  private String productCode;

  private String side;

  @JsonProperty("child_order_type")
  private String childOrderType;

  private BigDecimal price;

  @JsonProperty("average_price")
  private BigDecimal averagePrice;

  private BigDecimal size;

  @JsonProperty("child_order_state")
  private String childOrderState;

  @JsonProperty("expire_date")
  private String expireDate;

  @JsonProperty("child_order_date")
  private String childOrderDate;

  @JsonProperty("child_order_acceptance_id")
  private String childOrderAcceptanceId;

  @JsonProperty("outstanding_size")
  private BigDecimal outstandingSize;

  @JsonProperty("cancel_size")
  private BigDecimal cancelSize;

  @JsonProperty("executed_size")
  private BigDecimal executedSize;

  @JsonProperty("total_commission")
  private BigDecimal totalCommission;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getChildOrderId() {
    return childOrderId;
  }

  public void setChildOrderId(String childOrderId) {
    this.childOrderId = childOrderId;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public String getChildOrderType() {
    return childOrderType;
  }

  public void setChildOrderType(String childOrderType) {
    this.childOrderType = childOrderType;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getAveragePrice() {
    return averagePrice;
  }

  public void setAveragePrice(BigDecimal averagePrice) {
    this.averagePrice = averagePrice;
  }

  public BigDecimal getSize() {
    return size;
  }

  public void setSize(BigDecimal size) {
    this.size = size;
  }

  public String getChildOrderState() {
    return childOrderState;
  }

  public void setChildOrderState(String childOrderState) {
    this.childOrderState = childOrderState;
  }

  public String getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(String expireDate) {
    this.expireDate = expireDate;
  }

  public String getChildOrderDate() {
    return childOrderDate;
  }

  public void setChildOrderDate(String childOrderDate) {
    this.childOrderDate = childOrderDate;
  }

  public String getChildOrderAcceptanceId() {
    return childOrderAcceptanceId;
  }

  public void setChildOrderAcceptanceId(String childOrderAcceptanceId) {
    this.childOrderAcceptanceId = childOrderAcceptanceId;
  }

  public BigDecimal getOutstandingSize() {
    return outstandingSize;
  }

  public void setOutstandingSize(BigDecimal outstandingSize) {
    this.outstandingSize = outstandingSize;
  }

  public BigDecimal getCancelSize() {
    return cancelSize;
  }

  public void setCancelSize(BigDecimal cancelSize) {
    this.cancelSize = cancelSize;
  }

  public BigDecimal getExecutedSize() {
    return executedSize;
  }

  public void setExecutedSize(BigDecimal executedSize) {
    this.executedSize = executedSize;
  }

  public BigDecimal getTotalCommission() {
    return totalCommission;
  }

  public void setTotalCommission(BigDecimal totalCommission) {
    this.totalCommission = totalCommission;
  }

  @Override
  public String toString() {
    return "BitflyerQueryChildOrderResult{"
        + "id="
        + id
        + ", childOrderId='"
        + childOrderId
        + '\''
        + ", productCode='"
        + productCode
        + '\''
        + ", side='"
        + side
        + '\''
        + ", childOrderType='"
        + childOrderType
        + '\''
        + ", price="
        + price
        + ", averagePrice="
        + averagePrice
        + ", size="
        + size
        + ", childOrderState='"
        + childOrderState
        + '\''
        + ", expireDate='"
        + expireDate
        + '\''
        + ", childOrderDate='"
        + childOrderDate
        + '\''
        + ", childOrderAcceptanceId='"
        + childOrderAcceptanceId
        + '\''
        + ", outstandingSize="
        + outstandingSize
        + ", cancelSize="
        + cancelSize
        + ", executedSize="
        + executedSize
        + ", totalCommission="
        + totalCommission
        + '}';
  }
}
