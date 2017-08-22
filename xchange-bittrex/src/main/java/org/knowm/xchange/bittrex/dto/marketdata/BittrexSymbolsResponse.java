package org.knowm.xchange.bittrex.dto.marketdata;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexSymbolsResponse {

  private final boolean success;
  private final String message;
  private final ArrayList<BittrexSymbol> symbols;

  public BittrexSymbolsResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message,
      @JsonProperty("result") ArrayList<BittrexSymbol> symbols) {

    this.success = success;
    this.message = message;
    this.symbols = symbols;

  }

  public boolean isSuccess() {

    return success;
  }

  public String getMessage() {

    return message;
  }

  public ArrayList<BittrexSymbol> getSymbols() {

    return symbols;
  }

  @Override
  public String toString() {

    return "BittrexTickerResponse [success=" + success + ", message=" + message + ", symbols " + symbols.size() + "=" + symbols + "]";
  }

}
