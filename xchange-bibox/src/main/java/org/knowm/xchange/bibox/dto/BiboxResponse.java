package org.knowm.xchange.bibox.dto;

/**
 * General Bibox response
 * 
 * @param <T> Result type  
 *
 * @author odrotleff
 */
public class BiboxResponse<T> {
  private T result;
  private String cmd;

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
}
