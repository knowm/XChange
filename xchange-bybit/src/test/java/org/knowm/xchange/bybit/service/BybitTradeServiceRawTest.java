package org.knowm.xchange.bybit.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.BybitOrderRequest;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class BybitTradeServiceRawTest extends BaseWiremockTest{

    @Test
    public void testGetBybitOrder() throws IOException {
        Exchange bybitExchange = createExchange();
        BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);

        String orderDetails = "{\n" +
                "   \"ret_code\":0,\n" +
                "   \"ret_msg\":\"\",\n" +
                "   \"ext_code\":null,\n" +
                "   \"ext_info\":null,\n" +
                "   \"result\":{\n" +
                "      \"accountId\":\"123456789\",\n" +
                "      \"exchangeId\":\"301\",\n" +
                "      \"symbol\":\"COINUSDT\",\n" +
                "      \"symbolName\":\"COINUSDT\",\n" +
                "      \"orderLinkId\":\"1234567891011121\",\n" +
                "      \"orderId\":\"1234567891011121314\",\n" +
                "      \"price\":\"0\",\n" +
                "      \"origQty\":\"352\",\n" +
                "      \"executedQty\":\"352\",\n" +
                "      \"cummulativeQuoteQty\":\"0.569888\",\n" +
                "      \"avgPrice\":\"0.001619\",\n" +
                "      \"status\":\"FILLED\",\n" +
                "      \"timeInForce\":\"GTC\",\n" +
                "      \"type\":\"MARKET\",\n" +
                "      \"side\":\"SELL\",\n" +
                "      \"stopPrice\":\"0.0\",\n" +
                "      \"icebergQty\":\"0.0\",\n" +
                "      \"time\":\"1655997749601\",\n" +
                "      \"updateTime\":\"1655997749662\",\n" +
                "      \"isWorking\":true,\n" +
                "      \"locked\":\"0\"\n" +
                "   }\n" +
                "}";

        stubFor(
                get(urlPathEqualTo("/spot/v1/order"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(orderDetails)
                        )
        );
        BybitResult<BybitOrderDetails> order = bybitAccountServiceRaw.getBybitOrder("1234567891011121314");

        //{"ret_code":0,"ret_msg":"","ext_code":null,"ext_info":null,"result":{"accountId":"28649557","exchangeId":"301","symbol":"IMEUSDT","symbolName":"IMEUSDT","orderLinkId":"1655997749596563","orderId":"1184989442799045889","price":"0","origQty":"352","executedQty":"352","cummulativeQuoteQty":"0.569888","avgPrice":"0.001619","status":"FILLED","timeInForce":"GTC","type":"MARKET","side":"SELL","stopPrice":"0.0","icebergQty":"0.0","time":"1655997749601","updateTime":"1655997749662","isWorking":true,"locked":"0"}}
        System.out.println(order);
    }


    @Test
    public void testPlaceBybitOrder() throws IOException {
        Exchange bybitExchange = createExchange();
        BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);

        String orderPlacementResponse = "{\n" +
                "   \"ret_code\":0,\n" +
                "   \"ret_msg\":\"\",\n" +
                "   \"ext_code\":null,\n" +
                "   \"ext_info\":null,\n" +
                "   \"result\":{\n" +
                "      \"accountId\":\"28649557\",\n" +
                "      \"exchangeId\":\"301\",\n" +
                "      \"symbol\":\"IMEUSDT\",\n" +
                "      \"symbolName\":\"IMEUSDT\",\n" +
                "      \"orderLinkId\":\"1655997749596563\",\n" +
                "      \"orderId\":\"1184989442799045889\",\n" +
                "      \"price\":\"0\",\n" +
                "      \"origQty\":\"352\",\n" +
                "      \"executedQty\":\"352\",\n" +
                "      \"cummulativeQuoteQty\":\"0.569888\",\n" +
                "      \"avgPrice\":\"0.001619\",\n" +
                "      \"status\":\"FILLED\",\n" +
                "      \"timeInForce\":\"GTC\",\n" +
                "      \"type\":\"MARKET\",\n" +
                "      \"side\":\"SELL\",\n" +
                "      \"stopPrice\":\"0.0\",\n" +
                "      \"icebergQty\":\"0.0\",\n" +
                "      \"time\":\"1655997749601\",\n" +
                "      \"updateTime\":\"1655997749662\",\n" +
                "      \"isWorking\":true,\n" +
                "      \"locked\":\"0\"\n" +
                "   }\n" +
                "}";

        stubFor(
                post(urlPathEqualTo("/spot/v1/order"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(orderPlacementResponse)
                        )
        );

        BybitResult<BybitOrderRequest> order = bybitAccountServiceRaw.placeOrder(
                "IMEUSDT",
                300,
                "SELL",
                "MARKET"
        );

        System.out.println(order);
    }

}
