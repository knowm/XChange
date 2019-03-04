package org.knowm.xchange.simulated;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class SimulatedTradeService extends BaseExchangeService<SimulatedExchange> implements TradeService {

  protected SimulatedTradeService(SimulatedExchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    MatchingEngine engine = exchange.getEngine(limitOrder.getCurrencyPair());
    return engine.postOrder(getApiKey(), limitOrder).getId();
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    MatchingEngine engine = exchange.getEngine(marketOrder.getCurrencyPair());
    return engine.postOrder(getApiKey(), marketOrder).getId();
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (!(params instanceof OpenOrdersParamCurrencyPair)) {
      throw new ExchangeException("Currency pair required");
    }
    MatchingEngine engine = exchange.getEngine(((OpenOrdersParamCurrencyPair)params).getCurrencyPair());
    return engine.openOrders(getApiKey());
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  private String getApiKey() {
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    if (StringUtils.isEmpty(apiKey)) {
      throw new ExchangeSecurityException("No API key provided");
    }
    return apiKey;
  }
}