package org.knowm.xchange.luno.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.luno.LunoUtil;
import org.knowm.xchange.luno.dto.LunoBoolean;
import org.knowm.xchange.luno.dto.trade.LunoPostOrder;
import org.knowm.xchange.luno.dto.trade.LunoUserTrades;
import org.knowm.xchange.luno.dto.trade.State;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class LunoTradeService extends LunoBaseService implements TradeService {

  public LunoTradeService(Exchange exchange) {
    super(exchange);
  }

  private static OrderType convert(org.knowm.xchange.luno.dto.trade.OrderType type) {
    switch (type) {
      case ASK:
      case SELL:
        return OrderType.ASK;
      case BID:
      case BUY:
        return OrderType.BID;
      default:
        throw new ExchangeException("Not supported order type: " + type);
    }
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    List<LimitOrder> list = new ArrayList<>();
    for (org.knowm.xchange.luno.dto.trade.LunoOrders.Order lo :
        lunoAPI.listOrders(State.PENDING, null).getOrders()) {
      list.add(
          new LimitOrder(
              convert(lo.type),
              lo.limitVolume,
              LunoUtil.fromLunoPair(lo.pair),
              lo.orderId,
              lo.getCreationTimestamp(),
              lo.limitPrice));
    }
    return new OpenOrders(list);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    LunoPostOrder postOrder =
        marketOrder.getType() == OrderType.ASK
            ? lunoAPI.postMarketOrder(
                LunoUtil.toLunoPair(marketOrder.getCurrencyPair()),
                org.knowm.xchange.luno.dto.trade.OrderType.SELL,
                null,
                marketOrder.getOriginalAmount(),
                null,
                null)
            : lunoAPI.postMarketOrder(
                LunoUtil.toLunoPair(marketOrder.getCurrencyPair()),
                org.knowm.xchange.luno.dto.trade.OrderType.BUY,
                marketOrder.getOriginalAmount().multiply(marketOrder.getAveragePrice()),
                null,
                null,
                null);
    return postOrder.orderId;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    LunoPostOrder postLimitOrder =
        lunoAPI.postLimitOrder(
            LunoUtil.toLunoPair(limitOrder.getCurrencyPair()),
            convertForLimit(limitOrder.getType()),
            limitOrder.getOriginalAmount(),
            limitOrder.getLimitPrice(),
            null,
            null);
    return postLimitOrder.orderId;
  }

  private org.knowm.xchange.luno.dto.trade.OrderType convertForLimit(OrderType type) {
    switch (type) {
      case ASK:
        return org.knowm.xchange.luno.dto.trade.OrderType.ASK;
      case BID:
        return org.knowm.xchange.luno.dto.trade.OrderType.BID;
      default:
        throw new ExchangeException("Not supported order type: " + type);
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    LunoBoolean stopOrder = lunoAPI.stopOrder(orderId);
    return stopOrder.success;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params)
      throws ExchangeException, IOException {

    if (!(params instanceof TradeHistoryParamCurrencyPair)) {
      throw new ExchangeException("THe currency pair is mandatory in order to get user trades.");
    }
    CurrencyPair currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    Long since = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      since = ((TradeHistoryParamsTimeSpan) params).getStartTime().getTime();
    }
    Integer limit = null;
    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    LunoUserTrades lunoTrades = lunoAPI.listTrades(LunoUtil.toLunoPair(currencyPair), since, limit);
    List<UserTrade> trades = new ArrayList<>();
    for (org.knowm.xchange.luno.dto.trade.LunoUserTrades.UserTrade t : lunoTrades.getTrades()) {
      final CurrencyPair pair = LunoUtil.fromLunoPair(t.pair);
      final String tradeId = null; // currently there is no trade id!
      final BigDecimal feeAmount;
      final Currency feeCurrency;
      if (t.feeBase.compareTo(BigDecimal.ZERO) > 0) {
        feeAmount = t.feeBase;
        feeCurrency = pair.base;
      } else {
        feeAmount = t.feeCounter;
        feeCurrency = pair.counter;
      }
      trades.add(
          new UserTrade.Builder()
              .type(t.buy ? OrderType.BID : OrderType.ASK)
              .originalAmount(t.volume)
              .currencyPair(pair)
              .price(t.price)
              .timestamp(t.getTimestamp())
              .id(tradeId)
              .orderId(t.orderId)
              .feeAmount(feeAmount)
              .feeCurrency(feeCurrency)
              .build());
    }
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new LunoTradeHistoryParams();
  }

  public static class LunoTradeHistoryParams
      implements TradeHistoryParamCurrencyPair, TradeHistoryParamsTimeSpan, TradeHistoryParamLimit {

    CurrencyPair pair;
    private Date startTime;
    private Date endTime;
    private Integer limit;

    @Override
    public Integer getLimit() {
      return limit;
    }

    @Override
    public void setLimit(Integer limit) {
      this.limit = limit;
    }

    @Override
    public Date getStartTime() {
      return startTime;
    }

    @Override
    public void setStartTime(Date startTime) {
      this.startTime = startTime;
    }

    @Override
    public Date getEndTime() {
      return endTime;
    }

    @Override
    public void setEndTime(Date endTime) {
      this.endTime = endTime;
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
