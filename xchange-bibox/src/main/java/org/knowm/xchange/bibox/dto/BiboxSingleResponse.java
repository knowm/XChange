package org.knowm.xchange.bibox.dto;

/**
 * Result of batched calls with just one call (which happens most of the time)
 *
 * @author odrotleff
 */
public class BiboxSingleResponse<T> extends BiboxMultipleResponses<T> {

  public BiboxResponse<T> get() {
    return result.get(0);
  }
}
