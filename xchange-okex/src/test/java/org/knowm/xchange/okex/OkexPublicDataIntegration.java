package org.knowm.xchange.okex;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.FundingRate;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.dto.OkexInstType;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.marketdata.OkexCandleStick;
import org.knowm.xchange.okex.dto.marketdata.OkxFundingRateHistory;
import org.knowm.xchange.okex.service.OkexMarketDataService;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;

class OkexPublicDataIntegration {

  Exchange exchange;
  private final Instrument currencyPair = new CurrencyPair("BTC/USDT");
  private final Instrument instrument = new FuturesContract("BTC/USDT/SWAP");

  @BeforeEach
  void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(OkexExchange.class);
  }

  @Test
  void checkInstrumentMetaData() {
    exchange
        .getExchangeMetaData()
        .getInstruments()
        .forEach(
            (instrument1, instrumentMetaData) -> {
              System.out.println(instrument1 + "||" + instrumentMetaData);
              assertThat(instrumentMetaData.getMinimumAmount()).isGreaterThan(BigDecimal.ZERO);
              assertThat(instrumentMetaData.getPriceScale()).isGreaterThanOrEqualTo(0);
              assertThat(instrumentMetaData.getVolumeScale()).isNotNull();
              if (instrument1 instanceof FuturesContract) {
                assertThat(instrument1.getCounter()).isEqualTo(Currency.USDT);
              }
            });
    // full BTC/USDT/SWAP check
    InstrumentMetaData instrumentMetaData =
        exchange.getExchangeMetaData().getInstruments().get(instrument);
    org.assertj.core.api.Assertions.assertThat(
            instrumentMetaData.getContractValue().compareTo(new BigDecimal("0.01")))
        .isEqualTo(0);
    org.assertj.core.api.Assertions.assertThat(
            instrumentMetaData.getMinimumAmount().compareTo(new BigDecimal("0.0001")))
        .isEqualTo(0);
    assertThat(instrumentMetaData.getVolumeScale()).isEqualTo(4);
    org.assertj.core.api.Assertions.assertThat(
            instrumentMetaData.getAmountStepSize().compareTo(new BigDecimal("0.0001")))
        .isEqualTo(0);
    assertThat(instrumentMetaData.getPriceScale()).isEqualTo(1);
    org.assertj.core.api.Assertions.assertThat(
            instrumentMetaData.getPriceStepSize().compareTo(new BigDecimal("0.1")))
        .isEqualTo(0);
  }

  @Test
  void checkOrderBook() throws Exception {
    LimitOrder spotOrder =
        exchange.getMarketDataService().getOrderBook(currencyPair).getBids().get(0);
    LimitOrder swapOrder =
        exchange.getMarketDataService().getOrderBook(instrument).getBids().get(0);

    assertThat(spotOrder.getInstrument()).isEqualTo(currencyPair);
    assertThat(swapOrder.getInstrument()).isEqualTo(instrument);
  }

  @Test
  void checkTicker() throws Exception {
    Ticker spotTicker = exchange.getMarketDataService().getTicker(currencyPair);
    Ticker swapTicker = exchange.getMarketDataService().getTicker(instrument);

    assertThat(spotTicker.getInstrument().getBase()).isEqualTo(currencyPair.getBase());
    assertThat(spotTicker.getInstrument().getCounter()).isEqualTo(Currency.USDT);
    assertThat(swapTicker.getInstrument()).isEqualTo(instrument);
  }

  @Test
  void checkTickers() throws Exception {
    List<Ticker> spotTickers = exchange.getMarketDataService().getTickers(OkexInstType.SPOT);
    List<Ticker> swapTickers = exchange.getMarketDataService().getTickers(OkexInstType.SWAP);

    org.assertj.core.api.Assertions.assertThat(
            spotTickers.stream()
                .anyMatch(f -> f.getInstrument().equals(new CurrencyPair("BTC/USDT"))))
        .isTrue();
    org.assertj.core.api.Assertions.assertThat(
            swapTickers.stream()
                .anyMatch(f -> f.getInstrument().equals(new FuturesContract("BTC/USDT/SWAP"))))
        .isTrue();
  }

  @Test
  void checkTrades() throws Exception {
    Trades spotTrades = exchange.getMarketDataService().getTrades(currencyPair);
    Trades swapTrades = exchange.getMarketDataService().getTrades(instrument);

    assertThat(spotTrades.getTrades().get(0).getInstrument()).isEqualTo(currencyPair);
    assertThat(swapTrades.getTrades().get(0).getInstrument()).isEqualTo(instrument);
    assertThat(swapTrades.getTrades().get(0).getTimestamp())
        .isBeforeOrEqualTo(swapTrades.getTrades().get(5).getTimestamp());
  }

  @Test
  @Disabled
  void candleHist() throws Exception {
    OkexResponse<List<OkexCandleStick>> barHistDtos =
        ((OkexMarketDataService) exchange.getMarketDataService())
            .getHistoryCandle("BTC-USDT", null, null, null, null);
    org.assertj.core.api.Assertions.assertThat(
            Objects.nonNull(barHistDtos) && !barHistDtos.getData().isEmpty())
        .isTrue();
    DefaultCandleStickParam params =
        new DefaultCandleStickParam(
            new Date(System.currentTimeMillis() - 10 * 60 * 1000),
            new Date(System.currentTimeMillis()),
            60);
    CandleStickData candleStickData =
        exchange
            .getMarketDataService()
            .getCandleStickData(new FuturesContract("BTC/USDT/SWAP"), params);
    org.assertj.core.api.Assertions.assertThat(Objects.nonNull(candleStickData)).isTrue();
    org.assertj.core.api.Assertions.assertThat(candleStickData.getCandleSticks().isEmpty())
        .isFalse();
  }

  @Test
  @Disabled
  void candle() throws Exception {
    OkexResponse<List<OkexCandleStick>> barHistDtos =
        ((OkexMarketDataService) exchange.getMarketDataService())
            .getCandle("BTC-USDT", null, null, null, null);
    org.assertj.core.api.Assertions.assertThat(
            Objects.nonNull(barHistDtos) && !barHistDtos.getData().isEmpty())
        .isTrue();
  }

  @Test
  void checkFundingRate() throws Exception {
    FundingRate fundingRate = exchange.getMarketDataService().getFundingRate(instrument);
    System.out.println(fundingRate);
    assertThat(fundingRate.getFundingRateDate()).isNotNull();
  }

  @Test
  void instrumentOkexConvertions() {
    assertThat(OkexAdapters.adaptOkexInstrumentId("BTC-USDT-SWAP"))
        .isEqualTo(new FuturesContract("BTC/USDT/SWAP"));
    assertThat(OkexAdapters.adaptInstrument(new FuturesContract("BTC/USDT/SWAP")))
        .isEqualTo("BTC-USDT-SWAP");
    assertThat(OkexAdapters.adaptOkexInstrumentId("BTC-USDT"))
        .isEqualTo(new CurrencyPair("BTC/USDT"));
    assertThat(OkexAdapters.adaptInstrument(new CurrencyPair("BTC/USDT"))).isEqualTo("BTC-USDT");
    assertThat(OkexAdapters.adaptInstrument(new CurrencyPair("BTC/USDC"))).isEqualTo("BTC-USD");
  }

  @Test
  void fundingRateHistory() {
    try {
      List<OkxFundingRateHistory> fundingRateHistory =
          ((OkexMarketDataService) exchange.getMarketDataService())
              .getFundingRateHistory(
                  instrument,
                  System.currentTimeMillis() - 24 * 60 * 60 * 1000,
                  System.currentTimeMillis(),
                  null);
      System.out.println(fundingRateHistory);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
