package org.knowm.xchange.bitbay.dto;

/**
 * @author kpysniak
 */
public class BitbayBaseResponse {

  private final String error;

  protected BitbayBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
