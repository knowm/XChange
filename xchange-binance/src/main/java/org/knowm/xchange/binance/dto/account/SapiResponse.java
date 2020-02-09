package org.knowm.xchange.binance.dto.account;

public abstract class SapiResponse<T> {

  public SapiResponse() {
    super();
  }

  public abstract T getData();
}
