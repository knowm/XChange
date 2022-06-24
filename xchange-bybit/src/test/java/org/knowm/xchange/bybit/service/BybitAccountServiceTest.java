package org.knowm.xchange.bybit.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitBalances;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BybitAccountServiceTest extends BaseWiremockTest {

    @Test
    public void testGetWalletBalances() throws IOException {
        Exchange bybitExchange = createExchange();
        BybitAccountService bybitAccountService = new BybitAccountService(bybitExchange);

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
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(walletBalanceDetails)
                        )
        );


        AccountInfo accountInfo = bybitAccountService.getAccountInfo();
        assertThat(accountInfo.getWallet().getBalance(new Currency("COIN")).getTotal()).isEqualTo(new BigDecimal("66419.616666666666666666"));
        assertThat(accountInfo.getWallet().getBalance(new Currency("COIN")).getAvailable()).isEqualTo(new BigDecimal("56583.326666666666666666"));

        assertThat(accountInfo.getWallet().getBalance(new Currency("USDT")).getTotal()).isEqualTo(new BigDecimal("61.50059688096"));
        assertThat(accountInfo.getWallet().getBalance(new Currency("USDT")).getAvailable()).isEqualTo(new BigDecimal("61.50059688096"));
    }

}
