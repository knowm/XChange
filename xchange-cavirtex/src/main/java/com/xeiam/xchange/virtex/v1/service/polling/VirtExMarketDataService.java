package com.xeiam.xchange.virtex.v1.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.virtex.v1.VirtExAdapters;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTrade;

/**
 * <p>
 * Implementation of the market data service for VirtEx
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */

@Deprecated
public class VirtExMarketDataService extends VirtExMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public VirtExMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Adapt to XChange DTOs
    return VirtExAdapters.adaptTicker(getVirtExTicker(currencyPair.counterSymbol), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    VirtExDepth virtExDepth = getVirtExOrderBook(currencyPair.counterSymbol);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = VirtExAdapters.adaptOrders(virtExDepth.getAsks(), currencyPair.counterSymbol, "ask", "");
    List<LimitOrder> bids = VirtExAdapters.adaptOrders(virtExDepth.getBids(), currencyPair.counterSymbol, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    VirtExTrade[] virtExTrades = getVirtExTrades(currencyPair.counterSymbol);

    // Adapt to XChange DTOs
    return VirtExAdapters.adaptTrades(virtExTrades, currencyPair);
  }

}
