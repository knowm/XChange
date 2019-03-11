package org.knowm.xchange.coinfloor.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinfloor.CoinfloorExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoinfloorOrderBookIntegration {

  @Test
  public void fetchOrderBookTest() throws IOException {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinfloorExchange.class.getName());
    MarketDataService service = exchange.getMarketDataService();

    OrderBook orderBook = service.getOrderBook(CurrencyPair.BTC_GBP);
    assertThat(orderBook.getBids()).isNotEmpty();
    assertThat(orderBook.getAsks()).isNotEmpty();

    LimitOrder order = orderBook.getBids().get(0);
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_GBP);
    assertThat(order.getOriginalAmount()).isGreaterThan(BigDecimal.ZERO);
    assertThat(order.getLimitPrice()).isGreaterThan(BigDecimal.ZERO);
  }
}
