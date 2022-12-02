package org.knowm.xchange.bitstamp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeNotNull;

import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.dto.trade.UserTrade;

public class BitstampTradeServiceIntegration {

  private String apiKey = null;
  private String secretKey = null;
  private String username = null;
  private BitstampTradeService bitstampTradeService;

  @Before
  public void setUp() {
    assumeNotNull(apiKey, secretKey, username);
    ExchangeSpecification specification =
        ExchangeFactory.INSTANCE
            .createExchange(BitstampExchange.class)
            .getDefaultExchangeSpecification();
    specification.setApiKey(apiKey);
    specification.setSecretKey(secretKey);
    specification.setUserName(username);

    bitstampTradeService =
        (BitstampTradeService)
            ExchangeFactory.INSTANCE.createExchange(specification).getTradeService();
  }

  @Test
  public void testGetTradeHistory() throws IOException {
    final BitstampTradeHistoryParams tradeHistoryParams =
        (BitstampTradeHistoryParams) bitstampTradeService.createTradeHistoryParams();
    final List<UserTrade> userTrades =
        bitstampTradeService.getTradeHistory(tradeHistoryParams).getUserTrades();
    assertThat(userTrades).isNotNull();

    int numberOfLastTradesToLoad = 2;
    if (userTrades.size() >= numberOfLastTradesToLoad) {
      final UserTrade referenceTrade = userTrades.get(userTrades.size() - numberOfLastTradesToLoad);

      tradeHistoryParams.setStartId(referenceTrade.getId());

      final List<UserTrade> userTradesSinceTradeId =
          bitstampTradeService.getTradeHistory(tradeHistoryParams).getUserTrades();

      assertThat(userTradesSinceTradeId).hasSize(numberOfLastTradesToLoad);
    }
  }
}
