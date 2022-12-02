package org.knowm.xchange.coinjar.dto.trading;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import si.mazi.rescu.HttpResponseAware;

public class CoinjarFills extends ArrayList<CoinjarFill> implements HttpResponseAware {

  private Map<String, List<String>> headers;

  @Override
  public void setResponseHeaders(Map<String, List<String>> headers) {
    this.headers = headers;
  }

  @Override
  public Map<String, List<String>> getResponseHeaders() {
    return headers;
  }

  public String getNextPageCursor() {
    if (headers.containsKey("X-Cjx-Cursor")) {
      return headers.get("X-Cjx-Cursor").get(0);
    } else {
      return null;
    }
  }
}
