package org.knowm.xchange.krakenfutures;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesOrderFlags;
import org.knowm.xchange.service.trade.params.DefaultCancelAllOrdersByInstrument;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Ignore
public class KrakenFuturesPrivateDataTest {

    Exchange exchange;
    Instrument instrument = new FuturesContract("BTC/USD/PERP");

    @Before
    public void setUp(){

        Properties properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream("/secret.keys"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExchangeSpecification spec = new ExchangeSpecification(KrakenFuturesExchange.class);
        spec.setApiKey(properties.getProperty("apiKey"));
        spec.setSecretKey(properties.getProperty("secret"));
        spec.setExchangeSpecificParametersItem(KrakenFuturesExchange.USE_SANDBOX, true);

        exchange = ExchangeFactory.INSTANCE.createExchange(spec);
    }

    @Test
    public void checkAccount() throws IOException {
        AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
        System.out.println(accountInfo);
        assertThat(accountInfo.getWallet(Wallet.WalletFeature.FUTURES_TRADING)).isNotNull();
        assertThat(Objects.requireNonNull(accountInfo.getWallet(Wallet.WalletFeature.FUTURES_TRADING)).getCurrentLeverage()).isGreaterThan(BigDecimal.ZERO);
    }

    @Test
    public void placeOrderCheckOpenOrdersAndCancel() throws IOException {
        String orderId = exchange.getTradeService().placeLimitOrder(new LimitOrder.Builder(Order.OrderType.BID,instrument)
                        .limitPrice(BigDecimal.valueOf(1000))
                        .originalAmount(BigDecimal.ONE)
                .build());
        List<LimitOrder> openOrders = exchange.getTradeService().getOpenOrders().getOpenOrders();
        System.out.println(openOrders.get(0).toString());
        assertThat(openOrders.get(0).getInstrument()).isEqualTo(instrument);
        assertThat(openOrders.get(0).getId()).isEqualTo(orderId);
        assertThat(exchange.getTradeService().cancelOrder(orderId)).isTrue();
    }

    @Test
    public void changeOrderAndGetOpenOrders() throws IOException {
        exchange.getTradeService().cancelAllOrders(new DefaultCancelAllOrdersByInstrument(instrument));
        String clientId = "12345";
        String orderId = exchange.getTradeService().placeLimitOrder(new LimitOrder.Builder(Order.OrderType.BID,instrument)
                .limitPrice(BigDecimal.valueOf(1000))
                .originalAmount(BigDecimal.ONE)
                        .userReference(clientId)
                .flag(KrakenFuturesOrderFlags.POST_ONLY)
                .build());
        List<LimitOrder> openOrders = exchange.getTradeService().getOpenOrders().getOpenOrders();
        System.out.println(openOrders.toString());
        assertThat(openOrders.get(0).getInstrument()).isEqualTo(instrument);
        assertThat(openOrders.get(0).getId()).isEqualTo(orderId);

        String newOrderId = exchange.getTradeService().changeOrder(new LimitOrder.Builder(Order.OrderType.BID,instrument)
                .limitPrice(BigDecimal.valueOf(2000))
                .id(orderId)
                .originalAmount(BigDecimal.TEN)
                .userReference(clientId)
                .build());
        openOrders = exchange.getTradeService().getOpenOrders().getOpenOrders();
        System.out.println(openOrders.toString());
        assertThat(openOrders.get(0).getInstrument()).isEqualTo(instrument);
        assertThat(openOrders.get(0).getId()).isEqualTo(newOrderId);
        assertThat(openOrders.get(0).getLimitPrice()).isEqualTo(BigDecimal.valueOf(2000));
        assertThat(openOrders.get(0).getOriginalAmount()).isEqualTo(BigDecimal.TEN);
        assertThat(exchange.getTradeService().cancelOrder(newOrderId)).isTrue();
    }

    @Test
    public void placeStopOrderAndGetOpenOrders() throws IOException {
        String orderId = exchange.getTradeService().placeStopOrder(new StopOrder.Builder(Order.OrderType.ASK,instrument)
                .intention(StopOrder.Intention.STOP_LOSS)
                .stopPrice(BigDecimal.valueOf(1000))
                .flag(KrakenFuturesOrderFlags.REDUCE_ONLY)
                .originalAmount(BigDecimal.ONE)
                .build());
        OpenOrders openOrders = exchange.getTradeService().getOpenOrders();
        System.out.println(openOrders.getHiddenOrders());
        assertThat(openOrders.getHiddenOrders().get(0).getInstrument()).isEqualTo(instrument);
        assertThat(openOrders.getHiddenOrders().get(0).getId()).isEqualTo(orderId);
        assertThat(openOrders.getHiddenOrders().get(0).hasFlag(KrakenFuturesOrderFlags.REDUCE_ONLY)).isTrue();
        assertThat(exchange.getTradeService().cancelOrder(orderId)).isTrue();
    }

    @Test
    public void placeMarketOrderAndGetTradeHistory() throws IOException, InterruptedException {
        String orderId = exchange.getTradeService().placeMarketOrder(new MarketOrder.Builder(Order.OrderType.BID,instrument)
                .originalAmount(BigDecimal.ONE)
                .build());
        TimeUnit.SECONDS.sleep(1);
        TradeHistoryParamInstrument params = (TradeHistoryParamInstrument) exchange.getTradeService().createTradeHistoryParams();
        params.setInstrument(instrument);
        List<UserTrade> userTrades = exchange.getTradeService().getTradeHistory(params).getUserTrades();
        Collections.reverse(userTrades);
        System.out.println(userTrades);
        assertThat(userTrades.get(0).getInstrument()).isEqualTo(instrument);
        assertThat(userTrades.get(0).getOrderId()).isEqualTo(orderId);
    }

    @Test
    public void cancelAllOrdersByInstrument() throws IOException {
        exchange.getTradeService().placeLimitOrder(new LimitOrder.Builder(Order.OrderType.BID,instrument)
                .limitPrice(BigDecimal.valueOf(1000))
                .originalAmount(BigDecimal.ONE)
                .build());
        exchange.getTradeService().placeLimitOrder(new LimitOrder.Builder(Order.OrderType.BID,instrument)
                .limitPrice(BigDecimal.valueOf(1000))
                .originalAmount(BigDecimal.ONE)
                .build());
        List<LimitOrder> openOrders = exchange.getTradeService().getOpenOrders().getOpenOrders();
        System.out.println(openOrders.get(0).toString());
        assertThat(openOrders.get(0).getInstrument()).isEqualTo(instrument);
        Collection<String> orderIds = exchange.getTradeService().cancelAllOrders(new DefaultCancelAllOrdersByInstrument(instrument));
        orderIds.forEach(System.out::println);
        assertThat(orderIds.size()).isEqualTo(2);
    }

    @Test
    public void checkTradeHistory() throws IOException {
        List<UserTrade> userTrades = exchange.getTradeService().getTradeHistory(new DefaultTradeHistoryParamInstrument(instrument)).getUserTrades();
        System.out.println(userTrades);
        assertThat(userTrades.get(0).getInstrument()).isEqualTo(instrument);
    }

    @Test
    public void checkOpenPositions() throws IOException {
        List<OpenPosition> openPositions = exchange.getTradeService().getOpenPositions().getOpenPositions();
        System.out.println(openPositions);
        assertThat(openPositions.get(0).getInstrument()).isEqualTo(instrument);
    }
}
