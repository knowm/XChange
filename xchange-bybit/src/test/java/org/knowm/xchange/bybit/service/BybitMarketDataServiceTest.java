package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BybitMarketDataServiceTest extends BaseWiremockTest {

  private MarketDataService marketDataService;

  @Before
  public void setUp() throws Exception {
    Exchange bybitExchange = createExchange();
    marketDataService = bybitExchange.getMarketDataService();
  }

  @Test
  public void testGetTickerWithInverseArg() throws Exception {
    initGetStub("/v5/market/tickers", "/getTickerInverse.json5");

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD, BybitCategory.INVERSE);

    assertThat(ticker.getInstrument().toString()).isEqualTo("BTC/USD");
    assertThat(ticker.getOpen()).isEqualTo(new BigDecimal("16464.50"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("16597.00"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("16596.00"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("16597.50"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("30912.50"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("15700.00"));
    assertThat(ticker.getVwap()).isNull();
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("49337318"));
    assertThat(ticker.getQuoteVolume()).isEqualTo(new BigDecimal("2352.94950046"));
    assertThat(ticker.getTimestamp()).isEqualTo(new Date(1672376496682L));
    assertThat(ticker.getBidSize()).isEqualTo(new BigDecimal("1"));
    assertThat(ticker.getAskSize()).isEqualTo(new BigDecimal("1"));
    assertThat(ticker.getPercentageChange()).isEqualTo(new BigDecimal("0.008047"));
  }

  @Test
  public void testGetTickerWithSpotArg() throws Exception {
    initGetStub("/v5/market/tickers", "/getTickerSpot.json5");

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD, BybitCategory.SPOT);

    assertThat(ticker.getInstrument().toString()).isEqualTo("BTC/USD");
    assertThat(ticker.getOpen()).isEqualTo(new BigDecimal("20393.48"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("20533.13"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("20517.96"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("20527.77"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("21128.12"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("20318.89"));
    assertThat(ticker.getVwap()).isNull(); // If it's supposed to be null
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("11801.27771"));
    assertThat(ticker.getQuoteVolume()).isEqualTo(new BigDecimal("243765620.65899866"));
    assertThat(ticker.getTimestamp()).isEqualTo(new Date(1673859087947L));
    assertThat(ticker.getBidSize()).isEqualTo(new BigDecimal("2"));
    assertThat(ticker.getAskSize()).isEqualTo(new BigDecimal("1.862172"));
    assertThat(ticker.getPercentageChange()).isEqualTo(new BigDecimal("0.0068"));
  }
}
