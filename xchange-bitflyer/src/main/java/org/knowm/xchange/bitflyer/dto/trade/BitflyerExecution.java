package org.knowm.xchange.bitflyer.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "id",
  "side",
  "price",
  "size",
  "exec_date",
  "buy_child_order_acceptance_id",
  "sell_child_order_acceptance_id"
})
public class BitflyerExecution {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("side")
  private String side;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("size")
  private BigDecimal size;

  @JsonProperty("exec_date")
  private String execDate;

  @JsonProperty("buy_child_order_acceptance_id")
  private String buyChildOrderAcceptanceId;

  @JsonProperty("sell_child_order_acceptance_id")
  private String sellChildOrderAcceptanceId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public void setSize(BigDecimal size) {
    this.size = size;
  }

  public String getExecDate() {
    return execDate;
  }

  public void setExecDate(String execDate) {
    this.execDate = execDate;
  }

  public String getBuyChildOrderAcceptanceId() {
    return buyChildOrderAcceptanceId;
  }

  public void setBuyChildOrderAcceptanceId(String buyChildOrderAcceptanceId) {
    this.buyChildOrderAcceptanceId = buyChildOrderAcceptanceId;
  }

  public String getSellChildOrderAcceptanceId() {
    return sellChildOrderAcceptanceId;
  }

  public void setSellChildOrderAcceptanceId(String sellChildOrderAcceptanceId) {
    this.sellChildOrderAcceptanceId = sellChildOrderAcceptanceId;
  }

  @Override
  public String toString() {
    return "BitflyerExecution{"
        + "id="
        + id
        + ", side='"
        + side
        + '\''
        + ", price="
        + price
        + ", size="
        + size
        + ", execDate='"
        + execDate
        + '\''
        + ", buyChildOrderAcceptanceId='"
        + buyChildOrderAcceptanceId
        + '\''
        + ", sellChildOrderAcceptanceId='"
        + sellChildOrderAcceptanceId
        + '\''
        + '}';
  }
}
