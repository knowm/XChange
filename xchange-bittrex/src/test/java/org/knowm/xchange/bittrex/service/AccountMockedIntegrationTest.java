package org.knowm.xchange.bittrex.service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.bittrex.dto.account.BittrexDepositHistory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

/** @author walec51 */
public class AccountMockedIntegrationTest extends BaseMockedIntegrationTest {

  private static BittrexAccountService accountService;

  @Before
  public void setUp() {
    accountService = (BittrexAccountService) createExchange().getAccountService();
  }

  @Test
  public void accountInfoFetchTest() throws Exception {
    stubFor(
        get(urlPathEqualTo("/api/v1.1/account/getbalances"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("balances.json")));
    AccountInfo accountInfo = accountService.getAccountInfo();
    assertThat(accountInfo).isNotNull();
    Wallet wallet = accountInfo.getWallet();
    assertThat(wallet).isNotNull();
    Balance btcBalance = wallet.getBalance(Currency.BTC);
    assertThat(btcBalance).isNotNull();
    assertThat(btcBalance.getAvailable()).isNotNull().isPositive();
  }

  @Test
  public void depositHistoryFetchTest() throws Exception {
    stubFor(
        get(urlPathEqualTo("/api/v1.1/account/getdeposithistory"))
            .withQueryParam("currency", equalTo("BTC"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("deposithistory.json")));
    List<BittrexDepositHistory> depositsHistory
        = accountService.getDepositsHistory(Currency.BTC);
    assertThat(depositsHistory).isNotEmpty();
    BittrexDepositHistory entry = depositsHistory.get(0);
    assertThat(entry).isNotNull();
    assertThat(entry.getCurrency()).isEqualTo("BTC");
    assertThat(entry.getAmount()).isNotNull().isPositive();
  }
}
