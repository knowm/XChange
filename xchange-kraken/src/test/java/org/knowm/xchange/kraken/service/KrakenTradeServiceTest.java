package org.knowm.xchange.kraken.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class KrakenTradeServiceTest extends BaseWiremockTest {

    private KrakenTradeService classUnderTest;

    public static final String WIREMOCK_FILES_PATH = "__files";
    private static final String ORDERS_FILE_NAME = "example-open-orders-data.json";
    private static final String TRADES_FILE_NAME = "example-trade-history-data.json";

    @Before
    public void setup() {
        classUnderTest = (KrakenTradeService) createExchange().getTradeService();
    }

    @Test
    public void ordersTest() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonRoot = mapper.readTree(
                this.getClass().
                        getResource("/" + WIREMOCK_FILES_PATH + "/" + ORDERS_FILE_NAME));

        stubFor(
                post(urlPathEqualTo("/0/private/OpenOrders"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile(ORDERS_FILE_NAME)
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
    public void tradeHistoryTest() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonRoot = mapper.readTree(
                this.getClass().
                        getResource("/" + WIREMOCK_FILES_PATH + "/" + TRADES_FILE_NAME));

        stubFor(
                post(urlPathEqualTo("/0/private/TradesHistory"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile(TRADES_FILE_NAME)
                        )
        );

        TradeHistoryParams tradeHistoryParams = classUnderTest.createTradeHistoryParams();

        UserTrades userTrades = classUnderTest.getTradeHistory(tradeHistoryParams);

        assertThat(userTrades).isNotNull();
        assertThat(userTrades.getUserTrades()).hasSize(jsonRoot.get("result").
                get("trades").size());
        UserTrade firstUserTrade = userTrades.getUserTrades().get(0);
        assertThat(firstUserTrade).isNotNull();
        assertThat(firstUserTrade.getOriginalAmount()).isNotNull().isPositive();
        assertThat(firstUserTrade.getId()).isNotBlank();
        assertThat(firstUserTrade.getInstrument()).isEqualTo(CurrencyPair.BTC_USD);

        UserTrade secondUserTrade = userTrades.getUserTrades().get(1);
        assertThat(secondUserTrade).isNotNull();
        assertThat(secondUserTrade.getOriginalAmount()).isNotNull().isPositive();
        assertThat(secondUserTrade.getId()).isNotBlank();
        assertThat(secondUserTrade.getInstrument()).isEqualTo(CurrencyPair.LTC_USD);

    }

    @Test
    public void tradeHistoryTestByCurrencyPairTest() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonRoot = mapper.readTree(
                this.getClass().
                        getResource("/" + WIREMOCK_FILES_PATH + "/" + TRADES_FILE_NAME));

        stubFor(
                post(urlPathEqualTo("/0/private/TradesHistory"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile(TRADES_FILE_NAME)
                        )
        );

        TradeHistoryParams tradeHistoryParams = new DefaultTradeHistoryParamCurrencyPair(CurrencyPair.LTC_BTC);

        UserTrades userTrades = classUnderTest.getTradeHistory(tradeHistoryParams);

        assertThat(userTrades).isNotNull();
        assertThat(userTrades.getUserTrades()).hasSize(2);
        UserTrade firstUserTrade = userTrades.getUserTrades().get(0);
        assertThat(firstUserTrade).isNotNull();
        assertThat(firstUserTrade.getOriginalAmount()).isNotNull().isPositive();
        assertThat(firstUserTrade.getId()).isNotBlank();
        assertThat(firstUserTrade.getInstrument()).isEqualTo(CurrencyPair.LTC_BTC);

        UserTrade secondUserTrade = userTrades.getUserTrades().get(1);
        assertThat(secondUserTrade).isNotNull();
        assertThat(secondUserTrade.getOriginalAmount()).isNotNull().isPositive();
        assertThat(secondUserTrade.getId()).isNotBlank();
        assertThat(secondUserTrade.getInstrument()).isEqualTo(CurrencyPair.LTC_BTC);
    }
}