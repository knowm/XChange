package org.knowm.xchange.cexio.dto;

public class CexioSingleIdRequest extends CexIORequest {
  public final String id;

  public CexioSingleIdRequest(String id) {
    this.id = id;
  }
}
