package org.knowm.xchange.bybit.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.BybitOrderRequest;

import java.io.IOException;

public class BybitTradeServiceRawTest extends BaseWiremockTest{

    @Test
    public void testGetBybitOrder() throws IOException {
        Exchange bybitExchange = createExchange();
        BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);
        BybitResult<BybitOrderDetails> order = bybitAccountServiceRaw.getBybitOrder("");
        System.out.println(order);
    }


    @Test
    public void testPlaceBybitOrder() throws IOException {
        Exchange bybitExchange = createExchange();
        BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);
        BybitResult<BybitOrderRequest> order = bybitAccountServiceRaw.placeOrder(
                "",
                300,
                "SELL",
                "MARKET"
        );
        System.out.println(order);
    }

}
