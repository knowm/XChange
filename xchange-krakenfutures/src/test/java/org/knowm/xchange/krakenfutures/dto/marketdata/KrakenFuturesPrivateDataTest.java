package org.knowm.xchange.krakenfutures.dto.marketdata;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.krakenfutures.KrakenFuturesExchange;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.DefaultCancelAllOrdersByInstrument;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamInstrument;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    //TODO marketOrder test, stopOrder test
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
