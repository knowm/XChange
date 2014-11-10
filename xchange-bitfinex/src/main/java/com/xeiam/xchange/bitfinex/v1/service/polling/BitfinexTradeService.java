package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.util.ArrayList;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bitfinex.v1.BitfinexAdapters;
import com.xeiam.xchange.bitfinex.v1.BitfinexOrderType;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.*;

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
  public UserTrades getTradeHistory(final Object... arguments) throws IOException {

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

  /**
   * Required parameters:
   * {@link TradeHistoryParamCount#getCount()}
   * {@link TradeHistoryParamsTimeSpan#getStartTime()}
   * {@link TradeHistoryParamCurrencyPair#getCurrencyPair()}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    String symbol = BitfinexAdapters.adaptCurrencyPair(((TradeHistoryParamCurrencyPair) params).getCurrencyPair());
    Long timestamp = ((TradeHistoryParamsTimeSpan) params).getStartTime();
    Integer limit = ((TradeHistoryParamCount) params).getCount();

    final BitfinexTradeResponse[] trades = getBitfinexTradeHistory(symbol, timestamp, limit);
    return BitfinexAdapters.adaptTradeHistory(trades, symbol);
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.TradeHistoryParams createTradeHistoryParams() {

    return new BitfinexTradeHistoryParams(0L, 50, CurrencyPair.BTC_USD);
  }

  public static class BitfinexTradeHistoryParams extends TradeHistoryParamsTimeSpanImpl implements TradeHistoryParamCurrencyPair, TradeHistoryParamCount {

    private int count;
    private CurrencyPair pair;

    public BitfinexTradeHistoryParams(long startTime, int count, CurrencyPair pair) {
      super(startTime);
      this.count = count;
      this.pair = pair;
    }

    @Override
    public void setCount(Integer count) {

      this.count = count;
    }

    @Override
    public Integer getCount() {

      return count;
    }

    @Override
    public CurrencyPair getCurrencyPair() {

      return pair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {

      this.pair = pair;
    }
  }
}
