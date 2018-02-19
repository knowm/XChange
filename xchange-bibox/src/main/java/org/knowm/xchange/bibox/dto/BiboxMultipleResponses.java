package org.knowm.xchange.bibox.dto;

import java.util.List;

/**
 * Result of batched calls (all POST requests)
 * 
 * @author odrotleff
 * @param <R>
 */
public class BiboxMultipleResponses<R> {

  private List<BiboxResponse<R>> result;

  public BiboxMultipleResponses() {}

  public List<BiboxResponse<R>> getResult() {
    return result;
  }

  public void setResult(List<BiboxResponse<R>> result) {
    this.result = result;
  }
}
