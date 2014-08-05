package com.xeiam.xchange.vaultofsatoshi.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiAdapters;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosDepth;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTrade;

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

    Integer round = 2;

    Integer count = 100;

    Integer groupOrders = 1;

    if (args.length > 0) {
      round = ((Number) args[0]).intValue();
      if (args.length > 1) {
        count = ((Number) args[1]).intValue();
        if (args.length > 2) {
          groupOrders = ((Number) args[2]).intValue();
        }
      }
    }

    // Request data
    VosDepth vaultOfSatoshiDepth = getVosOrderBook(currencyPair, round, count, groupOrders);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = VaultOfSatoshiAdapters.adaptOrders(vaultOfSatoshiDepth.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids = VaultOfSatoshiAdapters.adaptOrders(vaultOfSatoshiDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Integer count = 100;
    Long sinceId = null;

    if (args.length > 0) {
      count = ((Number) args[0]).intValue();
      if (args.length > 1) {
      }
      sinceId = ((Number) args[1]).longValue();
    }

    // Request data
    VosTrade[] vosTrades = getVosTrades(currencyPair, count, sinceId);

    // Adapt to XChange DTOs
    return VaultOfSatoshiAdapters.adaptTrades(vosTrades, currencyPair);
  }

}
