package org.knowm.xchange.deribit.v2.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrades;
import org.knowm.xchange.deribit.v2.service.DeribitMarketDataService;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.instrument.Instrument;

public class DeribitTradesFetchIntegration {

  private static Exchange exchange;
  private static DeribitMarketDataService deribitMarketDataService;

  @BeforeClass
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    exchange.applySpecification(((DeribitExchange) exchange).getSandboxExchangeSpecification());
    deribitMarketDataService = (DeribitMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void getDeribitLastTradesTest() throws Exception {
    DeribitTrades trades =
        deribitMarketDataService.getLastTradesByInstrument(
            "BTC-PERPETUAL", null, null, null, null, null);

    assertThat(trades.getTrades()).isNotEmpty();
  }

  @Test
  public void getTradesTest() throws Exception {
    Instrument pair = new FuturesContract(CurrencyPair.BTC_USD, null);
    Trades trades = deribitMarketDataService.getTrades(pair);

    assertThat(trades).isNotNull();
    assertThat(trades.getTrades()).isNotEmpty();
  }
}
