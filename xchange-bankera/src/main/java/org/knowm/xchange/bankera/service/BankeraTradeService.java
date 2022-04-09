package org.knowm.xchange.bankera.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bankera.BankeraAdapters;
import org.knowm.xchange.bankera.dto.trade.BankeraOrder;
import org.knowm.xchange.bankera.dto.trade.BankeraUserTrades;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.*;

public class BankeraTradeService extends BankeraTradeServiceRaw implements TradeService {

  public BankeraTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(null);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return new OpenOrders(BankeraAdapters.adaptOpenOrders(getBankeraOpenOrders(params)));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    BankeraOrder order = placeBankeraMarketOrder(marketOrder);
    return Long.toString(order.getId());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    BankeraOrder order = placeBankeraLimitOrder(limitOrder);
    return Long.toString(order.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    BankeraOrder order = cancelBankeraOrder(orderId);
    return order != null;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    CurrencyPair currencyPair = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      TradeHistoryParamCurrencyPair tradeHistoryParamCurrencyPair =
          (TradeHistoryParamCurrencyPair) params;
      currencyPair = tradeHistoryParamCurrencyPair.getCurrencyPair();
    }

    BankeraUserTrades trades = getUserTrades(currencyPair);
    return new UserTrades(
        BankeraAdapters.adaptUserTrades(trades), Trades.TradeSortType.SortByTimestamp);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamCurrencyPair();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new BankeraOpenOrderParams();
  }

  public static class BankeraOpenOrderParams
      implements OpenOrdersParams,
          OpenOrdersParamLimit,
          OpenOrdersParamCurrencyPair,
          OpenOrdersParamOffset {

    private Integer limit = 100;
    private Integer offset = 0;
    private CurrencyPair currencyPair;

    public BankeraOpenOrderParams() {}

    @Override
    public boolean accept(LimitOrder order) {
      return OpenOrdersParamCurrencyPair.super.accept(order);
    }

    @Override
    public Integer getLimit() {
      return limit;
    }

    @Override
    public void setLimit(Integer limit) {
      this.limit = limit;
    }

    @Override
    public Integer getOffset() {
      return offset;
    }

    @Override
    public void setOffset(Integer offset) {
      this.offset = offset;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {
      this.currencyPair = pair;
    }
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    List<Order> orders = new ArrayList<>(orderQueryParams.length);

    for (OrderQueryParams orderQueryParam : orderQueryParams) {
      BankeraOrder order = getUserOrder(orderQueryParam.getOrderId());
      orders.add(BankeraAdapters.adaptOrder(order));
    }

    return orders;
  }
}
