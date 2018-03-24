package org.knowm.xchange.abucoins.dto;

public class AbucoinsSingleIdRequest extends AbucoinsRequest {
  public final String id;

  public AbucoinsSingleIdRequest(String id) {
    this.id = id;
  }
}
