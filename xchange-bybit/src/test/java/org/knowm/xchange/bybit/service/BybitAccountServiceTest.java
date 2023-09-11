package org.knowm.xchange.bybit.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.ws.rs.core.Response.Status;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;

public class BybitAccountServiceTest extends BaseWiremockTest {

  @Test
  public void testGetWalletBalances() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitAccountService bybitAccountService =
        new BybitAccountService(bybitExchange, BybitAccountType.UNIFIED);

    stubFor(
        get(urlPathEqualTo("/v5/account/wallet-balance"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        IOUtils.resourceToString(
                            "/getWalletBalance.json5", StandardCharsets.UTF_8))));

    AccountInfo accountInfo = bybitAccountService.getAccountInfo();
    assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getTotal())
        .isEqualTo(new BigDecimal("0"));
    assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getAvailable())

        .isEqualTo(new BigDecimal("0"));
  }
}
