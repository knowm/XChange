package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.marketdata.BybitFundingRateHistory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

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

    Ticker ticker = marketDataService.getTicker(new FuturesContract(CurrencyPair.BTC_USD, "PERP"));

    assertThat(ticker.getInstrument().toString()).isEqualTo("BTC/USD/PERP");
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

    Ticker ticker = marketDataService.getTicker((Instrument) CurrencyPair.BTC_USD);

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

  @Test
  public void testGetOrderBook() throws Exception {
    initGetStub("/v5/market/orderbook", "/getOrderbookSpot.json5");

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

    assertThat(orderBook.getBids()).hasSize(2);
    assertThat(orderBook.getAsks()).hasSize(2);
    assertThat(orderBook.getBids().get(0).getLimitPrice()).isEqualTo(new BigDecimal("65485.47"));
    assertThat(orderBook.getBids().get(0).getInstrument()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(orderBook.getAsks().get(0).getLimitPrice()).isEqualTo(new BigDecimal("65557.7"));
  }

  @Test
  public void testGetFundingRateHistory() throws Exception {
    initGetStub("/v5/market/funding/history", "/getFundingRateHistory.json5");

    List<BybitFundingRateHistory> fundingRateHistory = ((BybitMarketDataService) marketDataService).getFundingRateHistory(new FuturesContract("ETH/USDT/PERP"),
        null, null, null);

    assertThat(fundingRateHistory.get(0).getInstrument().toString()).isEqualTo("ETH/USDT/PERP");
    assertThat(fundingRateHistory.get(0).getFundingRate()).isEqualTo(new BigDecimal("0.0001"));
    assertThat(fundingRateHistory.get(0).getFundingRateTimestamp()).isEqualTo(Instant.ofEpochMilli(1672051897447L));

  }

  @Test
  public void testGetCandleStickData() throws Exception {
    initGetStub("/v5/market/kline", "/getKlines.json5");

    CandleStickData candleStickData =
        marketDataService
            .getCandleStickData(
                CurrencyPair.BTC_USDT, new DefaultCandleStickParam(new Date(1670601600000L), new Date(1670608800000L), 60));

    assertThat(candleStickData.getInstrument().toString()).isEqualTo("BTC/USDT");
    assertThat(candleStickData.getCandleSticks()).hasSize(3);
    assertThat(candleStickData.getCandleSticks().get(0).getTimestamp())
        .isEqualTo(Instant.ofEpochMilli(1670608800000L));
    assertThat(candleStickData.getCandleSticks().get(0).getOpen())
        .isEqualTo(new BigDecimal("17071"));
    assertThat(candleStickData.getCandleSticks().get(0).getHigh())
        .isEqualTo(new BigDecimal("17073"));
    assertThat(candleStickData.getCandleSticks().get(0).getLow())
        .isEqualTo(new BigDecimal("17027"));
    assertThat(candleStickData.getCandleSticks().get(0).getClose())
        .isEqualTo(new BigDecimal("17055.5"));
    assertThat(candleStickData.getCandleSticks().get(0).getVolume())
        .isEqualTo(new BigDecimal("268611"));
    assertThat(candleStickData.getCandleSticks().get(0).getQuotaVolume())
        .isEqualTo(new BigDecimal("15.74462667"));
  }
}
