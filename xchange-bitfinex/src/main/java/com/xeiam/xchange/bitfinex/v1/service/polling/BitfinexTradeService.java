package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitfinex.v1.BitfinexAdapters;
import com.xeiam.xchange.bitfinex.v1.BitfinexOrderType;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;
import com.xeiam.xchange.utils.DateUtils;

public class BitfinexTradeService extends BitfinexTradeServiceRaw implements PollingTradeService {

  private static final OpenOrders noOpenOrders = new OpenOrders(new ArrayList<LimitOrder>());

  public BitfinexTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    BitfinexOrderStatusResponse[] activeOrders = getBitfinexOpenOrders();

    if (activeOrders.length <= 0) {
      return noOpenOrders;
    } else {
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
  public UserTrades getTradeHistory(Object... arguments) throws IOException {

    String symbol = "btcusd";
    long timestamp = 0;
    int limit = 50;

    if (arguments.length >= 1) {
      if (arguments[0] instanceof CurrencyPair) {
        final CurrencyPair pair = (CurrencyPair) arguments[0];
        symbol = pair.baseSymbol + pair.counterSymbol;
      } else {
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
   * @param params Implementation of {@link TradeHistoryParamCurrencyPair} is mandatory. Can optionally implement {@link TradeHistoryParamPaging} and
   *        {@link TradeHistoryParamsTimeSpan#getStartTime()}. All other TradeHistoryParams types will be ignored.
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    final String symbol;
    if (params instanceof TradeHistoryParamCurrencyPair && ((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null) {
      symbol = BitfinexAdapters.adaptCurrencyPair(((TradeHistoryParamCurrencyPair) params).getCurrencyPair());
    } else {
      // Exchange will return the errors below if CurrencyPair is not provided.
      // field not on request: "Key symbol was not present."
      // field supplied but blank: "Key symbol may not be the empty string"
      throw new ExchangeException("CurrencyPair must be supplied");
    }

    final long timestamp;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      Date startTime = ((TradeHistoryParamsTimeSpan) params).getStartTime();
      timestamp = DateUtils.toUnixTime(startTime);
    } else {
      timestamp = 0;
    }

    final int limit;
    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      Integer pageLength = pagingParams.getPageLength();
      Integer pageNum = pagingParams.getPageNumber();
      limit = (pageLength != null && pageNum != null) ? pageLength * (pageNum + 1) : 50;
    } else {
      limit = 50;
    }

    final BitfinexTradeResponse[] trades = getBitfinexTradeHistory(symbol, timestamp, limit);
    return BitfinexAdapters.adaptTradeHistory(trades, symbol);
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    return new BitfinexTradeHistoryParams(new Date(0), 50, CurrencyPair.BTC_USD);
  }

  public static class BitfinexTradeHistoryParams extends DefaultTradeHistoryParamsTimeSpan
      implements TradeHistoryParamCurrencyPair, TradeHistoryParamPaging {

    private int count;
    private CurrencyPair pair;
    private Integer pageNumber;

    public BitfinexTradeHistoryParams(Date startTime, int count, CurrencyPair pair) {

      super(startTime);

      this.count = count;
      this.pair = pair;
    }

    @Override
    public void setPageLength(Integer count) {

      this.count = count;
    }

    @Override
    public Integer getPageLength() {

      return count;
    }

    @Override
    public void setPageNumber(Integer pageNumber) {

      this.pageNumber = pageNumber;
    }

    @Override
    public Integer getPageNumber() {

      return pageNumber;
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
