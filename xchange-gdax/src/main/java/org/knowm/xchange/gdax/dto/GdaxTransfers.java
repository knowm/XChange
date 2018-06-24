package org.knowm.xchange.gdax.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import si.mazi.rescu.HttpResponseAware;

public class GdaxTransfers extends ArrayList<GdaxTransfer> implements HttpResponseAware {
  private Map<String, List<String>> headers;

  @Override
  public void setResponseHeaders(Map<String, List<String>> headers) {
    this.headers = headers;
  }

  @Override
  public Map<String, List<String>> getResponseHeaders() {
    return headers;
  }

  public String getHeader(String key) {
    return getResponseHeaders().get(key).get(0);
  }
}
