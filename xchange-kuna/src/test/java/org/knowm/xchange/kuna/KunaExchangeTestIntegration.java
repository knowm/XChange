package org.knowm.xchange.kuna;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kuna.service.KunaAccountService;
import org.knowm.xchange.kuna.service.KunaMarketDataService;
import org.knowm.xchange.kuna.service.KunaTradeService;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;

public class KunaExchangeTestIntegration extends BaseKunaTest {

  @Test
  public void test_exchange_specification() {
    ExchangeSpecification exchangeSpecification = getExchange().getDefaultExchangeSpecification();

    assertThat(exchangeSpecification).isNotNull();
    assertThat(exchangeSpecification.getSslUri()).isEqualToIgnoringCase(KunaExchange.KUNA_URL);
    assertThat(exchangeSpecification.getHost()).isEqualToIgnoringCase(KunaExchange.KUNA_HOST);
    assertThat(exchangeSpecification.getPort()).isEqualTo(KunaExchange.KUNA_PORT);
  }

  @Test
  public void test_exchange_services() {
    AccountService accountService = getExchange().getAccountService();
    TradeService tradeService = getExchange().getTradeService();
    MarketDataService marketDataService = getExchange().getMarketDataService();

    assertThat(accountService).isNotNull();
    assertThat(tradeService).isNotNull();
    assertThat(marketDataService).isNotNull();

    assertThat(accountService instanceof KunaAccountService);
    assertThat(tradeService instanceof KunaTradeService);
    assertThat(marketDataService instanceof KunaMarketDataService);

  }

}