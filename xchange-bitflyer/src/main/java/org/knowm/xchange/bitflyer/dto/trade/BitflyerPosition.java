package org.knowm.xchange.bitflyer.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitflyerPosition {
  @JsonProperty("product_code")
  public String productCode;

  @JsonProperty("side")
  public String side;

  @JsonProperty("price")
  public BigDecimal price;

  @JsonProperty("size")
  public BigDecimal size;

  @JsonProperty("commission")
  public BigDecimal commission;

  @JsonProperty("swap_point_accumulate")
  public BigDecimal swapPointAccumulate;

  @JsonProperty("require_collateral")
  public BigDecimal requireCollateral;

  @JsonProperty("open_date")
  public String openDate;

  @JsonProperty("leverage")
  public BigDecimal leverage;

  @JsonProperty("pnl")
  public BigDecimal pnl;

  public String getProductCode() {
    return productCode;
  }

  public String getSide() {
    return side;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public BigDecimal getCommission() {
    return commission;
  }

  public BigDecimal getSwapPointAccumulate() {
    return swapPointAccumulate;
  }

  public BigDecimal getRequireCollateral() {
    return requireCollateral;
  }

  public String getOpenDate() {
    return openDate;
  }

  public BigDecimal getLeverage() {
    return leverage;
  }

  public BigDecimal getPnl() {
    return pnl;
  }

  @Override
  public String toString() {
    return "BitflyerPosition{"
        + "productCode='"
        + productCode
        + '\''
        + ", side='"
        + side
        + '\''
        + ", price="
        + price
        + ", size="
        + size
        + ", commission="
        + commission
        + ", swapPointAccumulate="
        + swapPointAccumulate
        + ", requireCollateral="
        + requireCollateral
        + ", openDate='"
        + openDate
        + '\''
        + ", leverage="
        + leverage
        + ", pnl="
        + pnl
        + '}';
  }
}
