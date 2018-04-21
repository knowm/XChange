package org.knowm.xchange.bitbay.v3.dto.trade;

import java.util.ArrayList;
import java.util.List;

public class BitbayUserTradesQuery {

  private String limit;
  private String nextPageCursor;
  private String fromTime;
  private String toTime;
  private List<String> markets = new ArrayList<>();

  public String getNextPageCursor() {
    return nextPageCursor;
  }

  public void setNextPageCursor(String nextPageCursor) {
    this.nextPageCursor = nextPageCursor;
  }

  public String getLimit() {
    return limit;
  }

  public void setLimit(String limit) {
    this.limit = limit;
  }

  public String getFromTime() {
    return fromTime;
  }

  public void setFromTime(String fromTime) {
    this.fromTime = fromTime;
  }

  public String getToTime() {
    return toTime;
  }

  public void setToTime(String toTime) {
    this.toTime = toTime;
  }

  public void setMarkets(List<String> markets) {
    this.markets = markets;
  }

  public List<String> getMarkets() {
    return markets;
  }
}
