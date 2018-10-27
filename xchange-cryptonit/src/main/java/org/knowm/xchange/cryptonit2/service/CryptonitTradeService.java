package org.knowm.xchange.cryptonit2.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.CryptonitAdapters;
import org.knowm.xchange.cryptonit2.CryptonitAuthenticatedV2;
import org.knowm.xchange.cryptonit2.CryptonitUtils;
import org.knowm.xchange.cryptonit2.dto.CryptonitException;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitOrder;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitUserTransaction;
import org.knowm.xchange.cryptonit2.order.dto.CryptonitGenericOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/** @author Matija Mazi */
public class CryptonitTradeService extends CryptonitTradeServiceRaw implements TradeService {

  public CryptonitTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, CryptonitException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, IOException {
    Collection<CurrencyPair> pairs = DefaultOpenOrdersParamCurrencyPair.getPairs(params, exchange);
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (CurrencyPair pair : pairs) {
      CryptonitOrder[] openOrders = getCryptonitOpenOrders(pair);
      for (CryptonitOrder CryptonitOrder : openOrders) {
        OrderType orderType = CryptonitOrder.getType() == 0 ? OrderType.BID : OrderType.ASK;
        String id = Integer.toString(CryptonitOrder.getId());
        BigDecimal price = CryptonitOrder.getPrice();
        limitOrders.add(
            new LimitOrder(
                orderType,
                CryptonitOrder.getAmount(),
                pair,
                id,
                CryptonitOrder.getDatetime(),
                price));
      }
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, CryptonitException {
    CryptonitAuthenticatedV2.Side side =
        order.getType().equals(BID)
            ? CryptonitAuthenticatedV2.Side.buy
            : CryptonitAuthenticatedV2.Side.sell;
    CryptonitOrder CryptonitOrder =
        placeCryptonitMarketOrder(order.getCurrencyPair(), side, order.getOriginalAmount());
    if (CryptonitOrder.getErrorMessage() != null) {
      throw new ExchangeException(CryptonitOrder.getErrorMessage());
    }
    return Integer.toString(CryptonitOrder.getId());
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, CryptonitException {

    CryptonitAuthenticatedV2.Side side =
        order.getType().equals(BID)
            ? CryptonitAuthenticatedV2.Side.buy
            : CryptonitAuthenticatedV2.Side.sell;
    CryptonitOrder CryptonitOrder =
        placeCryptonitOrder(
            order.getCurrencyPair(), side, order.getOriginalAmount(), order.getLimitPrice());
    if (CryptonitOrder.getErrorMessage() != null) {
      throw new ExchangeException(CryptonitOrder.getErrorMessage());
    }
    return Integer.toString(CryptonitOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, CryptonitException {

    return cancelCryptonitOrder(Integer.parseInt(orderId));
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /** Required parameter types: {@link TradeHistoryParamPaging#getPageLength()} */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Long limit = null;
    CurrencyPair currencyPair = null;
    Long offset = null;
    TradeHistoryParamsSorted.Order sort = null;
    if (params instanceof TradeHistoryParamPaging) {
      limit = Long.valueOf(((TradeHistoryParamPaging) params).getPageLength());
    }
    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }
    if (params instanceof TradeHistoryParamsSorted) {
      sort = ((TradeHistoryParamsSorted) params).getOrder();
    }
    CryptonitUserTransaction[] txs =
        getCryptonitUserTransactions(
            limit, currencyPair, offset, sort == null ? null : sort.toString());
    return CryptonitAdapters.adaptTradeHistory(txs);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new CryptonitTradeHistoryParams(null, CryptonitUtils.MAX_TRANSACTIONS_PER_QUERY);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    Collection<Order> orders = new ArrayList<>(orderIds.length);

    for (String orderId : orderIds) {
      if (orderId != null) {
        try {
          orders.add(
              CryptonitAdapters.adaptOrder(
                  orderId, super.getCryptonitOrder(Long.parseLong(orderId))));
        } catch (MismatchedInputException mie) {
          if (mie.getMessage().contains("Order not found")) {
            Order unknown =
                new CryptonitGenericOrder(
                    null, null, null, orderId, null, null, null, null, OrderStatus.UNKNOWN);
            orders.add(unknown);
          }
        }
      }
    }

    return orders;
  }
}
