package org.knowm.xchange.livecoin.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
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

  private static LivecoinAccountService accountService;

  @Before
  public void setUp() {
    accountService = (LivecoinAccountService) createExchange().getAccountService();
  }

  @Test
  public void accountInfoTest() throws Exception {
    stubFor(
        get(urlPathEqualTo("/payment/balances"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("balances.json")));
    AccountInfo accountInfo = accountService.getAccountInfo();
    assertThat(accountInfo).isNotNull();
    Wallet wallet = accountInfo.getWallet();
    assertThat(wallet).isNotNull();
    Balance usdBalance = wallet.getBalance(Currency.USD);
    assertThat(usdBalance).isNotNull();
    assertThat(usdBalance.getTotal()).isNotNull().isPositive();
    assertThat(usdBalance.getAvailable()).isNotNull().isPositive();
  }

  @Test
  public void fundingHistoryTest() throws Exception {
    stubFor(
        get(urlPathEqualTo("/payment/history/transactions"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("transactions_deposit.json")));
    TradeHistoryParams params = accountService.createFundingHistoryParams();
    List<FundingRecord> fundingHistory = accountService.getFundingHistory(params);
    assertThat(fundingHistory).isNotEmpty();
    FundingRecord record = fundingHistory.get(0);
    assertThat(record).isNotNull();
    assertThat(record.getType()).isEqualTo(DEPOSIT);
    assertThat(record.getCurrency()).isEqualTo(Currency.RUR);
    assertThat(record.getAmount()).isNotNull().isPositive();
  }
}
