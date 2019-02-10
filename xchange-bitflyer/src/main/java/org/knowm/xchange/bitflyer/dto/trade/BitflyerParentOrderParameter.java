package org.knowm.xchange.bitflyer.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitflyerParentOrderParameter {
  private static final int CURRENCY_SCALE = 2;
  private static final int SIZE_SCALE = 8;

  private String productCode;
  private BitflyerParentOrderConditionType conditionType;
  private BitflyerSide side;
  private BigDecimal price;
  private BigDecimal triggerPrice;
  private BigDecimal size;
  private BigDecimal offset;

  public BitflyerParentOrderParameter(
      String productCode,
      BitflyerParentOrderConditionType conditionType,
      BitflyerSide side,
      BigDecimal price,
      BigDecimal triggerPrice,
      BigDecimal size,
      BigDecimal offset) {
    this.productCode = productCode;
    this.conditionType = conditionType;
    this.side = side;
    this.price = price != null ? price.setScale(CURRENCY_SCALE, RoundingMode.HALF_EVEN) : null;
    this.triggerPrice =
        triggerPrice != null ? triggerPrice.setScale(CURRENCY_SCALE, RoundingMode.HALF_EVEN) : null;
    this.size = size != null ? size.setScale(SIZE_SCALE, RoundingMode.HALF_EVEN) : null;
    this.offset = offset != null ? offset.setScale(CURRENCY_SCALE, RoundingMode.HALF_EVEN) : null;
  }

  @JsonProperty("product_code")
  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  @JsonProperty("condition_type")
  public BitflyerParentOrderConditionType getConditionType() {
    return conditionType;
  }

  public void setConditionType(BitflyerParentOrderConditionType conditionType) {
    this.conditionType = conditionType;
  }

  @JsonProperty("side")
  public BitflyerSide getSide() {
    return side;
  }

  public void setSide(BitflyerSide side) {
    this.side = side;
  }

  @JsonProperty("price")
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @JsonProperty("trigger_price")
  public BigDecimal getTriggerPrice() {
    return triggerPrice;
  }

  public void setTriggerPrice(BigDecimal triggerPrice) {
    this.triggerPrice = triggerPrice;
  }

  @JsonProperty("size")
  public BigDecimal getSize() {
    return size;
  }

  public void setSize(BigDecimal size) {
    this.size = size;
  }

  @JsonProperty("offset")
  public BigDecimal getOffset() {
    return offset;
  }

  public void setOffset(BigDecimal offset) {
    this.offset = offset;
  }

  @Override
  public String toString() {
    return "BitflyerParentOrderParameter{"
        + "productCode='"
        + productCode
        + '\''
        + ", conditionType="
        + conditionType
        + ", side="
        + side
        + ", price="
        + price
        + ", triggerPrice="
        + triggerPrice
        + ", size="
        + size
        + ", offset="
        + offset
        + '}';
  }
}
