package org.knowm.xchange.gdax.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;
import si.mazi.rescu.HttpResponseAware;

public class GDAXTrades extends ArrayList<GDAXTrade> implements HttpResponseAware {
  private static final long serialVersionUID = 8072963227369004488L;
  private Map<String, List<String>> headers;
  private Long earliestTradeId;
  private Long latestTradeId;

  public GDAXTrades() {
    super();
    earliestTradeId = null;
    latestTradeId = null;
  }

  public boolean addAll(GDAXTrades gdaxTrades) {
    if (earliestTradeId == null) {
      earliestTradeId = gdaxTrades.getEarliestTradeId();
    } else if (gdaxTrades.getEarliestTradeId() != null) {
      earliestTradeId = Math.min(earliestTradeId, gdaxTrades.getEarliestTradeId());
    }

    if (latestTradeId == null) {
      latestTradeId = gdaxTrades.getLatestTradeId();
    } else if (gdaxTrades.getLatestTradeId() != null) {
      latestTradeId = Math.max(latestTradeId, gdaxTrades.getLatestTradeId());
    }

    return super.addAll(gdaxTrades);
  }

  @Override
  public void setResponseHeaders(Map<String, List<String>> headers) {
    this.headers = headers;

    earliestTradeId = getHeaderAsLong("cb-after");
    latestTradeId = getHeaderAsLong("cb-before");
  }

  @Override
  public Map<String, List<String>> getResponseHeaders() {
    return headers;
  }

  public Long getHeaderAsLong(String key) {
    Long header = null;

    try {
      header = Long.valueOf(getResponseHeaders().get(key).get(0));
    } catch (NullPointerException | NumberFormatException e) {
      // nop
    }

    return header;
  }

  public Long getEarliestTradeId() {
    return earliestTradeId;
  }

  public Long getLatestTradeId() {
    return latestTradeId;
  }
}
