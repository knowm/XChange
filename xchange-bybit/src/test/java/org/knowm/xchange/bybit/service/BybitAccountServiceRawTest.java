package org.knowm.xchange.bybit.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import jakarta.ws.rs.core.Response.Status;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitBalance;
import org.knowm.xchange.bybit.dto.account.BybitBalances;

public class BybitAccountServiceRawTest extends BaseWiremockTest {

  @Test
  public void testGetWalletBalances() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitAccountServiceRaw bybitAccountServiceRaw = new BybitAccountServiceRaw(bybitExchange);

    String walletBalanceDetails = "{\n" +
        "   \"ret_code\":0,\n" +
        "   \"ret_msg\":\"\",\n" +
        "   \"ext_code\":null,\n" +
        "   \"ext_info\":null,\n" +
        "   \"result\":{\n" +
        "      \"balances\":[\n" +
        "         {\n" +
        "            \"coin\":\"COIN\",\n" +
        "            \"coinId\":\"COIN\",\n" +
        "            \"coinName\":\"COIN\",\n" +
        "            \"total\":\"66419.616666666666666666\",\n" +
        "            \"free\":\"56583.326666666666666666\",\n" +
        "            \"locked\":\"9836.29\"\n" +
        "         },\n" +
        "         {\n" +
        "            \"coin\":\"USDT\",\n" +
        "            \"coinId\":\"USDT\",\n" +
        "            \"coinName\":\"USDT\",\n" +
        "            \"total\":\"61.50059688096\",\n" +
        "            \"free\":\"61.50059688096\",\n" +
        "            \"locked\":\"0\"\n" +
        "         }\n" +
        "      ]\n" +
        "   }\n" +
        "}";

    stubFor(
        get(urlPathEqualTo("/spot/v1/account"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(walletBalanceDetails)
            )
    );

    BybitResult<BybitBalances> walletBalances = bybitAccountServiceRaw.getWalletBalances();

    BybitBalances walletBalancesResult = walletBalances.getResult();
    List<BybitBalance> balances = walletBalancesResult.getBalances();

    assertThat(balances.get(0).getTotal()).isEqualTo("66419.616666666666666666");
    assertThat(balances.get(0).getFree()).isEqualTo("56583.326666666666666666");
    assertThat(balances.get(0).getLocked()).isEqualTo("9836.29");
    assertThat(balances.get(0).getCoin()).isEqualTo("COIN");
    assertThat(balances.get(0).getCoinId()).isEqualTo("COIN");
    assertThat(balances.get(0).getCoinName()).isEqualTo("COIN");

    assertThat(balances.get(1).getTotal()).isEqualTo("61.50059688096");
    assertThat(balances.get(1).getFree()).isEqualTo("61.50059688096");
    assertThat(balances.get(1).getLocked()).isEqualTo("0");
    assertThat(balances.get(1).getCoin()).isEqualTo("USDT");
    assertThat(balances.get(1).getCoinId()).isEqualTo("USDT");
    assertThat(balances.get(1).getCoinName()).isEqualTo("USDT");
  }

}
