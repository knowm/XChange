package org.knowm.xchange.kraken.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class KrakenTradeServiceTest extends BaseWiremockTest {

    private KrakenTradeService classUnderTest;

    @Before
    public void setup() {
        classUnderTest = (KrakenTradeService) createExchange().getTradeService();
    }

    @Test
    public void ordersTest() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonRoot = mapper.readTree(OPEN_ORDERS_BODY);

        stubFor(
                post(urlPathEqualTo("/0/private/OpenOrders"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(OPEN_ORDERS_BODY)
                        )
        );

        OpenOrders openOrders = classUnderTest.getOpenOrders();

        assertThat(openOrders).isNotNull();
        assertThat(openOrders.getOpenOrders()).hasSize(jsonRoot.get("result").
                get("open").size());
        LimitOrder firstOrder = openOrders.getOpenOrders().get(0);
        assertThat(firstOrder).isNotNull();
        assertThat(firstOrder.getOriginalAmount()).isNotNull().isPositive();
        assertThat(firstOrder.getId()).isNotBlank();
        assertThat(firstOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_USD);

        LimitOrder secondOrder = openOrders.getOpenOrders().get(1);
        assertThat(secondOrder).isNotNull();
        assertThat(secondOrder.getOriginalAmount()).isNotNull().isPositive();
        assertThat(secondOrder.getId()).isNotBlank();
        assertThat(secondOrder.getInstrument()).isEqualTo(CurrencyPair.LTC_USD);

    }

    @Test
    public void openOrdersByCurrencyPairTest() throws Exception {

        stubFor(
                post(urlPathEqualTo("/0/private/OpenOrders"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(OPEN_ORDERS_BODY)
                        )
        );

        DefaultOpenOrdersParamCurrencyPair defaultOpenOrdersParamCurrencyPair =
                new DefaultOpenOrdersParamCurrencyPair(CurrencyPair.BTC_USD);

        OpenOrders openOrders = classUnderTest.getOpenOrders(defaultOpenOrdersParamCurrencyPair);
        assertThat(openOrders).isNotNull();
        assertThat(openOrders.getOpenOrders().size()).isEqualTo(3);
        LimitOrder firstOrder = openOrders.getOpenOrders().get(0);
        assertThat(firstOrder).isNotNull();
        assertThat(firstOrder.getOriginalAmount()).isNotNull().isPositive();
        assertThat(firstOrder.getId()).isNotBlank();
        assertThat(firstOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_USD);

        LimitOrder secondOrder = openOrders.getOpenOrders().get(1);
        assertThat(secondOrder).isNotNull();
        assertThat(secondOrder.getOriginalAmount()).isNotNull().isPositive();
        assertThat(secondOrder.getId()).isNotBlank();
        assertThat(secondOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_USD);
    }

    private static String OPEN_ORDERS_BODY = "{\n" +
            "  \"error\": [],\n" +
            "  \"result\": {\n" +
            "    \"open\": {\n" +
            "      \"OQCLML-BW3P3-BUCMWZ\": {\n" +
            "        \"refid\": null,\n" +
            "        \"userref\": 0,\n" +
            "        \"status\": \"open\",\n" +
            "        \"opentm\": 1616666559.8974,\n" +
            "        \"starttm\": 0,\n" +
            "        \"expiretm\": 0,\n" +
            "        \"descr\": {\n" +
            "          \"pair\": \"XBTUSD\",\n" +
            "          \"type\": \"buy\",\n" +
            "          \"ordertype\": \"limit\",\n" +
            "          \"price\": \"30010.0\",\n" +
            "          \"price2\": \"0\",\n" +
            "          \"leverage\": \"none\",\n" +
            "          \"order\": \"buy 1.25000000 XBTUSD @ limit 30010.0\",\n" +
            "          \"close\": \"\"\n" +
            "        },\n" +
            "        \"vol\": \"1.25000000\",\n" +
            "        \"vol_exec\": \"0.37500000\",\n" +
            "        \"cost\": \"11253.7\",\n" +
            "        \"fee\": \"0.00000\",\n" +
            "        \"price\": \"30010.0\",\n" +
            "        \"stopprice\": \"0.00000\",\n" +
            "        \"limitprice\": \"0.00000\",\n" +
            "        \"misc\": \"\",\n" +
            "        \"oflags\": \"fciq\",\n" +
            "        \"trades\": [\n" +
            "          \"TCCCTY-WE2O6-P3NB37\"\n" +
            "        ]\n" +
            "      },\n" +
            "      \"OB5VMB-B4U2U-DK2WRW\": {\n" +
            "        \"refid\": null,\n" +
            "        \"userref\": 120,\n" +
            "        \"status\": \"open\",\n" +
            "        \"opentm\": 1616665899.5699,\n" +
            "        \"starttm\": 0,\n" +
            "        \"expiretm\": 0,\n" +
            "        \"descr\": {\n" +
            "          \"pair\": \"LTCUSD\",\n" +
            "          \"type\": \"buy\",\n" +
            "          \"ordertype\": \"limit\",\n" +
            "          \"price\": \"14500.0\",\n" +
            "          \"price2\": \"0\",\n" +
            "          \"leverage\": \"5:1\",\n" +
            "          \"order\": \"buy 0.27500000 LTCUSD @ limit 14500.0 with 5:1 leverage\",\n" +
            "          \"close\": \"\"\n" +
            "        },\n" +
            "        \"vol\": \"0.27500000\",\n" +
            "        \"vol_exec\": \"0.00000000\",\n" +
            "        \"cost\": \"0.00000\",\n" +
            "        \"fee\": \"0.00000\",\n" +
            "        \"price\": \"0.00000\",\n" +
            "        \"stopprice\": \"0.00000\",\n" +
            "        \"limitprice\": \"0.00000\",\n" +
            "        \"misc\": \"\",\n" +
            "        \"oflags\": \"fciq\"\n" +
            "      },\n" +
            "      \"OXHXGL-F5ICS-6DIC67\": {\n" +
            "        \"refid\": null,\n" +
            "        \"userref\": 120,\n" +
            "        \"status\": \"open\",\n" +
            "        \"opentm\": 1616665894.0036,\n" +
            "        \"starttm\": 0,\n" +
            "        \"expiretm\": 0,\n" +
            "        \"descr\": {\n" +
            "          \"pair\": \"LTCUSD\",\n" +
            "          \"type\": \"buy\",\n" +
            "          \"ordertype\": \"limit\",\n" +
            "          \"price\": \"17500.0\",\n" +
            "          \"price2\": \"0\",\n" +
            "          \"leverage\": \"5:1\",\n" +
            "          \"order\": \"buy 0.27500000 LTCUSD @ limit 17500.0 with 5:1 leverage\",\n" +
            "          \"close\": \"\"\n" +
            "        },\n" +
            "        \"vol\": \"0.27500000\",\n" +
            "        \"vol_exec\": \"0.00000000\",\n" +
            "        \"cost\": \"0.00000\",\n" +
            "        \"fee\": \"0.00000\",\n" +
            "        \"price\": \"0.00000\",\n" +
            "        \"stopprice\": \"0.00000\",\n" +
            "        \"limitprice\": \"0.00000\",\n" +
            "        \"misc\": \"\",\n" +
            "        \"oflags\": \"fciq\"\n" +
            "      },\n" +
            "      \"OLQCVY-B27XU-MBPCL5\": {\n" +
            "        \"refid\": null,\n" +
            "        \"userref\": 251,\n" +
            "        \"status\": \"open\",\n" +
            "        \"opentm\": 1616665556.7646,\n" +
            "        \"starttm\": 0,\n" +
            "        \"expiretm\": 0,\n" +
            "        \"descr\": {\n" +
            "          \"pair\": \"XBTUSD\",\n" +
            "          \"type\": \"buy\",\n" +
            "          \"ordertype\": \"limit\",\n" +
            "          \"price\": \"23500.0\",\n" +
            "          \"price2\": \"0\",\n" +
            "          \"leverage\": \"none\",\n" +
            "          \"order\": \"buy 0.27500000 XBTUSD @ limit 23500.0\",\n" +
            "          \"close\": \"\"\n" +
            "        },\n" +
            "        \"vol\": \"0.27500000\",\n" +
            "        \"vol_exec\": \"0.00000000\",\n" +
            "        \"cost\": \"0.00000\",\n" +
            "        \"fee\": \"0.00000\",\n" +
            "        \"price\": \"0.00000\",\n" +
            "        \"stopprice\": \"0.00000\",\n" +
            "        \"limitprice\": \"0.00000\",\n" +
            "        \"misc\": \"\",\n" +
            "        \"oflags\": \"fciq\"\n" +
            "      },\n" +
            "      \"OQCGAF-YRMIQ-AMJTNJ\": {\n" +
            "        \"refid\": null,\n" +
            "        \"userref\": 0,\n" +
            "        \"status\": \"open\",\n" +
            "        \"opentm\": 1616665511.0373,\n" +
            "        \"starttm\": 0,\n" +
            "        \"expiretm\": 0,\n" +
            "        \"descr\": {\n" +
            "          \"pair\": \"XBTUSD\",\n" +
            "          \"type\": \"buy\",\n" +
            "          \"ordertype\": \"limit\",\n" +
            "          \"price\": \"24500.0\",\n" +
            "          \"price2\": \"0\",\n" +
            "          \"leverage\": \"none\",\n" +
            "          \"order\": \"buy 1.25000000 XBTUSD @ limit 24500.0\",\n" +
            "          \"close\": \"\"\n" +
            "        },\n" +
            "        \"vol\": \"1.25000000\",\n" +
            "        \"vol_exec\": \"0.00000000\",\n" +
            "        \"cost\": \"0.00000\",\n" +
            "        \"fee\": \"0.00000\",\n" +
            "        \"price\": \"0.00000\",\n" +
            "        \"stopprice\": \"0.00000\",\n" +
            "        \"limitprice\": \"0.00000\",\n" +
            "        \"misc\": \"\",\n" +
            "        \"oflags\": \"fciq\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
}