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
import org.knowm.xchange.bitstamp.BitstampV2;
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
import org.knowm.xchange.service.trade.params.CancelAllOrders;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;
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

    BitstampOrder[] openOrders = getBitstampOpenOrders();
    List<LimitOrder> limitOrders = new ArrayList<>(openOrders.length);

    for (BitstampOrder bitstampOrder : openOrders) {
      OrderType orderType = bitstampOrder.getType() == 0 ? OrderType.BID : OrderType.ASK;
      String id = Long.toString(bitstampOrder.getId());
      BigDecimal price = bitstampOrder.getPrice();
      limitOrders.add(
          new LimitOrder(
              orderType,
              bitstampOrder.getAmount(),
              new BitstampV2.Pair(bitstampOrder.getCurrencyPair()).pair,
              id,
              bitstampOrder.getDatetime(),
              price,
              null, // avgPrice
              null, // cumAmount
              null, // fee
              Order.OrderStatus.NEW));
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
    TradeHistoryParamsSorted.Order order = null;
    Long sinceTimestamp = null;
    String sinceId = null;
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
      order = ((TradeHistoryParamsSorted) params).getOrder();
    }
    if (params instanceof TradeHistoryParamsTimeSpan) {
      sinceTimestamp =
          DateUtils.toUnixTimeNullSafe(((TradeHistoryParamsTimeSpan) params).getStartTime());
    }
    if (params instanceof TradeHistoryParamsIdSpan) {
      sinceId = ((TradeHistoryParamsIdSpan) params).getStartId();
    }
    BitstampUserTransaction[] txs;
    final String sort = order == null ? null : order.toString();
    if (currencyPair == null) {
      txs = getBitstampUserTransactions(limit, offset, sort, sinceTimestamp, sinceId);
    } else {
      txs = getBitstampUserTransactions(limit, currencyPair, offset, sort, sinceTimestamp, sinceId);
    }

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
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    Collection<Order> orders = new ArrayList<>(orderQueryParams.length);

    for (OrderQueryParams params : orderQueryParams) {
      orders.add(
          BitstampAdapters.adaptOrder(
              params.getOrderId(),
              super.getBitstampOrder(Long.parseLong(params.getOrderId())),
              exchange.getExchangeSymbols()));
    }

    return orders;
  }
}
