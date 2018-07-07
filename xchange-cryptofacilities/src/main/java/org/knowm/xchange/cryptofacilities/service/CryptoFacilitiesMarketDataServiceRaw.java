package org.knowm.xchange.cryptofacilities.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesInstruments;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrderBook;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesPublicFills;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTicker;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTickers;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesMarketDataServiceRaw extends CryptoFacilitiesBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CryptoFacilitiesTicker getCryptoFacilitiesTicker(CurrencyPair currencyPair)
      throws IOException {

    CryptoFacilitiesTicker ticker =
        getCryptoFacilitiesTickers().getTicker(currencyPair.base.toString());

    return ticker;
  }

  public CryptoFacilitiesTickers getCryptoFacilitiesTickers() throws IOException {

    CryptoFacilitiesTickers tickers = cryptoFacilities.getTickers();

    if (tickers.isSuccess()) {
      return tickers;
    } else {
      throw new ExchangeException("Error getting CF tickers: " + tickers.getError());
    }
  }

  public CryptoFacilitiesInstruments getCryptoFacilitiesInstruments() throws IOException {

    CryptoFacilitiesInstruments instruments = cryptoFacilities.getInstruments();

    if (instruments.isSuccess()) {
      return instruments;
    } else {
      throw new ExchangeException("Error getting CF instruments: " + instruments.getError());
    }
  }

  public CryptoFacilitiesOrderBook getCryptoFacilitiesOrderBook(CurrencyPair currencyPair)
      throws IOException {

    CryptoFacilitiesOrderBook orderBook =
        cryptoFacilities.getOrderBook(currencyPair.base.toString());

    if (orderBook.isSuccess()) {
      orderBook.setCurrencyPair(currencyPair);
      return orderBook;
    } else {
      throw new ExchangeException("Error getting CF order book: " + orderBook.getError());
    }
  }

  public CryptoFacilitiesPublicFills getCryptoFacilitiesHistory(CurrencyPair currencyPair)
      throws IOException {

    CryptoFacilitiesPublicFills publicFills =
        cryptoFacilities.getHistory(currencyPair.base.toString());

    if (publicFills.isSuccess()) {
      publicFills.setCurrencyPair(currencyPair);
      return publicFills;
    } else {
      throw new ExchangeException("Error getting CF public fills: " + publicFills.getError());
    }
  }
}
