package org.knowm.xchange.paymium.dto;

public class PaymiumBaseResponse {

  private final String error;

  protected PaymiumBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
