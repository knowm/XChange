package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcurex.BitcurexAdapters;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the generic market data service for Bitcurex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcurexMarketDataService extends BitcurexMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitcurexMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // get data
    BitcurexTicker bitcurexTicker = getBitcurexTicker(currencyPair.counterSymbol);

    // Adapt to XChange DTOs
    return BitcurexAdapters.adaptTicker(bitcurexTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // get data
    BitcurexDepth bitcurexDepth = getBitcurexOrderBook(currencyPair.counterSymbol);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = BitcurexAdapters.adaptOrders(bitcurexDepth.getAsks(), currencyPair, OrderType.ASK, "");
    List<LimitOrder> bids = BitcurexAdapters.adaptOrders(bitcurexDepth.getBids(), currencyPair, OrderType.BID, "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    // get data
    BitcurexTrade[] bitcurexTrades = getBitcurexTrades(currencyPair.counterSymbol);

    // Adapt to XChange DTOs
    return BitcurexAdapters.adaptTrades(bitcurexTrades, currencyPair);
  }

}
