package org.knowm.xchange.cryptowatch.dto.marketdata.results;

/**
 * @param <V>
 * @author massi.gerardi
 */
public class CryptowatchResult<V> {

  private final V result;
  private final String error;
  private final Allowance allowance;

  public CryptowatchResult(V result, String error, Allowance allowance) {
    this.result = result;
    this.error = error;
    this.allowance = allowance;
  }

  public String getError() {
    return error;
  }

  public V getResult() {
    return result;
  }

  @Override
  public String toString() {
    return "CryptowatchResult{" + "result=" + result + ", error='" + error + '\'' + '}';
  }

  public boolean isSuccess() {
    return error == null;
  }
}
