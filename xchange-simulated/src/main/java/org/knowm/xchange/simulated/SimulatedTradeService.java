package org.knowm.xchange.simulated;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
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
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.*;

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
    if (!(params instanceof OpenOrdersParamCurrencyPair)) {
      throw new ExchangeException("Currency pair required");
    }
    MatchingEngine engine =
        exchange.getEngine(((OpenOrdersParamCurrencyPair) params).getCurrencyPair());
    exchange.maybeThrow();
    return new OpenOrders(engine.openOrders(getApiKey()));
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

        engine.cancelOrder(orderId, type);

        return true;
      }
    }
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    return Arrays.stream(orderQueryParams)
        .map(
            p -> {
              if (!(p instanceof OrderQueryParamCurrencyPair))
                throw new NotYetImplementedForExchangeException();

              MatchingEngine engine =
                  exchange.getEngine(((OrderQueryParamCurrencyPair) p).getCurrencyPair());

              Optional<LimitOrder> first =
                  engine.openOrders(getApiKey()).stream()
                      .filter(o -> o.getId().equals(p.getOrderId()))
                      .findFirst();

              return first.orElse(null);
            })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
