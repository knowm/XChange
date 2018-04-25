package org.knowm.xchange.yobit.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.yobit.YoBitExchange;

public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(YoBitExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(CurrencyPair.build("LTC", "BTC"));
    System.out.println(ticker.toString());
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.build("LTC", "BTC"));
    assertThat(ticker).isNotNull();

    Ticker ticker2 = marketDataService.getTicker(CurrencyPair.build("ETH", "BTC"));
    assertThat(ticker2).isNotNull();
    assertThat(ticker2.getCurrencyPair()).isEqualTo(CurrencyPair.build("ETH", "BTC"));
    assertThat(ticker2.getLast()).isNotEqualTo(ticker.getLast());
  }
}
