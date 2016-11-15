package org.knowm.xchange.bitkonan.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitkonan.BitKonanExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

public class TradesFetchIntegration {

  @Test
  public void tradesFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitKonanExchange.class.getName());
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();
    Trades trades = marketDataService.getTrades(new CurrencyPair("BTC", "USD"));
    System.out.println(trades.toString());
    assertThat(trades).isNotNull();
  }
}
