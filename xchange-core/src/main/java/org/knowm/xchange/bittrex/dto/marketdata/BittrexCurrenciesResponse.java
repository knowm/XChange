package org.knowm.xchange.bittrex.dto.marketdata;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexCurrenciesResponse {

  private final boolean success;
  private final String message;
  private final BittrexCurrency[] currencies;

  public BittrexCurrenciesResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message,
      @JsonProperty("result") BittrexCurrency[] currencies) {

    this.success = success;
    this.message = message;
    this.currencies = currencies;

  }

  public boolean isSuccess() {

    return success;
  }

  public String getMessage() {

    return message;
  }

  public BittrexCurrency[] getCurrencies() {

    return currencies;
  }

  @Override
  public String toString() {

    return "BittrexCurrenciesResponse [success=" + success + ", message=" + message + ", currencies=" + Arrays.toString(currencies) + "]";
  }
}
