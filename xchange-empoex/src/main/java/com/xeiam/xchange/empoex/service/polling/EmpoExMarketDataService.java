package com.xeiam.xchange.empoex.service.polling;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.empoex.EmpoExAdapters;
import com.xeiam.xchange.empoex.EmpoExUtils;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExLevel;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExTicker;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExTrade;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for EmpoEx
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class EmpoExMarketDataService extends EmpoExMarketDataServiceRaw implements PollingMarketDataService {

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
