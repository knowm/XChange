package org.knowm.xchange.test.binance;

import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.utils.StreamUtils;

public class TradeServiceIntegration {

    static Exchange exchange;
    static TradeService tradeService;

    @BeforeClass
    public static void beforeClass() {
        exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName());
        tradeService = exchange.getTradeService();
    }

    @Before
    public void before() {
      Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
    }

    @Test
    public void testMetaData() throws Exception {

        CurrencyPair pair = CurrencyPair.XRP_BTC;
        OpenOrders orders = tradeService.getOpenOrders(new DefaultOpenOrdersParamCurrencyPair(pair));
        LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
        if (order != null) {
          System.out.println(order);
        }
    }
}
