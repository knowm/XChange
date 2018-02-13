package org.knowm.xchange.bibox.dto;

import java.util.List;

/**
 * Result of batched calls (all POST requests)
 * 
 * @author odrotleff
 */
public class BiboxMultipleResponses {

  private List<BiboxResponse<?>> result;
  
  public BiboxResponse<?> getFirst() {
    return result.get(0);
  }

  public BiboxMultipleResponses() {}

  public List<BiboxResponse<?>> getResult() {
    return result;
  }

  public void setResult(List<BiboxResponse<?>> result) {
    this.result = result;
  }
}
