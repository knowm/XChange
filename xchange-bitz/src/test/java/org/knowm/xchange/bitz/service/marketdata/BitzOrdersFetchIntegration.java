package org.knowm.xchange.bitz.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitz.BitZExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitzOrdersFetchIntegration {

  @Test
  public void ordersFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitZExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();
    OrderBook orders = marketDataService.getOrderBook(new CurrencyPair("LTC", "BTC"));

    // Verify Not Null Values
    assertThat(orders).isNotNull();

    // TODO: Logical Verification Of Values
  }
}
