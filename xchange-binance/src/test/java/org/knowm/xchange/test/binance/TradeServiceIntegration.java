package org.knowm.xchange.test.binance;

import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeServiceIntegration {

  static Logger LOG = LoggerFactory.getLogger(TradeServiceIntegration.class);

  static Exchange exchange;
  static BinanceTradeService tradeService;
  static BinanceMarketDataService marketService;
  static BinanceAccountService accountService;

  @BeforeClass
  public static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName());
    marketService = (BinanceMarketDataService) exchange.getMarketDataService();
    accountService = (BinanceAccountService) exchange.getAccountService();
    tradeService = (BinanceTradeService) exchange.getTradeService();
  }

  @Before
  public void before() {
    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }

  @Test
  public void openOrders() throws Exception {

    CurrencyPair pair = CurrencyPair.XRP_BTC;

    OpenOrders orders = tradeService.getOpenOrders(pair);
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println(order);
    }
  }
}
