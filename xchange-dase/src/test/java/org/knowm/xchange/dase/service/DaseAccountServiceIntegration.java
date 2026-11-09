package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dase.dto.account.DaseBalancesResponse;
import org.knowm.xchange.dase.dto.user.DaseUserProfile;

/**
 * Authenticated adapter integration tests (user profile, balances). Skips when
 * DASE_API_KEY/DASE_API_SECRET are not present in the environment. Run with: mvn clean verify
 * -DskipIntegrationTests=false
 */
class DaseAccountServiceIntegration {

  private Exchange authenticatedExchangeOrSkip() {
    String apiKey = System.getenv("DASE_API_KEY");
    String secret = System.getenv("DASE_API_SECRET");
    boolean hasCreds = apiKey != null && !apiKey.isEmpty() && secret != null && !secret.isEmpty();
    assumeTrue(hasCreds, "DASE_API_KEY/DASE_API_SECRET must be set for authenticated tests");

    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    ExchangeSpecification spec = ex.getDefaultExchangeSpecification();
    spec.setApiKey(apiKey);
    spec.setSecretKey(secret);
    ex.applySpecification(spec);
    return ex;
  }

  @Test
  void user_profile_live() throws Exception {
    Exchange ex = authenticatedExchangeOrSkip();
    DaseAccountServiceRaw raw = new DaseAccountServiceRaw(ex);

    DaseUserProfile profile = raw.getUserProfile();
    assertThat(profile).isNotNull();
    assertThat(profile.getPortfolioId()).isNotNull();
  }

  @Test
  void balances_live() throws Exception {
    Exchange ex = authenticatedExchangeOrSkip();
    DaseAccountServiceRaw raw = new DaseAccountServiceRaw(ex);

    DaseBalancesResponse balances = raw.getDaseBalances();
    assertThat(balances).isNotNull();
    // balances.getBalances() may be empty; presence is sufficient for smoke test
  }
}
