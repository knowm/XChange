package org.knowm.xchange.vircurex.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.vircurex.VircurexAdapters;
import org.knowm.xchange.vircurex.dto.marketdata.VircurexDepth;
import org.knowm.xchange.vircurex.dto.marketdata.VircurexLastTrade;

/**
 * Implementation of the market data service for Vircurex
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class VircurexMarketDataService extends VircurexMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VircurexMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    VircurexLastTrade vircurexLastTrade = getVircurexTicker(currencyPair);

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(vircurexLastTrade.getValue())
        .build();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    VircurexDepth vircurexDepth = getVircurexOrderBook(currencyPair);

    // Adapt to XChange DTOs
    List<LimitOrder> asks =
        VircurexAdapters.adaptOrders(vircurexDepth.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids =
        VircurexAdapters.adaptOrders(vircurexDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
