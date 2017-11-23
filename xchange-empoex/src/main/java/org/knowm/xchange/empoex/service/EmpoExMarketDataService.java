package org.knowm.xchange.empoex.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.empoex.EmpoExAdapters;
import org.knowm.xchange.empoex.EmpoExUtils;
import org.knowm.xchange.empoex.dto.marketdata.EmpoExLevel;
import org.knowm.xchange.empoex.dto.marketdata.EmpoExTicker;
import org.knowm.xchange.empoex.dto.marketdata.EmpoExTrade;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * <p>
 * Implementation of the market data service for EmpoEx
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class EmpoExMarketDataService extends EmpoExMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public EmpoExMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    EmpoExTicker empoExTicker = super.getEmpoExTicker(currencyPair);
    return EmpoExAdapters.adaptEmpoExTicker(empoExTicker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairString = EmpoExUtils.toPairString(currencyPair);
    Map<String, List<EmpoExLevel>> depth = super.getEmpoExDepth(currencyPair).get(pairString);

    return EmpoExAdapters.adaptEmpoExDepth(depth, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairString = EmpoExUtils.toPairString(currencyPair);
    List<EmpoExTrade> trades = super.getEmpoExTrades(currencyPair).get(pairString);

    return EmpoExAdapters.adaptEmpoExTrades(trades, currencyPair);
  }

}
