package org.knowm.xchange.bybit.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.ws.rs.core.Response.Status;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitAccountBalance;
import org.knowm.xchange.bybit.dto.account.BybitAccountType;
import org.knowm.xchange.bybit.dto.account.BybitCoinBalance;
import org.knowm.xchange.bybit.dto.account.BybitWalletBalance;

public class BybitAccountServiceRawTest extends BaseWiremockTest {

  @Test
  public void testGetWalletBalancesWithCoin() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitAccountServiceRaw bybitAccountServiceRaw = new BybitAccountServiceRaw(bybitExchange);

    stubFor(
        get(urlPathEqualTo("/v5/account/wallet-balance"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        IOUtils.resourceToString(
                            "/getWalletBalance.json5", StandardCharsets.UTF_8))));

    BybitResult<BybitWalletBalance> walletBalances =
        bybitAccountServiceRaw.getWalletBalances(BybitAccountType.UNIFIED);

    BybitWalletBalance walletBalance = walletBalances.getResult();
    BybitAccountBalance accountBalance = walletBalance.getList().get(0);

    assertThat(accountBalance.getTotalEquity()).isEqualTo("3.31216591");
    assertThat(accountBalance.getAccountIMRate()).isEqualTo("0");
    assertThat(accountBalance.getTotalMarginBalance()).isEqualTo("3.00326056");
    assertThat(accountBalance.getTotalInitialMargin()).isEqualTo("0");
    assertThat(accountBalance.getAccountType()).isEqualTo(BybitAccountType.UNIFIED);
    assertThat(accountBalance.getTotalAvailableBalance()).isEqualTo("3.00326056");
    assertThat(accountBalance.getAccountMMRate()).isEqualTo("0");
    assertThat(accountBalance.getTotalPerpUPL()).isEqualTo("0");
    assertThat(accountBalance.getTotalWalletBalance()).isEqualTo("3.00326056");
    assertThat(accountBalance.getAccountLTV()).isEqualTo("0");
    assertThat(accountBalance.getTotalMaintenanceMargin()).isEqualTo("0");

    List<BybitCoinBalance> coins = accountBalance.getCoins();

    assertThat(coins.get(0).getAvailableToBorrow()).isEqualTo("3");
    assertThat(coins.get(0).getBonus()).isEqualTo("0");
    assertThat(coins.get(0).getAccruedInterest()).isEqualTo("0");
    assertThat(coins.get(0).getAvailableToWithdraw()).isEqualTo("0");
    assertThat(coins.get(0).getTotalOrderIM()).isEqualTo("0");
    assertThat(coins.get(0).getEquity()).isEqualTo("0");
    assertThat(coins.get(0).getTotalPositionMM()).isEqualTo("0");
    assertThat(coins.get(0).getUsdValue()).isEqualTo("0");
    assertThat(coins.get(0).getUnrealisedPnl()).isEqualTo("0");
    assertThat(coins.get(0).isCollateralSwitch()).isEqualTo(true);
    assertThat(coins.get(0).getBorrowAmount()).isEqualTo("0.0");
    assertThat(coins.get(0).getTotalPositionIM()).isEqualTo("0");
    assertThat(coins.get(0).getWalletBalance()).isEqualTo("0");
    assertThat(coins.get(0).getCumRealisedPnl()).isEqualTo("0");
    assertThat(coins.get(0).getLocked()).isEqualTo("0");
    assertThat(coins.get(0).isMarginCollateral()).isEqualTo(true);
    assertThat(coins.get(0).getCoin()).isEqualTo("BTC");
  }
}
