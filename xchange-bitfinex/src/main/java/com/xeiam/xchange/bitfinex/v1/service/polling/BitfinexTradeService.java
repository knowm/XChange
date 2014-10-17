package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.util.ArrayList;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.BitfinexAdapters;
import com.xeiam.xchange.bitfinex.v1.BitfinexOrderType;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BitfinexTradeService extends BitfinexTradeServiceRaw implements PollingTradeService {

  private static final OpenOrders noOpenOrders = new OpenOrders(new ArrayList<LimitOrder>());

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitfinexTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    BitfinexOrderStatusResponse[] activeOrders = getBitfinexOpenOrders();

    if (activeOrders.length <= 0) {
      return noOpenOrders;
    }
    else {
      return BitfinexAdapters.adaptOrders(activeOrders);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    BitfinexOrderStatusResponse newOrder = placeBitfinexMarketOrder(marketOrder, BitfinexOrderType.MARKET);

    return String.valueOf(newOrder.getId());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    BitfinexOrderStatusResponse newOrder = placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.LIMIT, false);

    return String.valueOf(newOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return cancelBitfinexOrder(orderId);
  }

  @Override
  public Trades getTradeHistory(final Object... arguments) throws IOException {

    String symbol = "btcusd";
    long timestamp = 0;
    int limit = 50;

    if (arguments.length >= 1) {
      if (arguments[0] instanceof CurrencyPair) {
        final CurrencyPair pair = (CurrencyPair) arguments[0];
        symbol = pair.baseSymbol + pair.counterSymbol;
      }
      else {
        symbol = (String) arguments[0];
      }
    }
    if (arguments.length >= 2) {
      timestamp = (Long) arguments[1];
    }
    if (arguments.length >= 3) {
      limit = (Integer) arguments[2];
    }

    final BitfinexTradeResponse[] trades = getBitfinexTradeHistory(symbol, timestamp, limit);

    return BitfinexAdapters.adaptTradeHistory(trades, symbol);
  }
}
