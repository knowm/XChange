package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOwnTrade;
import org.knowm.xchange.hitbtc.v2.internal.HitbtcAdapters;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class HitbtcTradeService extends HitbtcTradeServiceRaw implements TradeService {

  public HitbtcTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    List<HitbtcOrder> openOrdersRaw = getOpenOrdersRaw();
    return HitbtcAdapters.adaptOpenOrders(openOrdersRaw);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    HitbtcExecutionReport placeMarketOrderRaw = placeMarketOrderRaw(marketOrder);
    return placeMarketOrderRaw.getClientOrderId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    HitbtcExecutionReport placeLimitOrderRaw = placeLimitOrderRaw(limitOrder);
    return placeLimitOrderRaw.getClientOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    HitbtcExecutionReportResponse cancelOrderRaw = cancelOrderRaw(orderId);
    return cancelOrderRaw.getCancelReject() == null && cancelOrderRaw.getExecutionReport() != null;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    if (orderParams instanceof CancelOrderByIdParams) {
      cancelOrder(((CancelOrderByIdParams) orderParams).orderId);
    }
    return false;
  }

  /**
   * Required parameters: {@link TradeHistoryParamPaging} {@link TradeHistoryParamCurrencyPair}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    int count = 1000;
    int offset = 0;

    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      if (pagingParams.getPageLength() != null) {
        count = pagingParams.getPageLength();
      }

      Integer pageNumber = pagingParams.getPageNumber();
      offset = count * (pageNumber != null ? pageNumber : 0);
    }

    String symbols = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      TradeHistoryParamCurrencyPair tradeHistoryParamCurrencyPair = (TradeHistoryParamCurrencyPair) params;
      CurrencyPair pair = tradeHistoryParamCurrencyPair.getCurrencyPair();
      symbols = HitbtcAdapters.adaptCurrencyPair(pair);
    }

    List<HitbtcOwnTrade> tradeHistoryRaw = getTradeHistoryRaw(offset, count, symbols);
    return HitbtcAdapters.adaptTradeHistory(tradeHistoryRaw, exchange.getExchangeMetaData());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new HitbtcTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {

    return null;
  }

  public static class HitbtcTradeHistoryParams extends DefaultTradeHistoryParamPaging implements TradeHistoryParamCurrencyPair {

    private CurrencyPair pair;

    public HitbtcTradeHistoryParams() {
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {

      this.pair = pair;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return pair;
    }
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public void verifyOrder(LimitOrder limitOrder) {

  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) {

  }
}
