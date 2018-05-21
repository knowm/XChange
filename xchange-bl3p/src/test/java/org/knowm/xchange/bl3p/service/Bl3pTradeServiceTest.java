package org.knowm.xchange.bl3p.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bl3p.Bl3pExchange;
import org.knowm.xchange.bl3p.service.params.CancelOrderByIdAndCurrencyPairParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;

import java.io.IOException;

public class Bl3pTradeServiceTest {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(Bl3pExchange.class);
    TradeService tradeService = exchange.getTradeService();

    @Test
    public void getOpenOrders() throws IOException {
        OpenOrders openOrders = tradeService.getOpenOrders();

        System.out.println(openOrders);
    }

    @Test
    public void createMarketOrder() throws IOException {
        /**
        MarketOrder order = new MarketOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_EUR)
                .originalAmount(new BigDecimal("0.0001"))
                .build();

        String orderId = tradeService.placeMarketOrder(order);

        System.out.println(orderId);
        */
    }

    @Test
    public void createLimitOrder() throws IOException {
        /*
        LimitOrder order = new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_EUR)
                .originalAmount(new BigDecimal("0.001"))
                .limitPrice(new BigDecimal("5000"))
                .build();

        String orderId = tradeService.placeLimitOrder(order);

        System.out.println(orderId);
        */
    }

    @Test
    public void cancelOrder() throws IOException {
        CancelOrderByIdAndCurrencyPairParams params = new CancelOrderByIdAndCurrencyPairParams(CurrencyPair.BTC_EUR, "30058063");
        boolean result = tradeService.cancelOrder(params);

        System.out.println(result);
    }

}