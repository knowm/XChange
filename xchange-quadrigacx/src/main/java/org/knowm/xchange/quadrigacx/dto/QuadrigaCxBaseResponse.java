package org.knowm.xchange.quadrigacx.dto;

public abstract class QuadrigaCxBaseResponse {

  private final String error;

  protected QuadrigaCxBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
