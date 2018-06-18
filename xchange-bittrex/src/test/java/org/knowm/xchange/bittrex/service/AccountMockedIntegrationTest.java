package org.knowm.xchange.bittrex.service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.Before;

import org.junit.Test;
import org.knowm.xchange.dto.account.AccountInfo;

/** @author walec51 */
public class AccountMockedIntegrationTest extends BaseMockedIntegrationTest {

  private static BittrexAccountService accountService;

  @Before
  public void setUp() {
    accountService = (BittrexAccountService) createExchange().getAccountService();
  }
  
  @Test
  public void accountInfoFetchTest() throws Exception {
    stubFor(get(urlPathEqualTo("/api/v1.1/account/getbalances"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBodyFile("balances.json")));
    AccountInfo accountInfo = accountService.getAccountInfo();
    assertThat(accountInfo).isNotNull();
  }
}
