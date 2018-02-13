package org.knowm.xchange.cexio.dto;

/**
 * @author ujjwal on 13/02/18.
 */
public abstract class CexIOApiResponse {
  private final String e;
  private final Object data;
  private final String ok;
  private final String error;

  protected CexIOApiResponse(String e, Object data, String ok, String error) {
    this.e = e;
    this.data = data;
    this.ok = ok;
    this.error = error;
  }

  public String getE() {
    return e;
  }

  public Object getData() {
    return data;
  }

  public String getOk() {
    return ok;
  }

  public String getError() {
    return error;
  }
}
