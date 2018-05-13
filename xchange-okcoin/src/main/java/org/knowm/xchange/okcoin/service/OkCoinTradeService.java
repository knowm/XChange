package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.trade.OkCoinBatchTradeResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinOrderResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinTradeResult;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class OkCoinTradeService extends OkCoinTradeServiceRaw implements TradeService {

  private static final String ORDER_STATUS_FILLED = "1";

  private static final OpenOrders NO_OPEN_ORDERS =
      new OpenOrders(Collections.<LimitOrder>emptyList());

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) {
    if (!(params instanceof OpenOrdersParamCurrencyPair)) {
      throw new UnsupportedOperationException(
          "Getting open orders is only available for a single market.");
    }
    CurrencyPair symbol = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
    OkCoinOrderResult orderResults;
    try {
      // orderId = -1 returns all of the orders on this market
      orderResults = getOrder(-1, OkCoinAdapters.adaptSymbol(symbol));
    } catch (Exception e) {
      return NO_OPEN_ORDERS;
      // Market not present.
    }

    if (orderResults.getOrders() == null || orderResults.getOrders().length == 0) {
      return NO_OPEN_ORDERS;
    }
    return OkCoinAdapters.adaptOpenOrders(Collections.singletonList(orderResults));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    String marketOrderType = null;
    String rate = null;
    String amount = null;

    if (marketOrder.getType().equals(OrderType.BID)) {
      marketOrderType = "buy_market";
      rate = marketOrder.getOriginalAmount().toPlainString();
      amount = "1";
    } else {
      marketOrderType = "sell_market";
      rate = "1";
      amount = marketOrder.getOriginalAmount().toPlainString();
    }

    long orderId =
        trade(
                OkCoinAdapters.adaptSymbol(marketOrder.getCurrencyPair()),
                marketOrderType,
                rate,
                amount)
            .getOrderId();
    return String.valueOf(orderId);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    long orderId =
        trade(
                OkCoinAdapters.adaptSymbol(limitOrder.getCurrencyPair()),
                limitOrder.getType() == OrderType.BID ? "buy" : "sell",
                limitOrder.getLimitPrice().toPlainString(),
                limitOrder.getOriginalAmount().toPlainString())
            .getOrderId();
    return String.valueOf(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (!(orderParams instanceof CancelOrderByIdParams)
        || !(orderParams instanceof CancelOrderByCurrencyPair)) {
      throw new UnsupportedOperationException(
          "Cancelling an order is only available for a single market and a single id.");
    }
    long id = Long.valueOf(((CancelOrderByIdParams) orderParams).getOrderId());
    String symbol =
        OkCoinAdapters.adaptSymbol(((CancelOrderByCurrencyPair) orderParams).getCurrencyPair());
    OkCoinTradeResult cancelResult = cancelOrder(id, symbol);
    return id == cancelResult.getOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  /**
   * Cancel a batch of up to three orders (maximum allowed by the exchange).
   *
   * @param limitOrders orders to cancel
   * @return (id, result) mappings
   */
  public Map<LimitOrder, Boolean> cancelUpToThreeOrders(List<LimitOrder> limitOrders)
      throws IOException {
    Set<Long> ordersToCancel =
        limitOrders.stream().map(Order::getId).map(Long::parseLong).collect(Collectors.toSet());
    if (ordersToCancel.isEmpty() || ordersToCancel.size() > 3) {
      throw new UnsupportedOperationException(
          "Can only batch cancel 1 to 3 orders. " + ordersToCancel.size() + " orders provided.");
    }
    CurrencyPair currencyPair = limitOrders.get(0).getCurrencyPair();
    boolean valid =
        limitOrders.stream().allMatch(order -> order.getCurrencyPair().equals(currencyPair));
    if (!valid) {
      throw new UnsupportedOperationException(
          "Can only batch cancel orders with the same currency pair.");
    }

    OkCoinBatchTradeResult okCoinBatchTradeResult =
        cancelUpToThreeOrders(ordersToCancel, OkCoinAdapters.adaptSymbol(currencyPair));
    Map<String, Boolean> requestResults = new HashMap<>(ordersToCancel.size());
    if (okCoinBatchTradeResult.getSuccess() != null) {
      Arrays.stream(okCoinBatchTradeResult.getSuccess().split(BATCH_DELIMITER))
          .forEach(id -> requestResults.put(id, Boolean.TRUE));
    }
    if (okCoinBatchTradeResult.getError() != null) {
      Arrays.stream(okCoinBatchTradeResult.getError().split(BATCH_DELIMITER))
          .forEach(id -> requestResults.put(id, Boolean.FALSE));
    }
    Map<LimitOrder, Boolean> results = new HashMap<>(limitOrders.size());
    requestResults.forEach(
        (id, result) ->
            limitOrders
                .stream()
                .filter(order -> order.getId().equals(id))
                .findAny()
                .ifPresent(limitOrder -> results.put(limitOrder, requestResults.get(id))));
    return results;
  }

  /**
   * OKEX does not support trade history in the usual way, it only provides a aggregated view on a
   * per order basis of how much the order has been filled and the average price. Individual trade
   * details are not available. As a consequence of this, the trades supplied by this method will
   * use the order ID as their trade ID, and will be subject to being amended if a partially filled
   * order if further filled. Supported parameters are {@link TradeHistoryParamCurrencyPair} and
   * {@link TradeHistoryParamPaging}, if not supplied then the query will default to BTC/USD or
   * BTC/CNY (depending on session configuration) and the last 200 trades.
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer pageLength = null, pageNumber = null;
    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
      pageLength = paging.getPageLength();
      pageNumber = paging.getPageNumber();
    }
    if (pageNumber == null) {
      pageNumber = 1; // pages start from 1
    }
    if (pageLength == null) {
      pageLength = 200; // 200 is the maximum number
    }

    CurrencyPair pair = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }
    if (pair == null) {
      pair = useIntl ? CurrencyPair.BTC_USD : CurrencyPair.BTC_CNY;
    }

    OkCoinOrderResult orderHistory =
        getOrderHistory(
            OkCoinAdapters.adaptSymbol(pair),
            ORDER_STATUS_FILLED,
            pageNumber.toString(),
            pageLength.toString());
    return OkCoinAdapters.adaptTrades(orderHistory);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new OkCoinTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  public static class OkCoinTradeHistoryParams extends DefaultTradeHistoryParamPaging
      implements TradeHistoryParamCurrencyPair {

    private CurrencyPair pair;

    public OkCoinTradeHistoryParams() {}

    public OkCoinTradeHistoryParams(Integer pageLength, Integer pageNumber, CurrencyPair pair) {

      super(pageLength, pageNumber);
      this.pair = pair;
    }

    @Override
    public CurrencyPair getCurrencyPair() {

      return pair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {

      this.pair = pair;
    }
  }

  public static class OkCoinCancelOrderParam
      implements CancelOrderByIdParams, CancelOrderByCurrencyPair {
    private final CurrencyPair currencyPair;
    private final String id;

    public OkCoinCancelOrderParam(CurrencyPair currencyPair, String id) {
      this.currencyPair = currencyPair;
      this.id = id;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public String getOrderId() {
      return id;
    }
  }
}
