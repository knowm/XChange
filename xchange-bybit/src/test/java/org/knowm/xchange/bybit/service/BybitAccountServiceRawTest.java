package org.knowm.xchange.bybit.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitBalances;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

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
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(walletBalanceDetails)
                        )
        );

        BybitResult<BybitBalances> walletBalances = bybitAccountServiceRaw.getWalletBalances();
        System.out.println(walletBalances);
    }

}
