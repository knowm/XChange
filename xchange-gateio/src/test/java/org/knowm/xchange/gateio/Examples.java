package org.knowm.xchange.gateio;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.dto.marketdata.GateioKlineInterval;
import org.knowm.xchange.gateio.service.GateioMarketDataService;

public class Examples {

  public static void main(String... args) throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GateioExchange.class);

    GateioMarketDataService marketDataService =
        (GateioMarketDataService) exchange.getMarketDataService();

    // GET Klines
    marketDataService
        .getKlines(CurrencyPair.BTC_USDT, GateioKlineInterval.m5, 10)
        .forEach(System.out::println);
  }
}
