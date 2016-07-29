package org.knowm.xchange.virtex.v2.service.polling;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;
import org.knowm.xchange.virtex.v2.VirtExAdapters;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExDepth;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExTrade;

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
