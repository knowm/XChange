package org.knowm.xchange.campbx.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.campbx.CampBXAdapters;
import org.knowm.xchange.campbx.dto.marketdata.CampBXOrderBook;
import org.knowm.xchange.campbx.dto.marketdata.CampBXTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author Matija Mazi */
public class CampBXMarketDataService extends CampBXMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CampBXMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    CampBXTicker campbxTicker = getCampBXTicker();

    return CampBXAdapters.adaptTicker(campbxTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    CampBXOrderBook campBXOrderBook = getCampBXOrderBook();

    return CampBXAdapters.adaptOrders(campBXOrderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
