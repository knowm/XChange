package org.knowm.xchange.mexc.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;

import java.io.IOException;
import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MEXCAccountServiceTest extends BaseWiremockTest {

    @Test
    public void testGetWalletBalances() throws IOException {
        Exchange mexcExchange = createExchange();
        MEXCAccountService mexcAccountService = new MEXCAccountService(mexcExchange);

        String walletBalanceDetails = "{\n" +
                "    \"code\": 200,\n" +
                "    \"data\": {\n" +
                "        \"BTC\": {\n" +
                "            \"frozen\": \"0\",\n" +
                "            \"available\": \"140\"\n" +
                "        },\n" +
                "        \"ETH\": {\n" +
                "            \"frozen\": \"8471.296525048\",\n" +
                "            \"available\": \"483280.9653659222035\"\n" +
                "        },\n" +
                "        \"USDT\": {\n" +
                "            \"frozen\": \"0\",\n" +
                "            \"available\": \"27.3629\"\n" +
                "        },\n" +
                "        \"MX\": {\n" +
                "            \"frozen\": \"30.9863\",\n" +
                "            \"available\": \"450.0137\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        stubFor(
                get(urlPathEqualTo("/open/api/v2/account/info"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(walletBalanceDetails)
                        )
        );

        AccountInfo accountInfo = mexcAccountService.getAccountInfo();
        assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getFrozen()).isEqualTo(new BigDecimal("0"));
        assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getAvailable()).isEqualTo(new BigDecimal("140"));
        assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getTotal()).isEqualTo(new BigDecimal("140"));

        assertThat(accountInfo.getWallet().getBalance(new Currency("ETH")).getFrozen()).isEqualTo(new BigDecimal("8471.2965250480000"));
        assertThat(accountInfo.getWallet().getBalance(new Currency("ETH")).getAvailable()).isEqualTo(new BigDecimal("483280.9653659222035"));
        assertThat(accountInfo.getWallet().getBalance(new Currency("ETH")).getTotal()).isEqualTo(new BigDecimal("491752.2618909702035"));

        assertThat(accountInfo.getWallet().getBalance(new Currency("USDT")).getFrozen()).isEqualTo(new BigDecimal("0.0000"));
        assertThat(accountInfo.getWallet().getBalance(new Currency("USDT")).getAvailable()).isEqualTo(new BigDecimal("27.3629"));
        assertThat(accountInfo.getWallet().getBalance(new Currency("USDT")).getTotal()).isEqualTo(new BigDecimal("27.3629"));

        assertThat(accountInfo.getWallet().getBalance(new Currency("MX")).getFrozen()).isEqualTo(new BigDecimal("30.9863"));
        assertThat(accountInfo.getWallet().getBalance(new Currency("MX")).getAvailable()).isEqualTo(new BigDecimal("450.0137"));
        assertThat(accountInfo.getWallet().getBalance(new Currency("MX")).getTotal()).isEqualTo(new BigDecimal("481.0000"));

    }

}
