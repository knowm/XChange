package info.bitrich.xchangestream.bitmex.dto;

import java.math.BigDecimal;
import java.util.List;

public class RawOrderBook {
  private String symbol;
  private String timestamp;
  private List<List<BigDecimal>> asks;
  private List<List<BigDecimal>> bids;

  public List<List<BigDecimal>> getAsks() {
    return asks;
  }

  public List<List<BigDecimal>> getBids() {
    return bids;
  }
}
