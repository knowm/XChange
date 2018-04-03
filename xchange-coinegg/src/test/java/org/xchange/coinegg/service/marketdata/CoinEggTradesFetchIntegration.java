package org.xchange.coinegg.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.xchange.coinegg.CoinEggExchange;

public class CoinEggTradesFetchIntegration {

  @Test
  public void tradesFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinEggExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Trades trades = marketDataService.getTrades(CurrencyPair.ETH_BTC);

    // Verify Not Null Values
    assertThat(trades).isNotNull();

    assertThat(trades.getTrades()).isNotNull();
    assertThat(trades.getTrades()).isNotEmpty();

    assertThat(trades.getTrades().get(0).getId()).isNotNull();
    assertThat(trades.getTrades().get(0).getPrice()).isNotNull();
    assertThat(trades.getTrades().get(0).getOriginalAmount()).isNotNull();
    assertThat(trades.getTrades().get(0).getType()).isNotNull();
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isNotNull();
  }
}
