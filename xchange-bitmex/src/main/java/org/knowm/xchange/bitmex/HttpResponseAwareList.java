package org.knowm.xchange.bitmex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import si.mazi.rescu.HttpResponseAware;

/** @author Nikita Belenkiy on 03/07/2018. */
public class HttpResponseAwareList<E> extends ArrayList<E> implements HttpResponseAware {
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
