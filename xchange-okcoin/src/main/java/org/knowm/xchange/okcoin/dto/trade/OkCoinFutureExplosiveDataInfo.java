package org.knowm.xchange.okcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class OkCoinFutureExplosiveDataInfo {
  private final Integer amount;
  private final String createDate;
  private final BigDecimal loss;
  private final BigDecimal price;
  private final Integer type;

  public OkCoinFutureExplosiveDataInfo(
      @JsonProperty("amount") final Integer amount,
      @JsonProperty("create_date") final String createDate,
      @JsonProperty("loss") final BigDecimal loss,
      @JsonProperty("price") final BigDecimal price,
      @JsonProperty("type") final Integer type) {
    this.amount = amount;
    this.createDate = createDate;
    this.loss = loss;
    this.price = price;
    this.type = type;
  }

  public Integer getAmount() {
    return amount;
  }

  public String getCreateDate() {
    return createDate;
  }

  public BigDecimal getLoss() {
    return loss;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Integer getType() {
    return type;
  }

  @Override
  public String toString() {
    return "OkCoinFutureExplosiveDataInfo{"
        + "amount="
        + amount
        + ", createDate='"
        + createDate
        + '\''
        + ", loss="
        + loss
        + ", price="
        + price
        + ", type="
        + type
        + '}';
  }
}
