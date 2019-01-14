package org.knowm.xchange.bithumb.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.bithumb.dto.BithumbResponse;

public class BithumbTradeResponse extends BithumbResponse<List<BithumbTradeResponse.BithumbTrade>> {

  private final String orderId;

  public BithumbTradeResponse(
      @JsonProperty("status") String status,
      @JsonProperty("message") String message,
      @JsonProperty("data") List<BithumbTrade> data,
      @JsonProperty("order_id") String orderId) {
    super(status, message, data);
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }

  public static class BithumbTrade {
    private final String contId;
    private final BigDecimal units;
    private final BigDecimal price;
    private final BigDecimal total;
    private final BigDecimal fee;

    public BithumbTrade(
        @JsonProperty("cont_id") String contId,
        @JsonProperty("units") BigDecimal units,
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("total") BigDecimal total,
        @JsonProperty("fee") BigDecimal fee) {
      this.contId = contId;
      this.units = units;
      this.price = price;
      this.total = total;
      this.fee = fee;
    }

    public String getContId() {
      return contId;
    }

    public BigDecimal getUnits() {
      return units;
    }

    public BigDecimal getPrice() {
      return price;
    }

    public BigDecimal getTotal() {
      return total;
    }

    public BigDecimal getFee() {
      return fee;
    }

    @Override
    public String toString() {
      return "BithumbTrade{"
          + "contId='"
          + contId
          + '\''
          + ", units="
          + units
          + ", price="
          + price
          + ", total="
          + total
          + ", fee="
          + fee
          + '}';
    }
  }
}
