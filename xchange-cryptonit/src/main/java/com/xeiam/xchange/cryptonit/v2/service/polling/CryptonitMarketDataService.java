package com.xeiam.xchange.cryptonit.v2.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptonit.v2.CryptonitAdapters;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for Cryptonit
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */

public class CryptonitMarketDataService extends CryptonitMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CryptonitMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Adapt to XChange DTOs
    return CryptonitAdapters.adaptTicker(getCryptonitTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // Adapt to XChange DTOs
    List<LimitOrder> asks = CryptonitAdapters.adaptOrders(getCryptonitAsks(currencyPair, 1000), currencyPair, "ask", "");
    List<LimitOrder> bids = CryptonitAdapters.adaptOrders(getCryptonitBids(currencyPair, 1000), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    CryptonitOrders cryptonitTrades = getCryptonitTrades(currencyPair, 1000);

    // Adapt to XChange DTOs
    return CryptonitAdapters.adaptTrades(cryptonitTrades, currencyPair);
  }

}
