package org.knowm.xchange.coinbene;

import si.mazi.rescu.HttpStatusExceptionSupport;

public class CoinbeneException extends HttpStatusExceptionSupport {
  public CoinbeneException(String description) {
    super(description);
  }
}
