package org.knowm.xchange.ftx.dto.trade;

import java.math.BigDecimal;

public class FtxModifyOrderRequestPayload {

  private final BigDecimal price;

  private final BigDecimal size;

  private final String cliendId;

  public FtxModifyOrderRequestPayload(BigDecimal price, BigDecimal size, String cliendId) {
    this.price = price;
    this.size = size;
    this.cliendId = cliendId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public String getCliendId() {
    return cliendId;
  }

  @Override
  public String toString() {
    return "FtxModifyOrderRequestPayload{"
        + "price="
        + price
        + ", size="
        + size
        + ", cliendId='"
        + cliendId
        + '\''
        + '}';
  }
}
