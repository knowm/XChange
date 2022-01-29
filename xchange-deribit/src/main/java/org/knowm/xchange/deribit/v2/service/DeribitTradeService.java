package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.DeribitAdapters;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.Kind;
import org.knowm.xchange.deribit.v2.dto.trade.AdvancedOptions;
import org.knowm.xchange.deribit.v2.dto.trade.OrderFlags;
import org.knowm.xchange.deribit.v2.dto.trade.OrderPlacement;
import org.knowm.xchange.deribit.v2.dto.trade.OrderState;
import org.knowm.xchange.deribit.v2.dto.trade.OrderType;
import org.knowm.xchange.deribit.v2.dto.trade.TimeInForce;
import org.knowm.xchange.deribit.v2.dto.trade.Trigger;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByUserReferenceParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class DeribitTradeService extends DeribitTradeServiceRaw implements TradeService {

  public DeribitTradeService(DeribitExchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return DeribitAdapters.adaptOpenOrders(openOrders());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    List<org.knowm.xchange.deribit.v2.dto.trade.Order> openOrders;

    if (params instanceof OpenOrdersParamCurrencyPair) {
      OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
      CurrencyPair pair = pairParams.getCurrencyPair();
      openOrders = super.getOpenOrdersByCurrency(pair.base.getCurrencyCode(), null, null);
    } else if (params instanceof OpenOrdersParamInstrument) {
      OpenOrdersParamInstrument instrumentParams = (OpenOrdersParamInstrument) params;
      Instrument instrument = instrumentParams.getInstrument();
      openOrders =
          super.getOpenOrdersByInstrument(DeribitAdapters.adaptInstrumentName(instrument), null);
    } else {
      openOrders = openOrders();
    }

    return DeribitAdapters.adaptOpenOrders(openOrders);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamInstrument();
  }

  private List<org.knowm.xchange.deribit.v2.dto.trade.Order> openOrders() throws IOException {
    List<org.knowm.xchange.deribit.v2.dto.trade.Order> openOrders = new ArrayList<>();
    for (Currency c : ((DeribitAccountService) exchange.getAccountService()).currencies()) {
      openOrders.addAll(super.getOpenOrdersByCurrency(c.getCurrencyCode(), null, null));
    }
    return openOrders;
  }

  @Override
  public OpenPositions getOpenPositions() throws IOException {
    return new OpenPositions(
        ((DeribitAccountService) exchange.getAccountService()).openPositions());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return placeOrder(OrderType.market, marketOrder, null, null, null);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeOrder(OrderType.limit, limitOrder, limitOrder.getLimitPrice(), null, null);
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    /*
    // TBD:
    // get "trigger", "price" and "trigger price" as follows:
    Trigger trigger = findOrderFlagValue(order, Trigger.class);
    BigDecimal price = stopOrder.getLimitPrice();
    BigDecimal triggerPrice = stopOrder.getStopPrice()

    // then adapt order type of the stop order (add corresponding adapter)
    OrderType type = DeribitAdapters.adaptOrderType(stopOrder);

    // validate the values and then call placeOrder:
    placeOrder(type, order, price, trigger, triggerPrice);
    */
    throw new NotYetImplementedForExchangeException("placeStopOrder");
  }

  private String placeOrder(
      OrderType type, Order order, BigDecimal price, Trigger trigger, BigDecimal triggerPrice)
      throws IOException {
    String instrumentName = DeribitAdapters.adaptInstrumentName(order.getInstrument());
    BigDecimal amount = order.getOriginalAmount();
    String label = order.getUserReference();
    TimeInForce timeInForce = findOrderFlagValue(order, TimeInForce.class);
    BigDecimal maxShow = null;
    Boolean postOnly = hasOrderFlag(order, OrderFlags.POST_ONLY);
    Boolean rejectPostOnly = hasOrderFlag(order, OrderFlags.REJECT_POST_ONLY);
    Boolean reduceOnly = hasOrderFlag(order, OrderFlags.REDUCE_ONLY);
    AdvancedOptions advanced = findOrderFlagValue(order, AdvancedOptions.class);
    Boolean mmp = hasOrderFlag(order, OrderFlags.MMP);

    OrderPlacement placement;
    if (order.getType() == Order.OrderType.BID) {
      placement =
          super.buy(
              instrumentName,
              amount,
              type,
              label,
              price,
              timeInForce,
              maxShow,
              postOnly,
              rejectPostOnly,
              reduceOnly,
              triggerPrice,
              trigger,
              advanced,
              mmp);
    } else if (order.getType() == Order.OrderType.ASK) {
      placement =
          super.sell(
              instrumentName,
              amount,
              type,
              label,
              price,
              timeInForce,
              maxShow,
              postOnly,
              rejectPostOnly,
              reduceOnly,
              triggerPrice,
              trigger,
              advanced,
              mmp);
    } else {
      throw new ExchangeException("Unsupported order type: " + order.getType());
    }
    return placement.getOrder().getOrderId();
  }

  private static Boolean hasOrderFlag(Order order, OrderFlags flag) {
    return order.getOrderFlags().contains(flag) ? true : null;
  }

  private static <T extends IOrderFlags> T findOrderFlagValue(Order order, Class<T> klass) {
    return order.getOrderFlags().stream()
        .filter(flag -> flag.getClass().isInstance(klass))
        .map(flag -> (T) flag)
        .findFirst()
        .orElse(null);
  }

  @Override
  public String changeOrder(LimitOrder limitOrder) throws IOException {
    String orderId = limitOrder.getId();
    BigDecimal amount = limitOrder.getOriginalAmount();
    BigDecimal price = limitOrder.getLimitPrice();
    Boolean postOnly = hasOrderFlag(limitOrder, OrderFlags.POST_ONLY);
    Boolean rejectPostOnly = hasOrderFlag(limitOrder, OrderFlags.REJECT_POST_ONLY);
    Boolean reduceOnly = hasOrderFlag(limitOrder, OrderFlags.REDUCE_ONLY);
    BigDecimal triggerPrice = null; // not implemented currently
    AdvancedOptions advanced = findOrderFlagValue(limitOrder, AdvancedOptions.class);
    Boolean mmp = hasOrderFlag(limitOrder, OrderFlags.MMP);

    return super.edit(
            orderId,
            amount,
            price,
            postOnly,
            rejectPostOnly,
            reduceOnly,
            triggerPrice,
            advanced,
            mmp)
        .getOrder()
        .getOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return super.cancel(orderId).getOrderState() == OrderState.cancelled;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else if (orderParams instanceof CancelOrderByUserReferenceParams) {
      return 0
          < super.cancelByLabel(
              ((CancelOrderByUserReferenceParams) orderParams).getUserReference());
    } else {
      return false;
    }
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {CancelOrderByIdParams.class, CancelOrderByUserReferenceParams.class};
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    String instrumentName = null;
    if (params instanceof TradeHistoryParamInstrument) {
      Instrument instrument = ((TradeHistoryParamInstrument) params).getInstrument();
      if (instrument != null) {
        instrumentName = DeribitAdapters.adaptInstrumentName(instrument);
      }
    }

    String currency = null;
    Kind kind = null; // not implemented
    if (params instanceof TradeHistoryParamCurrencyPair) {
      CurrencyPair currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      if (currencyPair != null) {
        currency = currencyPair.base.getCurrencyCode();
      }
    }

    Date startTime = null;
    Date endTime = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      startTime = ((TradeHistoryParamsTimeSpan) params).getStartTime();
      endTime = ((TradeHistoryParamsTimeSpan) params).getEndTime();
    }

    String startId = null;
    String endId = null;
    if (params instanceof TradeHistoryParamsIdSpan) {
      startId = ((TradeHistoryParamsIdSpan) params).getStartId();
      endId = ((TradeHistoryParamsIdSpan) params).getEndId();
    }

    Integer limit = null;
    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    String sorting = null;
    if (params instanceof TradeHistoryParamsSorted) {
      TradeHistoryParamsSorted.Order order = ((TradeHistoryParamsSorted) params).getOrder();
      sorting =
          order == TradeHistoryParamsSorted.Order.asc
              ? "asc"
              : order == TradeHistoryParamsSorted.Order.desc ? "desc" : null;
    }

    Boolean includeOld = null;
    if (params instanceof DeribitTradeHistoryParamsOld) {
      includeOld = ((DeribitTradeHistoryParamsOld) params).isIncludeOld();
    }

    org.knowm.xchange.deribit.v2.dto.trade.UserTrades userTrades = null;

    if (startTime != null && endTime != null) {
      if (instrumentName != null) {
        userTrades =
            super.getUserTradesByInstrumentAndTime(
                instrumentName, startTime, endTime, limit, includeOld, sorting);
      } else if (currency != null) {
        userTrades =
            super.getUserTradesByCurrencyAndTime(
                currency, kind, startTime, endTime, limit, includeOld, sorting);
      }
    } else {
      if (instrumentName != null) {
        Integer startSeq = startId != null ? Integer.valueOf(startId) : null;
        Integer endSeq = endId != null ? Integer.valueOf(endId) : null;

        userTrades =
            super.getUserTradesByInstrument(
                instrumentName, startSeq, endSeq, limit, includeOld, sorting);
      } else if (currency != null) {
        userTrades =
            super.getUserTradesByCurrency(
                currency, kind, startId, endId, limit, includeOld, sorting);
      }
    }

    if (userTrades == null) {
      throw new ExchangeException("You should specify either instrument or currency pair");
    }

    return DeribitAdapters.adaptUserTrades(userTrades);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DeribitTradeHistoryParams();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    return getOrder(
        Arrays.stream(orderQueryParams).map(OrderQueryParams::getOrderId).toArray(String[]::new));
  }
}
