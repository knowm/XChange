package org.knowm.xchange.coinbasepro.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import si.mazi.rescu.HttpResponseAware;

public class CoinbasePagedResponse<T> extends ArrayList<T> implements HttpResponseAware {

  @JsonIgnore private Map<String, List<String>> headers;

  @Override
  public void setResponseHeaders(Map<String, List<String>> headers) {
    this.headers = headers;
  }

  @Override
  public Map<String, List<String>> getResponseHeaders() {
    return headers;
  }

  public String getBefore() {
    return headers.getOrDefault("Cb-Before", Collections.emptyList()).stream()
        .findFirst()
        .orElse(null);
  }

  public String getAfter() {
    return headers.getOrDefault("Cb-After", Collections.emptyList()).stream()
        .findFirst()
        .orElse(null);
  }
}
