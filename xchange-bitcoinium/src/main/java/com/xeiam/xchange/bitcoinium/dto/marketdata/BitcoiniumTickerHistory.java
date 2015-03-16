package com.xeiam.xchange.bitcoinium.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Ticker History from Bitcoinium Web Service
 * </p>
 */
public final class BitcoiniumTickerHistory {

  private final BitcoiniumTicker bitcoiniumTicker;
  private final BitcoiniumTicker[] condensedTickers;

  /**
   * Constructor
   *
   * @param bitcoiniumTicker
   * @param condensedTickers
   */
  public BitcoiniumTickerHistory(@JsonProperty("ticker") BitcoiniumTicker bitcoiniumTicker,
      @JsonProperty("condensedTickers") BitcoiniumTicker[] condensedTickers) {

    this.bitcoiniumTicker = bitcoiniumTicker;
    this.condensedTickers = condensedTickers;
  }

  public BitcoiniumTicker getBitcoiniumTicker() {

    return bitcoiniumTicker;
  }

  public BitcoiniumTicker[] getCondensedTickers() {

    return condensedTickers;
  }
}
