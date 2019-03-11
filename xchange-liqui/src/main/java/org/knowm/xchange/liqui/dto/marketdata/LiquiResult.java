package org.knowm.xchange.liqui.dto.marketdata;

public class LiquiResult<V> {

  private final V result;
  private final String error;

  public LiquiResult(final V result, final String error) {
    this.result = result;
    this.error = error;
  }

  public V getResult() {
    return result;
  }

  public String getError() {
    return error;
  }

  @Override
  public String toString() {
    return "LiquiResult{" + "result=" + result + ", error='" + error + '\'' + '}';
  }
}
