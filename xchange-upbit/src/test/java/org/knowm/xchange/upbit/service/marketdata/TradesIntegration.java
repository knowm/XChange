package org.knowm.xchange.upbit.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.upbit.UpbitExchange;

public class TradesIntegration {
  @Test
  public void TradesTest() throws Exception {
    ExchangeSpecification exSpec = new ExchangeSpecification(UpbitExchange.class);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    MarketDataService marketDataService = exchange.getMarketDataService();
    Trades trades = marketDataService.getTrades(CurrencyPair.ETH_BTC);
    assertThat(trades).isNotNull();
  }
}
