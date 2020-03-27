package org.knowm.xchange.huobi;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HuobiPublicApiIntegration {

  private Exchange exchange;

  @Before
  public void setup() {
    exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName());
  }

  @Test
  public void getTickerTest() throws Exception {
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USDT);

    assertThat(ticker).isNotNull();
    assertThat(ticker.getBid()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getAsk()).isGreaterThan(BigDecimal.ZERO);
  }

  @Test
  public void getAllTickerTest() throws Exception {
    MarketDataService marketDataService = exchange.getMarketDataService();
    List<Ticker> tickers = marketDataService.getTickers(null);

    assertThat(tickers).isNotNull();

    assertThat(tickers.get(0).getBid()).isGreaterThan(BigDecimal.ZERO);
    assertThat(tickers.get(0).getAsk()).isGreaterThan(BigDecimal.ZERO);
    assertThat(tickers.get(0).getBidSize()).isGreaterThan(BigDecimal.ZERO);
    assertThat(tickers.get(0).getAskSize()).isGreaterThan(BigDecimal.ZERO);
  }

  @Test
  public void getExchangeSymbolsTest() {
    List<CurrencyPair> exchangeSymbols = exchange.getExchangeSymbols();

    assertThat(exchangeSymbols).isNotNull();
    assertThat(exchangeSymbols).size().isGreaterThan(0);
  }
}
