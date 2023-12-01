package org.knowm.xchange.cexio.dto;

/**
 * @author ujjwal on 13/02/18.
 */
public abstract class CexIOApiResponse<T> {
  private final String e;
  private final T data;
  private final String ok;
  private final String error;

  protected CexIOApiResponse(String e, T data, String ok, String error) {
    this.e = e;
    this.data = data;
    this.ok = ok;
    this.error = error;
  }

  public String getE() {
    return e;
  }

  public T getData() {
    return data;
  }

  public String getOk() {
    return ok;
  }

  public String getError() {
    return error;
  }
}
