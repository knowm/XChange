package org.knowm.xchange.ftx.dto.account;

import java.math.BigDecimal;

public class FtxSubAccountTransferPOJO {

  private String coin;

  private BigDecimal size;

  private String source;

  private String destination;

  public FtxSubAccountTransferPOJO(
      String coin, BigDecimal size, String source, String destination) {
    this.coin = coin;
    this.size = size;
    this.source = source;
    this.destination = destination;
  }

  public String getCoin() {
    return coin;
  }

  public void setCoin(String coin) {
    this.coin = coin;
  }

  public BigDecimal getSize() {
    return size;
  }

  public void setSize(BigDecimal size) {
    this.size = size;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  @Override
  public String toString() {
    return "FtxSubAccountTransferPOJO{"
        + "coin='"
        + coin
        + '\''
        + ", size="
        + size
        + ", source='"
        + source
        + '\''
        + ", destination='"
        + destination
        + '\''
        + '}';
  }
}
