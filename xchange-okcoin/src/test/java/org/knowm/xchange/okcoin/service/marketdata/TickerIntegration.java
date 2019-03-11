package org.knowm.xchange.okcoin.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.okcoin.OkCoinExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author timmolter */
public class TickerIntegration {

  @Test
  public void tickerFetchChinaTest() throws Exception {
    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);
    exSpec.setExchangeSpecificParametersItem("Use_Intl", false);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "CNY"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void tickerFetchIntlTest() throws Exception {
    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);
    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
