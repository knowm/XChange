package org.knowm.xchange.bibox.dto;

import java.util.List;

/**
 * Result of batched calls with just one call (which happens most of the time)
 * 
 * @author odrotleff
 */
public class BiboxSingleResponse<T> {

  private List<BiboxResponse<T>> result;

  public BiboxResponse<T> get() {
    return result.get(0);
  }

  public BiboxSingleResponse() {}

  public List<BiboxResponse<T>> getResult() {
    return result;
  }

  public void setResult(List<BiboxResponse<T>> result) {
    this.result = result;
  }
}
