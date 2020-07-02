package org.knowm.xchange.bittrex.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

/** @author walec51 */
public class AccountMockedTestIntegration extends BaseMockedTestIntegration {

  private static BittrexAccountService accountService;
  private static final String BALANCES_FILE_NAME = "balances.json";

  @Before
  public void setUp() {
    accountService = (BittrexAccountService) createExchange().getAccountService();
  }

  @Test
  public void accountInfoTest() throws Exception {
    stubFor(
        get(urlEqualTo("/v3/balances"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile(BALANCES_FILE_NAME)));
    AccountInfo accountInfo = accountService.getAccountInfo();
    assertThat(accountInfo).isNotNull();

    Wallet wallet = accountInfo.getWallet();
    assertThat(wallet).isNotNull();

    // What's in the mocked json
    final ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonRoot =
        mapper.readTree(
            this.getClass().getResource("/" + WIREMOCK_FILES_PATH + "/" + BALANCES_FILE_NAME));
    JsonNode jsonBtcBalance = jsonRoot.get(0);
    Currency expectedCurrency = new Currency(jsonBtcBalance.get("currencySymbol").textValue());
    BigDecimal expectedTotal = new BigDecimal(jsonBtcBalance.get("total").textValue());
    BigDecimal expectedAvailable = new BigDecimal(jsonBtcBalance.get("available").textValue());
    BigDecimal expectedFrozen = expectedTotal.subtract(expectedAvailable);
    Date expectedTimestamp =
        Date.from(ZonedDateTime.parse(jsonBtcBalance.get("updatedAt").textValue()).toInstant());
    int expectedNumberOfBalances = jsonRoot.size();

    Balance btcBalance = wallet.getBalance(expectedCurrency);

    assertThat(wallet.getBalances().size()).isEqualTo(expectedNumberOfBalances);
    assertThat(btcBalance.getCurrency()).isEqualTo(expectedCurrency);
    assertThat(btcBalance.getTotal().compareTo(expectedTotal)).isEqualTo(0);
    assertThat(btcBalance.getAvailable().compareTo(expectedAvailable)).isEqualTo(0);
    assertThat(btcBalance.getFrozen().compareTo(expectedFrozen)).isEqualTo(0);
    assertThat(btcBalance.getTimestamp()).isEqualTo(expectedTimestamp);
  }
}
