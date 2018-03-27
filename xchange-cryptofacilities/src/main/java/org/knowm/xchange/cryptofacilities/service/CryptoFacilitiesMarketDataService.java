package org.knowm.xchange.cryptofacilities.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptofacilities.CryptoFacilitiesAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesMarketDataService extends CryptoFacilitiesMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return CryptoFacilitiesAdapters.adaptTicker(
        getCryptoFacilitiesTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return CryptoFacilitiesAdapters.adaptOrderBook(getCryptoFacilitiesOrderBook(currencyPair));
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
