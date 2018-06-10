package org.knowm.xchange.independentreserve.service;

import java.io.IOException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.independentreserve.IndependentReserveAdapters;
import org.knowm.xchange.independentreserve.IndependentReserveExchange;
import org.knowm.xchange.independentreserve.dto.marketdata.IndependentReserveTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Author: Kamil Zbikowski Date: 4/9/15
 *
 * @author Stuart Low <stuart@bizabank.com>
 */
public class IndependentReserveMarketDataService extends IndependentReserveMarketDataServiceRaw
    implements MarketDataService {
  public IndependentReserveMarketDataService(
      IndependentReserveExchange independentReserveExchange) {
    super(independentReserveExchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    IndependentReserveTicker t =
        getIndependentReserveTicker(currencyPair.base.toString(), currencyPair.counter.toString());
    return IndependentReserveAdapters.adaptTicker(t, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return IndependentReserveAdapters.adaptOrderBook(
        getIndependentReserveOrderBook(
            currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()));
  }
}
