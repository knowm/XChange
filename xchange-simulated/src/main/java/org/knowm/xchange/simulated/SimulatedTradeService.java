package org.knowm.xchange.simulated;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.trade.TradeService;

public class SimulatedTradeService extends BaseExchangeService<SimulatedExchange> implements TradeService {

  protected SimulatedTradeService(SimulatedExchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    String apiKey = getApiKey();
    MatchingEngine engine = exchange.getEngine(limitOrder.getCurrencyPair());
    return engine.postOrder(apiKey, limitOrder).getId();
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    String apiKey = getApiKey();
    MatchingEngine engine = exchange.getEngine(marketOrder.getCurrencyPair());
    return engine.postOrder(apiKey, marketOrder).getId();
  }

  private String getApiKey() {
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    if (StringUtils.isEmpty(apiKey)) {
      throw new ExchangeSecurityException("No API key provided");
    }
    return apiKey;
  }
}