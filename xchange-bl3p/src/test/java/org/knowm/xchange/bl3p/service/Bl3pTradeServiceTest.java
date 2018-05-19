package org.knowm.xchange.bl3p.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bl3p.Bl3pExchange;
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

}