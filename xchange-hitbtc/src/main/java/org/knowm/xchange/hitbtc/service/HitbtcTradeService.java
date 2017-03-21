package org.knowm.xchange.hitbtc.service;

import java.io.IOException;
import java.util.Collection;

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
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOrder;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class HitbtcTradeService extends HitbtcTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HitbtcTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    HitbtcOrder[] openOrdersRaw = getOpenOrdersRaw();
    return HitbtcAdapters.adaptOpenOrders(openOrdersRaw);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    HitbtcExecutionReport placeMarketOrderRaw = placeMarketOrderRaw(marketOrder);
    checkRejected(placeMarketOrderRaw);
    return placeMarketOrderRaw.getClientOrderId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    HitbtcExecutionReport placeLimitOrderRaw = placeLimitOrderRaw(limitOrder);
    checkRejected(placeLimitOrderRaw);
    return placeLimitOrderRaw.getClientOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    HitbtcExecutionReportResponse cancelOrderRaw = cancelOrderRaw(orderId);
    return cancelOrderRaw.getCancelReject() == null && cancelOrderRaw.getExecutionReport() != null;
  }

  /**
   * Required parameters: {@link TradeHistoryParamPaging} {@link TradeHistoryParamCurrencyPair}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
    Integer count = pagingParams.getPageLength();
    if (count == null) {
      count = 1000;
    }

    Integer pageNumber = pagingParams.getPageNumber();
    int offset = count * (pageNumber != null ? pageNumber : 0);

    CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    if (pair == null) {
      pair = CurrencyPair.BTC_USD;
    }

    HitbtcOwnTrade[] tradeHistoryRaw = getTradeHistoryRaw(offset, count, HitbtcAdapters.adaptCurrencyPair(pair));
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

    public HitbtcTradeHistoryParams(Integer pageLength) {

      super(pageLength);
    }

    public HitbtcTradeHistoryParams(Integer pageNumber, Integer pageLength) {

      super(pageLength, pageNumber);
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
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
