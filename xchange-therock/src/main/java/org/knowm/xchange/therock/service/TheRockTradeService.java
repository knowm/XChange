package org.knowm.xchange.therock.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.therock.TheRockAdapters;
import org.knowm.xchange.therock.TheRockExchange;
import org.knowm.xchange.therock.dto.TheRockException;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;

/**
 * @author Matija Mazi
 * @author Pnk
 */
public class TheRockTradeService extends TheRockTradeServiceRaw implements TradeService {

  public TheRockTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, ExchangeException {
    final TheRockOrder placedOrder =
        placeTheRockOrder(
            order.getCurrencyPair(),
            order.getOriginalAmount(),
            BigDecimal.ZERO,
            TheRockAdapters.adaptSide(order.getType()),
            TheRockOrder.Type.market);
    return placedOrder.getId().toString();
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, ExchangeException {
    final TheRockOrder placedOrder =
        placeTheRockOrder(
            order.getCurrencyPair(),
            order.getOriginalAmount(),
            order.getLimitPrice(),
            TheRockAdapters.adaptSide(order.getType()),
            TheRockOrder.Type.limit);
    return placedOrder.getId().toString();
  }

  /**
   * Not available from exchange since TheRock needs currency pair in order to return open orders
   */
  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    CurrencyPair currencyPair = null;

    if (params instanceof OpenOrdersParamCurrencyPair) {
      currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
    }

    if (currencyPair == null) {
      throw new ExchangeException("CurrencyPair parameter must not be null.");
    }

    return TheRockAdapters.adaptOrders(getTheRockOrders(currencyPair));
  }

  /** Not available from exchange since TheRock needs currency pair in order to cancel an order */
  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    CurrencyPair cp =
        (CurrencyPair)
            exchange
                .getExchangeSpecification()
                .getExchangeSpecificParameters()
                .get(TheRockExchange.CURRENCY_PAIR);
    if (cp == null) {
      throw new ExchangeException("Provide TheRockCancelOrderParams with orderId and currencyPair");
    }

    return cancelOrder(cp, orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {
    if (!(params instanceof CancelOrderByIdParams)) {
      return false;
    }
    CancelOrderByIdParams paramId = (CancelOrderByIdParams) params;

    CurrencyPair currencyPair;
    if (params instanceof CancelOrderByCurrencyPair) {
      CancelOrderByCurrencyPair paramCurrencyPair = (CancelOrderByCurrencyPair) params;
      currencyPair = paramCurrencyPair.getCurrencyPair();
    } else {
      currencyPair = null;
    }

    return cancelOrder(currencyPair, paramId.getOrderId());
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {CancelOrderByIdParams.class, CancelOrderByCurrencyPair.class};
  }

  private boolean cancelOrder(CurrencyPair currencyPair, String orderId)
      throws TheRockException, NumberFormatException, IOException {
    TheRockOrder cancelledOrder = cancelTheRockOrder(currencyPair, Long.parseLong(orderId));
    return "deleted".equals(cancelledOrder.getStatus());
  }

  /**
   * Not available from exchange since TheRock needs currency pair in order to return/show the order
   */
  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    if (!(params instanceof TradeHistoryParamCurrencyPair)) {
      throw new ExchangeException("TheRock API recquires " + TradeHistoryParamCurrencyPair.class);
    }

    TradeHistoryParamCurrencyPair pairParams = (TradeHistoryParamCurrencyPair) params;
    Long sinceTradeId = null; // get all trades starting from a specific trade_id
    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan trId = (TradeHistoryParamsIdSpan) params;
      try {
        sinceTradeId = Long.valueOf(trId.getStartId());
      } catch (Throwable ignored) {
      }
    }

    Date after = null;
    Date before = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan time = (TradeHistoryParamsTimeSpan) params;
      after = time.getStartTime();
      before = time.getEndTime();
    }

    int pageLength = 200;
    int page = 0;
    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging tradeHistoryParamPaging = (TradeHistoryParamPaging) params;
      pageLength = tradeHistoryParamPaging.getPageLength();
      page = tradeHistoryParamPaging.getPageNumber();
    }

    return TheRockAdapters.adaptUserTrades(
        getTheRockUserTrades(
            pairParams.getCurrencyPair(), sinceTradeId, after, before, pageLength, page),
        pairParams.getCurrencyPair());
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new TheRockOpenOrdersParams();
  }
}
