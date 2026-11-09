package org.knowm.xchange.huobi;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.huobi.dto.marketdata.KlineInterval;
import org.knowm.xchange.huobi.service.HuobiMarketDataService;

class HuobiInitIntegration {

  @Test
  void init() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class);

    HuobiMarketDataService marketDataService =
        (HuobiMarketDataService) exchange.getMarketDataService();

    // GET Klines
    Arrays.stream(marketDataService.getKlines(CurrencyPair.BTC_USDT, KlineInterval.m5, 10))
        .forEach(System.out::println);
  }
}
