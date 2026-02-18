package org.knowm.xchange.dase.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeTrue;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dase.dto.account.DaseSingleBalance;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/**
 * Additional authenticated integration tests for single balance and funding history. Skips when
 * DASE_API_KEY/DASE_API_SECRET are not present in the environment. Run with: mvn clean verify
 * -DskipIntegrationTests=false
 */
public class DaseAccountServiceMoreIntegration {

  private Exchange authenticatedExchangeOrSkip() {
    String apiKey = System.getenv("DASE_API_KEY");
    String secret = System.getenv("DASE_API_SECRET");
    boolean hasCreds = apiKey != null && !apiKey.isEmpty() && secret != null && !secret.isEmpty();
    assumeTrue("DASE_API_KEY/DASE_API_SECRET must be set for authenticated tests", hasCreds);

    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    ExchangeSpecification spec = ex.getDefaultExchangeSpecification();
    spec.setApiKey(apiKey);
    spec.setSecretKey(secret);
    ex.applySpecification(spec);
    return ex;
  }

  @Test
  public void single_balance_live() throws Exception {
    Exchange ex = authenticatedExchangeOrSkip();
    DaseAccountServiceRaw raw = new DaseAccountServiceRaw(ex);

    // Probe a likely-present fiat/crypto; API should return object even if zeroed
    DaseSingleBalance eur = raw.getDaseBalance("EUR");
    assertNotNull(eur);
  }

  @Test
  public void funding_history_live_smoke() throws Exception {
    Exchange ex = authenticatedExchangeOrSkip();
    DaseAccountService svc = new DaseAccountService(ex);

    TradeHistoryParams params = svc.createFundingHistoryParams();
    // Optional: leave defaults; validate call completes and returns a list (may be empty)
    assertNotNull(svc.getFundingHistory(params));
  }
}
