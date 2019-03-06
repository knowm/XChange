package org.knowm.xchange.simulated;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

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
   * Use instead of {@link #placeLimitOrder(LimitOrder)} to bypass rate limitations
   * and transient errors when building up a simulated order book.
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
}
