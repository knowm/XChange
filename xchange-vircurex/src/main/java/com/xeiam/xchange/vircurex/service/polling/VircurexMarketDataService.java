package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.vircurex.VircurexAdapters;
import com.xeiam.xchange.vircurex.dto.marketdata.VircurexDepth;
import com.xeiam.xchange.vircurex.dto.marketdata.VircurexLastTrade;

/**
 * <p>
 * Implementation of the market data service for Vircurex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VircurexMarketDataService extends VircurexMarketDataServiceRaw implements PollingMarketDataService {

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

    return new Ticker.Builder().currencyPair(currencyPair).last(vircurexLastTrade.getValue()).build();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    VircurexDepth vircurexDepth = getVircurexOrderBook(currencyPair);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = VircurexAdapters.adaptOrders(vircurexDepth.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids = VircurexAdapters.adaptOrders(vircurexDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
