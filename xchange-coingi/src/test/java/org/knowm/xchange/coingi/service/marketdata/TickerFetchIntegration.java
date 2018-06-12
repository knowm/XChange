package org.knowm.xchange.coingi.service.marketdata;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coingi.CoingiExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoingiExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();

    CurrencyPair pair = CurrencyPair.BTC_EUR;
    Ticker ticker0 = marketDataService.getTicker(pair);
    assertThat(ticker0).isNotNull();

    List<CurrencyPair> pairs = exchange.getExchangeSymbols();
    assert (pairs.contains(pair));
  }
}
