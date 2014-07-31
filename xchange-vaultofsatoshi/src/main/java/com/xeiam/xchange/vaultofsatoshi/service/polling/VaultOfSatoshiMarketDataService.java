package com.xeiam.xchange.vaultofsatoshi.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiAdapters;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VaultOfSatoshiDepth;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VaultOfSatoshiTrade;

/**
 * <p>
 * Implementation of the market data service for VaultOfSatoshi
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VaultOfSatoshiMarketDataService extends VaultOfSatoshiMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public VaultOfSatoshiMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Adapt to XChange DTOs
    return VaultOfSatoshiAdapters.adaptTicker(getVosTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    VaultOfSatoshiDepth vaultOfSatoshiDepth = getVosOrderBook(currencyPair);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = VaultOfSatoshiAdapters.adaptOrders(vaultOfSatoshiDepth.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids = VaultOfSatoshiAdapters.adaptOrders(vaultOfSatoshiDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Long sinceId = null;
    int count = 100;

    if (args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof Number) {
        sinceId = ((Number) arg0).longValue();
      }
      else {
        throw new ExchangeException("args[0] must be of type Number!");
      }
      if (args.length > 1) {
        Object arg1 = args[1];
        if (arg1 instanceof Number) {
          count = ((Number) arg1).intValue();
        }
        else {
          throw new ExchangeException("args[1] must be of type Number!");
        }
      }
    }
    // Request data
    List<VaultOfSatoshiTrade> vosTrades = getVosTrades(currencyPair, sinceId, count);

    // Adapt to XChange DTOs
    return VaultOfSatoshiAdapters.adaptTrades(vosTrades, currencyPair);
  }

}
