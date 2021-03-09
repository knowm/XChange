package org.knowm.xchange.coinbasepro.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProTrade;
import si.mazi.rescu.HttpResponseAware;

public class CoinbaseProTrades extends ArrayList<CoinbaseProTrade> implements HttpResponseAware {

  private static final long serialVersionUID = 8072963227369004488L;
  private Map<String, List<String>> headers;
  private Long earliestTradeId;
  private Long latestTradeId;

  public CoinbaseProTrades() {
    super();
    earliestTradeId = null;
    latestTradeId = null;
  }

  public boolean addAll(CoinbaseProTrades coinbaseProTrades) {

    if (earliestTradeId == null) {
      earliestTradeId = coinbaseProTrades.getEarliestTradeId();
    } else if (coinbaseProTrades.getEarliestTradeId() != null) {
      earliestTradeId = Math.min(earliestTradeId, coinbaseProTrades.getEarliestTradeId());
    }

    if (latestTradeId == null) {
      latestTradeId = coinbaseProTrades.getLatestTradeId();
    } else if (coinbaseProTrades.getLatestTradeId() != null) {
      latestTradeId = Math.max(latestTradeId, coinbaseProTrades.getLatestTradeId());
    }

    return super.addAll(coinbaseProTrades);
  }

  @Override
  public void setResponseHeaders(Map<String, List<String>> headers) {
    this.headers = headers;
    earliestTradeId = getHeaderAsLong("Cb-After");
    latestTradeId = getHeaderAsLong("Cb-Before");
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
