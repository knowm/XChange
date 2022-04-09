package org.knowm.xchange.simulated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByOrderTypeParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderParamId;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParam;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class SimulatedTradeService extends BaseExchangeService<SimulatedExchange>
    implements TradeService {

  protected SimulatedTradeService(SimulatedExchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    MatchingEngine engine = exchange.getEngine(limitOrder.getCurrencyPair());
    exchange.maybeThrow();
    return engine.postOrder(getApiKey(), limitOrder).getId();
  }

  /**
   * Use instead of {@link #placeLimitOrder(LimitOrder)} to bypass rate limitations and transient
   * errors when building up a simulated order book.
   *
   * @param limitOrder The limit order.
   * @return The id of the resulting order.
   */
  public String placeLimitOrderUnrestricted(LimitOrder limitOrder) {
    MatchingEngine engine = exchange.getEngine(limitOrder.getCurrencyPair());
    return engine.postOrder(getApiKey(), limitOrder).getId();
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    MatchingEngine engine = exchange.getEngine(marketOrder.getCurrencyPair());
    exchange.maybeThrow();
    return engine.postOrder(getApiKey(), marketOrder).getId();
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (params instanceof OpenOrdersParamCurrencyPair) {
      MatchingEngine engine =
          exchange.getEngine(((OpenOrdersParamCurrencyPair) params).getCurrencyPair());
      exchange.maybeThrow();
      return new OpenOrders(engine.openOrders(getApiKey()));
    } else {
      return new OpenOrders(
          exchange.getEngines().stream()
              .flatMap(e -> e.openOrders(getApiKey()).stream())
              .collect(Collectors.toList()));
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    if (!(params instanceof TradeHistoryParamCurrencyPair)) {
      throw new ExchangeException("Currency pair required");
    }
    MatchingEngine engine =
        exchange.getEngine(((TradeHistoryParamCurrencyPair) params).getCurrencyPair());
    exchange.maybeThrow();
    return new UserTrades(engine.tradeHistory(getApiKey()), TradeSortType.SortByTimestamp);
  }

  @Override
  public OpenOrdersParamCurrencyPair createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public TradeHistoryParamCurrencyPair createTradeHistoryParams() {
    return new DefaultTradeHistoryParamCurrencyPair();
  }

  private String getApiKey() {
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    if (StringUtils.isEmpty(apiKey)) {
      throw new ExchangeSecurityException("No API key provided");
    }
    return apiKey;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByCurrencyPair) {
      MatchingEngine engine =
          exchange.getEngine(((CancelOrderByCurrencyPair) orderParams).getCurrencyPair());
      exchange.maybeThrow();

      if (orderParams instanceof CancelOrderByIdParams
          && orderParams instanceof CancelOrderByOrderTypeParams) {
        String orderId = ((CancelOrderByIdParams) orderParams).getOrderId();
        Order.OrderType type = ((CancelOrderByOrderTypeParams) orderParams).getOrderType();

        engine.cancelOrder(orderId, getApiKey(), type);

        return true;
      }
    } else if (orderParams instanceof DefaultCancelOrderParamId) {
      String orderId = ((CancelOrderByIdParams) orderParams).getOrderId();
      exchange.getEngines().forEach(engine -> engine.cancelOrder(getApiKey(), orderId));
      return true;
    }

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {
      CancelOrderByIdParams.class,
      CancelOrderByCurrencyPair.class,
      CancelOrderByOrderTypeParams.class
    };
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelOrder(new DefaultCancelOrderParamId(orderId));
  }

  @Override
  public Class getRequiredOrderQueryParamClass() {
    return OrderQueryParamCurrencyPair.class;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) {
    return Arrays.stream(orderQueryParams)
        .flatMap(
            p -> {
              if (p instanceof OrderQueryParamCurrencyPair) {
                return exchange
                    .getEngine(((OrderQueryParamCurrencyPair) p).getCurrencyPair())
                    .openOrders(getApiKey())
                    .stream()
                    .filter(o -> o.getId().equals(p.getOrderId()));
              } else if (p instanceof DefaultQueryOrderParam) {
                return exchange.getEngines().stream()
                    .flatMap(e -> e.openOrders(getApiKey()).stream())
                    .filter(o -> o.getId().equals(p.getOrderId()));
              } else {
                throw new NotYetImplementedForExchangeException();
              }
            })
        .collect(Collectors.toList());
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) {
    List<Order> openOrders = new ArrayList<>();

    for (String orderId : orderIds) {
      openOrders.addAll(getOrder(new DefaultQueryOrderParam(orderId)));
    }
    return openOrders;
  }
}
