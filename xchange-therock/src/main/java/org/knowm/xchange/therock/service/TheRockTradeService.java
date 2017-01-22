package org.knowm.xchange.therock.service;

import java.io.IOException;
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
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.therock.TheRockAdapters;
import org.knowm.xchange.therock.TheRockExchange;
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
    final TheRockOrder placedOrder = placeTheRockOrder(order.getCurrencyPair(), order.getTradableAmount(), null,
        TheRockAdapters.adaptSide(order.getType()), TheRockOrder.Type.market);
    return placedOrder.getId().toString();
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, ExchangeException {
    final TheRockOrder placedOrder = placeTheRockOrder(order.getCurrencyPair(), order.getTradableAmount(), order.getLimitPrice(),
        TheRockAdapters.adaptSide(order.getType()), TheRockOrder.Type.limit);
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
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CurrencyPair currencyPair = null;

    if (params instanceof OpenOrdersParamCurrencyPair) {
      currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
    }

    if (currencyPair == null) {
      throw new ExchangeException("CurrencyPair parameter must not be null.");
    }

    return TheRockAdapters.adaptOrders(getTheRockOrders(currencyPair));
  }

  /**
   * Not available from exchange since TheRock needs currency pair in order to cancel an order
   */
  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    CurrencyPair cp = (CurrencyPair) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(TheRockExchange.CURRENCY_PAIR);
    if (cp == null) {
      throw new ExchangeException("Provide currencyPair attribute via setExchangeSpecificParameters.");
    }

    return "deleted".equals(cancelTheRockOrder(cp, Long.parseLong(orderId)).getStatus());
  }

  /**
   * Not available from exchange since TheRock needs currency pair in order to return/show the order
   */
  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
      if (!(params instanceof TradeHistoryParamCurrencyPair)) {
          throw new ExchangeException("TheRock API recquires " + TradeHistoryParamCurrencyPair.class.getName());
      }
      TradeHistoryParamCurrencyPair pairParams = (TradeHistoryParamCurrencyPair) params;
      Long sinceTradeId = null;        // get all trades starting from a specific trade_id
      if (params instanceof TradeHistoryParamsIdSpan) {
          TradeHistoryParamsIdSpan trId = (TradeHistoryParamsIdSpan) params;
          try {
            sinceTradeId = Long.valueOf(trId.getStartId());
        } catch (Throwable ignored) {}
      }
      Date after = null;
      Date before = null;
      
      if (params instanceof TradeHistoryParamsTimeSpan) {
          TradeHistoryParamsTimeSpan time = (TradeHistoryParamsTimeSpan) params;
          after = time.getStartTime();
          before = time.getEndTime();
      }
      return TheRockAdapters.adaptUserTrades(getTheRockUserTrades(pairParams.getCurrencyPair(), sinceTradeId, after, before), pairParams.getCurrencyPair());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new TheRockOpenOrdersParams();
  }
}
