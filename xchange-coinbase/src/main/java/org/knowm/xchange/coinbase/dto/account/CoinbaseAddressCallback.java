package org.knowm.xchange.coinbase.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author jamespedwards42 */
public class CoinbaseAddressCallback {

  @JsonProperty("address")
  private final CoinbaseCallbackUrlParam callbackUrlParam;

  public CoinbaseAddressCallback(String callbackUrl, final String label) {

    this.callbackUrlParam = new CoinbaseCallbackUrlParam(callbackUrl, label);
  }

  @Override
  public String toString() {

    return "CoinbaseAddressCallback [callbackUrlParam=" + callbackUrlParam + "]";
  }

  private static class CoinbaseCallbackUrlParam {

    @JsonProperty("callback_url")
    private final String callbackUrl;

    @JsonProperty("label")
    private final String label;

    private CoinbaseCallbackUrlParam(String callbackUrl, String label) {

      this.callbackUrl = callbackUrl;
      this.label = label;
    }

    @Override
    public String toString() {

      return "CoinbaseCallbackUrlParam [callbackUrl=" + callbackUrl + ", label=" + label + "]";
    }
  }
}
