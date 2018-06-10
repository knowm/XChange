package org.knowm.xchange.mercadobitcoin.service;

import static org.knowm.xchange.utils.DateUtils.toUnixTimeNullSafe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinAdapters;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinUtils;
import org.knowm.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import org.knowm.xchange.mercadobitcoin.dto.trade.MercadoBitcoinPlaceLimitOrderResult;
import org.knowm.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinTradeService extends MercadoBitcoinTradeServiceRaw
    implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public MercadoBitcoinTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    // TODO use currency pair param
    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> openOrdersBitcoinResult =
        getMercadoBitcoinUserOrders("btc_brl", null, "active", null, null, null, null);
    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> openOrdersLitecoinResult =
        getMercadoBitcoinUserOrders("ltc_brl", null, "active", null, null, null, null);

    List<LimitOrder> limitOrders = new ArrayList<>();

    limitOrders.addAll(
        MercadoBitcoinAdapters.adaptOrders(CurrencyPair.BTC_BRL, openOrdersBitcoinResult));
    limitOrders.addAll(
        MercadoBitcoinAdapters.adaptOrders(
            new CurrencyPair(Currency.LTC, Currency.BRL), openOrdersLitecoinResult));

    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * The result is not the pure order id. It is a composition with the currency pair and the order
   * id (the same format used as parameter of {@link #cancelOrder}). Please see {@link
   * org.knowm.xchange.mercadobitcoin.MercadoBitcoinUtils#makeMercadoBitcoinOrderId} .
   */
  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair;

    if (limitOrder.getCurrencyPair().equals(CurrencyPair.BTC_BRL)) {
      pair = "btc_brl";
    } else if (limitOrder.getCurrencyPair().equals(new CurrencyPair(Currency.LTC, Currency.BRL))) {
      pair = "ltc_brl";
    } else {
      throw new NotAvailableFromExchangeException();
    }

    String type;

    if (limitOrder.getType() == Order.OrderType.BID) {
      type = "buy";
    } else {
      type = "sell";
    }

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult> newOrderResult =
        mercadoBitcoinPlaceLimitOrder(
            pair, type, limitOrder.getOriginalAmount(), limitOrder.getLimitPrice());

    return MercadoBitcoinUtils.makeMercadoBitcoinOrderId(
        limitOrder.getCurrencyPair(), newOrderResult.getTheReturn().keySet().iterator().next());
  }

  /**
   * The ID is composed by the currency pair and the id number separated by colon, like: <code>
   * btc_brl:3498</code> Please see and use {@link
   * org.knowm.xchange.mercadobitcoin.MercadoBitcoinUtils#makeMercadoBitcoinOrderId} .
   */
  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    String[] pairAndId = orderId.split(":");

    mercadoBitcoinCancelOrder(pairAndId[0], pairAndId[1]);

    return true;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /**
   * @param params Required parameter types: {@link TradeHistoryParamCurrencyPair}. Supported types:
   *     {@link TradeHistoryParamsIdSpan}, {@link TradeHistoryParamsTimeSpan}.
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();

    String fromId = null;
    String toId = null;
    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan paramsIdSpan = (TradeHistoryParamsIdSpan) params;
      fromId = paramsIdSpan.getStartId();
      toId = paramsIdSpan.getEndId();
    }

    Long fromDate = null;
    Long toDate = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan paramsTimeSpan = (TradeHistoryParamsTimeSpan) params;
      fromDate = toUnixTimeNullSafe(paramsTimeSpan.getStartTime());
      toDate = toUnixTimeNullSafe(paramsTimeSpan.getEndTime());
    }

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> orders =
        getMercadoBitcoinUserOrders(
            MercadoBitcoinAdapters.adaptCurrencyPair(pair),
            null, /* all */
            null,
            fromId,
            toId,
            fromDate,
            toDate);

    return MercadoBitcoinAdapters.toUserTrades(pair, orders);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new MercadoTradeHistoryParams(CurrencyPair.BTC_BRL);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  public static class MercadoTradeHistoryParams extends DefaultTradeHistoryParamCurrencyPair
      implements TradeHistoryParamsIdSpan, TradeHistoryParamsTimeSpan {
    private String startId;
    private String endId;
    private Date startTime;
    private Date endTime;

    public MercadoTradeHistoryParams(CurrencyPair pair) {
      super(pair);
    }

    @Override
    public String getStartId() {
      return startId;
    }

    @Override
    public void setStartId(String startId) {
      this.startId = startId;
    }

    @Override
    public String getEndId() {
      return endId;
    }

    @Override
    public void setEndId(String endId) {
      this.endId = endId;
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
  }
}
