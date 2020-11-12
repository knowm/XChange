package org.knowm.xchange.bitbns.dto;

public class OrderStatusBody {

  private long entry_id;
  private String symbol;

  public long getEntry_id() {
    return entry_id;
  }

  public void setEntry_id(long entry_id) {
    this.entry_id = entry_id;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return "OrderStatusBody [entry_id=" + entry_id + ", symbol=" + symbol + "]";
  }
}
