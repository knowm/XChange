package org.knowm.xchange.cryptonit.v2.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit.v2.CryptonitAdapters;
import org.knowm.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Implementation of the market data service for Cryptonit
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class CryptonitMarketDataService extends CryptonitMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptonitMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Adapt to XChange DTOs
    return CryptonitAdapters.adaptTicker(getCryptonitTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // Adapt to XChange DTOs
    List<LimitOrder> asks =
        CryptonitAdapters.adaptOrders(
            getCryptonitAsks(currencyPair, 1000), currencyPair, "ask", "");
    List<LimitOrder> bids =
        CryptonitAdapters.adaptOrders(
            getCryptonitBids(currencyPair, 1000), currencyPair, "bid", "");

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
