package org.knowm.xchange.blockchain.service.trade;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.params.BlockchainTradeHistoryParams;
import org.knowm.xchange.blockchain.service.BlockchainBaseTest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import si.mazi.rescu.HttpStatusIOException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.*;

public class TradeServiceTest extends BlockchainBaseTest {
    private TradeService service;

    @Before
    public void init() {
        BlockchainExchange exchange = createExchange();
        service = exchange.getTradeService();
    }

    @Test(timeout = 2000)
    public void getOpenOrdersSuccess() throws Exception {
        stubGet(ORDERS_JSON, URL_ORDERS);
        OpenOrders response = service.getOpenOrders();
        assertThat(response).isNotNull();
        assertThat(response.getAllOpenOrders()).isNotEmpty();
        Order order = response.getAllOpenOrders().get(0);
        assertThat(order).isNotNull();
        assertThat(order.getOriginalAmount()).isNotNull().isPositive();
        assertThat(order.getId()).isEqualTo(ORDER_ID);
    }

    @Test(timeout = 2000)
    public void placeLimitOrderSuccess() throws Exception {
        String response = placeLimitOrder();
        assertThat(response).isEqualTo(ORDER_ID);
    }

    @Test(timeout = 2000)
    public void placeMarketOrderSuccess() throws Exception {
        String response = placeMarketOrder();
        assertThat(response).isEqualTo(MARKET_ORDER_ID);
    }

    @Test(timeout = 2000)
    public void placeStopOrderSuccess() throws Exception {
        String response = placeStopOrder();
        assertThat(response).isEqualTo(STOP_ORDER_ID);
    }

    @Test(timeout = 2000)
    public void cancelOrderSuccess() throws Exception {
        Boolean response = cancelOrder(200);
        assertThat(response).isEqualTo(true);
    }

    @Test(timeout = 2000)
    public void cancelOrderFailure() {
        Throwable exception = catchThrowable(() -> cancelOrder(400));
        assertThat(exception)
                .isInstanceOf(HttpStatusIOException.class)
                .hasMessage(HTTP_CODE_400);
    }

    @Test(timeout = 2000)
    public void cancelOrderByCurrency() throws Exception {
        CancelOrderByCurrencyPair cancelOrderByCurrencyPair = () -> new CurrencyPair("BTC/USD");
        Boolean response = cancelAllOrder(cancelOrderByCurrencyPair);
        assertThat(response).isEqualTo(true);
    }

    @Test(timeout = 2000)
    public void getTrades() throws Exception {
        BlockchainTradeHistoryParams params = (BlockchainTradeHistoryParams) service.createTradeHistoryParams();
        ((TradeHistoryParamsTimeSpan) params).setStartTime(
                new Date(System.currentTimeMillis() - END_TIME));

        params.setCurrencyPair(CurrencyPair.BTC_USDT);

        stubGet(ORDERS_JSON, URL_TRADES);
        UserTrades response = service.getTradeHistory(params);
        assertThat(response).isNotNull();
        assertThat(response.getUserTrades()).isNotEmpty();
        UserTrade trade = response.getUserTrades().get(0);
        assertThat(trade).isNotNull();
        assertThat(trade.getOriginalAmount()).isNotNull().isPositive();
        assertThat(trade.getPrice()).isNotNull().isPositive();
    }

    @Test(timeout = 2000)
    public void getOrderSuccess() throws Exception {
        stubGet(NEW_ORDER_LIMIT_JSON, URL_ORDERS_BY_ID_1);
        stubGet(NEW_ORDER_MARKET_JSON, URL_ORDERS_BY_ID_2);
        Collection<Order> response = service.getOrder("11111111", "22222222");
        Assert.assertNotNull(response);
        response.forEach(
                record -> Assert.assertTrue(record.getOriginalAmount().compareTo(BigDecimal.ZERO) > 0));

    }

    private String placeLimitOrder() throws IOException {
        stubPost(NEW_ORDER_LIMIT_JSON, 200, URL_ORDERS);

        LimitOrder limitOrder =
                new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                        .originalAmount(new BigDecimal("45.0"))
                        .limitPrice(new BigDecimal("0.23"))
                        .build();

        return service.placeLimitOrder(limitOrder);
    }

    private String placeMarketOrder() throws IOException {
        stubPost(NEW_ORDER_MARKET_JSON, 200, URL_ORDERS);

        MarketOrder marketOrder =
                new MarketOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                        .originalAmount(new BigDecimal("15.0"))
                        .cumulativeAmount(new BigDecimal("0.22"))
                        .build();

        return service.placeMarketOrder(marketOrder);
    }

    private String placeStopOrder() throws IOException {
        stubPost(NEW_ORDER_STOP_JSON, 200, URL_ORDERS);

        StopOrder stopOrder =
                new StopOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                        .originalAmount(new BigDecimal("67.0"))
                        .stopPrice(new BigDecimal("0.21"))
                        .build();

        return service.placeStopOrder(stopOrder);
    }

    private Boolean cancelOrder(int statusCode) throws IOException {
        stubDelete(statusCode, URL_ORDERS_BY_ID_1);

        return service.cancelOrder(ORDER_ID);
    }

    private Boolean cancelAllOrder(CancelOrderByCurrencyPair orderParams) throws IOException {
        stubDelete(200, URL_ORDERS);

        return service.cancelOrder(orderParams);
    }
}
