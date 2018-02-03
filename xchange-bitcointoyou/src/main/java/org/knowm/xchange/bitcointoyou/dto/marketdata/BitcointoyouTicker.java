package org.knowm.xchange.bitcointoyou.dto.marketdata;

import org.knowm.xchange.currency.CurrencyPair;

/**
 *
 * @author Jonathas Carrijo
 * @author Danilo Guimaraes
 */
public class BitcointoyouTicker {

  private final BitcointoyouMarketData bitcointoyouMarketData;
  private final CurrencyPair currencyPair;

  public BitcointoyouTicker(BitcointoyouMarketData bitcointoyouMarketData, CurrencyPair currencyPair) {

    super();
    this.bitcointoyouMarketData = bitcointoyouMarketData;
    this.currencyPair = currencyPair;
  }

  public BitcointoyouMarketData getBitcointoyouMarketData() {

    return bitcointoyouMarketData;
  }

  public CurrencyPair getCurrencyPair() {

    return currencyPair;
  }

}
