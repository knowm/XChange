package org.knowm.xchange.bittrex.service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;
import static org.knowm.xchange.dto.account.FundingRecord.Type.DEPOSIT;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/** @author walec51 */
public class AccountMockedIntegrationTest extends BaseMockedIntegrationTest {

  private static BittrexAccountService accountService;

  @Before
  public void setUp() {
    accountService = (BittrexAccountService) createExchange().getAccountService();
  }

  @Test
  public void accountInfoTest() throws Exception {
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
  public void fundingHistoryTest() throws Exception {
    stubFor(
        get(urlPathEqualTo("/api/v1.1/account/getdeposithistory"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("deposithistory.json")));
    stubFor(
        get(urlPathEqualTo("/api/v1.1/account/getwithdrawalhistory"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("withdrawalhistory.json")));
    TradeHistoryParams params = accountService.createFundingHistoryParams();
    List<FundingRecord> fundingHistory = accountService.getFundingHistory(params);
    assertThat(fundingHistory).isNotEmpty();
    FundingRecord record = fundingHistory.get(0);
    assertThat(record).isNotNull();
    assertThat(record.getType()).isEqualTo(DEPOSIT);
    assertThat(record.getCurrency()).isEqualTo(Currency.BTC);
    assertThat(record.getAmount()).isNotNull().isPositive();
  }
}
