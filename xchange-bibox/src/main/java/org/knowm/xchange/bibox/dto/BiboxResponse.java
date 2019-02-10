package org.knowm.xchange.bibox.dto;

/**
 * General Bibox response
 *
 * @param <T> Result type
 * @author odrotleff
 */
public class BiboxResponse<T> {
  protected T result;
  protected String cmd;
  protected BiboxError error = null;

  public T getResult() {
    return result;
  }

  public void setResult(T result) {
    this.result = result;
  }

  public String getCmd() {
    return cmd;
  }

  public void setCmd(String cmd) {
    this.cmd = cmd;
  }

  public BiboxError getError() {
    return error;
  }

  public void setError(BiboxError error) {
    this.error = error;
  }
}
