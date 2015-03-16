package com.xeiam.xchange.campbx.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.campbx.CampBXAdapters;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXOrderBook;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Matija Mazi
 */
public class CampBXMarketDataService extends CampBXMarketDataServiceRaw implements PollingMarketDataService {

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
