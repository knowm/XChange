package com.xeiam.xchange.virtex.v2.service.polling;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.virtex.v2.VirtExAdapters;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTrade;

/**
 * <p>
 * Implementation of the market data service for VirtEx
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VirtExMarketDataService extends VirtExMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VirtExMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Adapt to XChange DTOs
    return VirtExAdapters.adaptTicker(getVirtExTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    VirtExDepth virtExDepth = getVirtExOrderBook(currencyPair);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = VirtExAdapters.adaptOrders(virtExDepth.getAsks(), currencyPair, "ask", "");
    Collections.reverse(asks);
    List<LimitOrder> bids = VirtExAdapters.adaptOrders(virtExDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    List<VirtExTrade> virtExTrades = getVirtExTrades(currencyPair);

    // Adapt to XChange DTOs
    return VirtExAdapters.adaptTrades(virtExTrades, currencyPair);
  }

}
