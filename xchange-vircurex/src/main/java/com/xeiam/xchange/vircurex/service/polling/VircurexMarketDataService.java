package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.vircurex.VircurexAdapters;
import com.xeiam.xchange.vircurex.dto.marketdata.VircurexDepth;

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
   * @param exchangeSpecification
   */
  public VircurexMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    VircurexDepth vircurexDepth = getVircurexOrderBook(currencyPair);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = VircurexAdapters.adaptOrders(vircurexDepth.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids = VircurexAdapters.adaptOrders(vircurexDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(new Date(), asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
