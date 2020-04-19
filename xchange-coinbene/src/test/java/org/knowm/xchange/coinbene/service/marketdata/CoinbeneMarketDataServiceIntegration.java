package org.knowm.xchange.coinbene.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USDT;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbene.CoinbeneExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoinbeneMarketDataServiceIntegration {

  private static final Exchange COINBENE =
      ExchangeFactory.INSTANCE.createExchange(CoinbeneExchange.class.getName());

  @Test
  public void testGetTicker() throws Exception {

    MarketDataService marketDataService = COINBENE.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(BTC_USDT);
    assertThat(ticker.getAsk()).isGreaterThan(ticker.getBid());
    assertThat(ticker.getHigh()).isGreaterThan(ticker.getLow());
    assertThat(ticker.getCurrencyPair()).isEqualTo(BTC_USDT);
  }

  @Test
  public void testGetOrderBook() throws Exception {

    OrderBook orderBookDefault = COINBENE.getMarketDataService().getOrderBook(BTC_USDT);
    assertThat(orderBookDefault).isNotNull();
    assertThat(orderBookDefault.getAsks().size()).isGreaterThan(0);
    assertThat(orderBookDefault.getBids().size()).isGreaterThan(0);

    OrderBook orderBookShort = COINBENE.getMarketDataService().getOrderBook(BTC_USDT, 1);
    assertThat(orderBookShort).isNotNull();
    assertThat(orderBookShort.getAsks().size()).isEqualTo(1);
    assertThat(orderBookShort.getBids().size()).isEqualTo(1);

    OrderBook orderBookLong = COINBENE.getMarketDataService().getOrderBook(BTC_USDT, 10);
    assertThat(orderBookLong).isNotNull();
    assertThat(orderBookLong.getAsks().size()).isEqualTo(10);
    assertThat(orderBookLong.getBids().size()).isEqualTo(10);
  }

  @Test
  public void testGetTrades() throws Exception {

    Trades tradesDefault = COINBENE.getMarketDataService().getTrades(BTC_USDT);
    assertThat(tradesDefault.getTrades().size()).isEqualTo(300);
    tradesDefault.getTrades().forEach(t -> assertThat(t.getCurrencyPair()).isEqualTo(BTC_USDT));

    Trades tradesLimit20 = COINBENE.getMarketDataService().getTrades(BTC_USDT, 20);
    assertThat(tradesLimit20.getTrades().size()).isEqualTo(20);
    tradesLimit20.getTrades().forEach(t -> assertThat(t.getCurrencyPair()).isEqualTo(BTC_USDT));
  }

  @Test
  public void testGetSymbol() {

    List<CurrencyPair> symbols = COINBENE.getExchangeSymbols();
    List<Currency> counters =
        symbols.stream().map(s -> s.counter).distinct().collect(Collectors.toList());
    for (Currency c : counters) {
      System.out.println(c);
    }
    assertThat(symbols).isNotNull();
    assertThat(symbols.contains(CurrencyPair.ETH_BTC)).isEqualTo(true);
    assertThat(symbols.contains(CurrencyPair.DASH_USDT)).isEqualTo(true);
  }

  @Test
  public void testGetTickers() throws Exception {

    MarketDataService marketDataService = COINBENE.getMarketDataService();
    List<Ticker> tickers = marketDataService.getTickers(null);

    assertThat(tickers.stream().anyMatch(ticker -> ticker.getCurrencyPair().equals(BTC_USDT)))
        .isEqualTo(true);
    // BUG: org.knowm.xchange.coinbene.dto.CoinbeneAdapters.adaptSymbol is wrong (doesn't consider 4
    // chars coins)
    assertThat(
            tickers.stream()
                .anyMatch(ticker -> ticker.getCurrencyPair().equals(CurrencyPair.DASH_USDT)))
        .isEqualTo(true);
  }
}
