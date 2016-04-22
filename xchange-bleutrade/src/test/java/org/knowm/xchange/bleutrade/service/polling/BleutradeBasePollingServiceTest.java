package org.knowm.xchange.bleutrade.service.polling;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bleutrade.BleutradeAuthenticated;
import org.knowm.xchange.bleutrade.BleutradeExchange;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import org.knowm.xchange.currency.CurrencyPair;

@RunWith(PowerMockRunner.class)
public class BleutradeBasePollingServiceTest extends BleutradeServiceTestSupport {

  private BleutradeBasePollingService pollingService;

  @Before
  public void setUp() {
    BleutradeExchange exchange = (BleutradeExchange) ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getCanonicalName());
    exchange.getExchangeSpecification().setUserName(SPECIFICATION_USERNAME);
    exchange.getExchangeSpecification().setApiKey(SPECIFICATION_API_KEY);
    exchange.getExchangeSpecification().setSecretKey(SPECIFICATION_SECRET_KEY);

    pollingService = new BleutradeBasePollingService(exchange);
  }

  @Test
  public void constructor() {
    assertThat(Whitebox.getInternalState(pollingService, "apiKey")).isEqualTo(SPECIFICATION_API_KEY);
  }

  @Test
  public void shouldGetExchangeSymbols() throws IOException {
    // given
    final List<BleutradeMarket> expectedMarkets = expectedBleutradeMarkets();

    // when
    BleutradeMarketsReturn marketsReturn = new BleutradeMarketsReturn();
    marketsReturn.setSuccess(true);
    marketsReturn.setMessage("test message");
    marketsReturn.setResult(expectedMarkets);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarkets()).thenReturn(marketsReturn);
    Whitebox.setInternalState(pollingService, "bleutrade", bleutrade);

    // when
    List<CurrencyPair> exchangeSymbols = pollingService.getExchangeSymbols();

    // then
    assertThat(exchangeSymbols).hasSize(2);
    assertThat(exchangeSymbols).contains(CurrencyPair.DOGE_BTC, BLEU_BTC_CP);
  }

}
