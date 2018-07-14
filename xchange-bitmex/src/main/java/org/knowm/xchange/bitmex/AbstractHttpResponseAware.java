package org.knowm.xchange.bitmex;

import java.util.List;
import java.util.Map;
import si.mazi.rescu.HttpResponseAware;

/** @author Nikita Belenkiy on 02/07/2018. */
public class AbstractHttpResponseAware implements HttpResponseAware {
  private Map<String, List<String>> headers;

  @Override
  public void setResponseHeaders(Map<String, List<String>> headers) {
    this.headers = headers;
  }

  @Override
  public Map<String, List<String>> getResponseHeaders() {
    return this.headers;
  }
}
