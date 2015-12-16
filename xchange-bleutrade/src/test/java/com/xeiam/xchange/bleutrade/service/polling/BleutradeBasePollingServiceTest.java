package com.xeiam.xchange.bleutrade.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bleutrade.BleutradeAuthenticated;
import com.xeiam.xchange.bleutrade.BleutradeExchange;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.OpenOrders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class BleutradeBasePollingServiceTest extends BleutradeServiceTestSupport {

  private BleutradeBasePollingService pollingService;

  @Before
  public void setUp() throws Exception {
    BleutradeExchange exchange = (BleutradeExchange) ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getCanonicalName());
    exchange.getExchangeSpecification().setUserName("admin");
    exchange.getExchangeSpecification().setApiKey("publicKey");
    exchange.getExchangeSpecification().setSecretKey("secretKey");

    pollingService = new BleutradeBasePollingService(exchange);
  }

  @Test
  public void constructor() throws Exception {
    assertThat(Whitebox.getInternalState(pollingService, "apiKey")).isEqualTo("publicKey");
  }

  @Test
  public void shouldGetExchangeSymbols() throws Exception {
    // when
    BleutradeMarketsReturn marketsReturn = new BleutradeMarketsReturn();
    marketsReturn.setSuccess(true);
    marketsReturn.setMessage("test message");
    marketsReturn.setResult(Arrays.asList(
        createBleutradeMarket("DOGE", "BTC", "Dogecoin", "Bitcoin", new BigDecimal("0.10000000"), "DOGE_BTC", true),
        createBleutradeMarket("BLEU", "BTC", "Bleutrade Share", "Bitcoin", new BigDecimal("0.00000001"), "BLEU_BTC", true)
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarkets()).thenReturn(marketsReturn);
    Whitebox.setInternalState(pollingService, "bleutrade", bleutrade);

    // when
    List<CurrencyPair> exchangeSymbols = pollingService.getExchangeSymbols();

    // then
    assertThat(exchangeSymbols).hasSize(2);
    assertThat(exchangeSymbols).contains(CurrencyPair.DOGE_BTC, new CurrencyPair("BLEU", "BTC"));
  }

}
