package org.knowm.xchange.ftx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class FtxModifyOrderRequestPayload {

  private final BigDecimal price;

  private final BigDecimal size;

  private final String clientId;

  public FtxModifyOrderRequestPayload(
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("clientId") String clientId) {
    this.price = price;
    this.size = size;
    this.clientId = clientId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public String getClientId() {
    return clientId;
  }

  @Override
  public String toString() {
    return "FtxModifyOrderRequestPayload{"
        + "price="
        + price
        + ", size="
        + size
        + ", clientId='"
        + clientId
        + '\''
        + '}';
  }
}
