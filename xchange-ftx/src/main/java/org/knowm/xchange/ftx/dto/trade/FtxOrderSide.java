package org.knowm.xchange.ftx.dto.trade;

public enum FtxOrderSide {
  sell,
  buy;

  public FtxOrderSide getOpposite() {
    switch (this) {
      case sell:
        return buy;
      case buy:
        return sell;
      default:
        return null;
    }
  }
}
