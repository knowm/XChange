package org.knowm.xchange.bitstamp.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2;
import org.knowm.xchange.bitstamp.BitstampUtils;
import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
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
import org.knowm.xchange.utils.DateUtils;

/** @author Matija Mazi */
public class BitstampTradeService extends BitstampTradeServiceRaw implements TradeService {

  public BitstampTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, BitstampException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, IOException {
    Collection<CurrencyPair> pairs = DefaultOpenOrdersParamCurrencyPair.getPairs(params, exchange);
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (CurrencyPair pair : pairs) {
      BitstampOrder[] openOrders = getBitstampOpenOrders(pair);
      for (BitstampOrder bitstampOrder : openOrders) {
        OrderType orderType = bitstampOrder.getType() == 0 ? OrderType.BID : OrderType.ASK;
        String id = Long.toString(bitstampOrder.getId());
        BigDecimal price = bitstampOrder.getPrice();
        limitOrders.add(
            new LimitOrder(
                orderType,
                bitstampOrder.getAmount(),
                pair,
                id,
                bitstampOrder.getDatetime(),
                price,
                null, // avgPrice
                null, // cumAmount
                null, // fee
                Order.OrderStatus.NEW));
      }
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, BitstampException {
    BitstampAuthenticatedV2.Side side =
        order.getType().equals(BID)
            ? BitstampAuthenticatedV2.Side.buy
            : BitstampAuthenticatedV2.Side.sell;
    BitstampOrder bitstampOrder =
        placeBitstampMarketOrder(order.getCurrencyPair(), side, order.getOriginalAmount());
    if (bitstampOrder.getErrorMessage() != null) {
      throw new ExchangeException(bitstampOrder.getErrorMessage());
    }
    return Long.toString(bitstampOrder.getId());
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, BitstampException {

    BitstampAuthenticatedV2.Side side =
        order.getType().equals(BID)
            ? BitstampAuthenticatedV2.Side.buy
            : BitstampAuthenticatedV2.Side.sell;
    BitstampOrder bitstampOrder =
        placeBitstampOrder(
            order.getCurrencyPair(), side, order.getOriginalAmount(), order.getLimitPrice());
    if (bitstampOrder.getErrorMessage() != null) {
      throw new ExchangeException(bitstampOrder.getErrorMessage());
    }
    return Long.toString(bitstampOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BitstampException {

    return cancelBitstampOrder(Long.parseLong(orderId));
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelAllOrders) {
      return cancelAllBitstampOrders();
    }
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    }
    return false;
  }

  /** Required parameter types: {@link TradeHistoryParamPaging#getPageLength()} */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Long limit = null;
    CurrencyPair currencyPair = null;
    Long offset = null;
    TradeHistoryParamsSorted.Order sort = null;
    Long sinceTimestamp = null;
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
    if (params instanceof TradeHistoryParamsTimeSpan) {
      sinceTimestamp =
          DateUtils.toUnixTimeNullSafe(((TradeHistoryParamsTimeSpan) params).getStartTime());
    }
    BitstampUserTransaction[] txs =
        getBitstampUserTransactions(
            limit, currencyPair, offset, sort == null ? null : sort.toString(), sinceTimestamp);
    return BitstampAdapters.adaptTradeHistory(txs);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new BitstampTradeHistoryParams(null, BitstampUtils.MAX_TRANSACTIONS_PER_QUERY);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    Collection<Order> orders = new ArrayList<>(orderIds.length);

    for (String orderId : orderIds) {
      orders.add(
          BitstampAdapters.adaptOrder(orderId, super.getBitstampOrder(Long.parseLong(orderId))));
    }

    return orders;
  }
}
