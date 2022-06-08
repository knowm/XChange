package org.knowm.xchange.blockchain.service.trade;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.service.BlockchainBaseTest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.service.trade.TradeService;
import si.mazi.rescu.HttpStatusIOException;

import java.io.IOException;
import java.math.BigDecimal;

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
        OpenOrders response = getOpenOrders();
        System.out.println(response);
        Assert.assertNotNull(response);
    }

    @Test(timeout = 2000)
    public void placeLimitOrderSuccess() throws Exception {
        String response = placeLimitOrder();
        System.out.println(response);
        Assert.assertNotNull(response);
    }

    @Test(timeout = 2000)
    public void placeMarketOrderSuccess() throws Exception {
        String response = placeMarketOrder();
        System.out.println(response);
        Assert.assertNotNull(response);
    }

    @Test(timeout = 2000)
    public void placeStopOrderSuccess() throws Exception {
        String response = placeStopOrder();
        System.out.println(response);
        Assert.assertNotNull(response);
    }

    @Test(timeout = 2000)
    public void cancelOrderSuccess() throws Exception {
        Boolean response = cancelOrder(200, ORDER_ID);
        assertThat(response).isEqualTo(true);
    }

    @Test(timeout = 2000)
    public void cancelOrderFailure() throws Exception {
        Throwable exception = catchThrowable(() -> cancelOrder(400, ORDER_ID));
        assertThat(exception)
                .isInstanceOf(HttpStatusIOException.class)
                .hasMessage(HTTP_CODE_400);
    }

    private OpenOrders getOpenOrders() throws IOException {
        stubGet(ORDERS_JSON, 200, URL_ORDERS);

        return service.getOpenOrders();
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

    private Boolean cancelOrder(int statusCode, String orderId) throws IOException {
        stubDelete(statusCode, URL_ORDERS_DELETE);

        return service.cancelOrder(orderId);
    }
}
