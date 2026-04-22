package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.GateioExchangeWiremock;
import org.knowm.xchange.gateio.dto.GateioExchangeType;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;

public class GateioFuturesMarketDataServiceTest extends GateioExchangeWiremock {

  GateioMarketDataService gateioMarketDataService;
  FuturesContract btcUsdt = new FuturesContract("BTC/USDT/PERP");

  @BeforeEach
  void setUp() throws IOException {
    exchange.getExchangeSpecification().setExchangeSpecificParametersItem(GateioExchange.EXCHANGE_TYPE, GateioExchangeType.FUTURES);
    exchange.remoteInit();
    gateioMarketDataService = (GateioMarketDataService) exchange.getMarketDataService();
  }

  @Test
  void getTicker_valid_futures() throws IOException {
    Ticker actual = gateioMarketDataService.getTicker(btcUsdt);

    assertThat(actual.getInstrument()).isEqualTo(btcUsdt);
    assertThat(actual.getLast()).isEqualByComparingTo(new BigDecimal("6432"));
    assertThat(actual.getAsk()).isEqualByComparingTo(new BigDecimal("34217.9"));
    assertThat(actual.getAskSize()).isEqualByComparingTo(new BigDecimal("1000"));
    assertThat(actual.getBid()).isEqualByComparingTo(new BigDecimal("34089.7"));
    assertThat(actual.getBidSize()).isEqualByComparingTo(new BigDecimal("100"));
    assertThat(actual.getHigh()).isEqualByComparingTo(new BigDecimal("6790"));
    assertThat(actual.getLow()).isEqualByComparingTo(new BigDecimal("6278"));
    assertThat(actual.getVolume()).isEqualByComparingTo(new BigDecimal("184040233284").multiply(new BigDecimal("0.0001")));
    assertThat(actual.getQuoteVolume()).isEqualByComparingTo(new BigDecimal("184040233284"));
    assertThat(actual.getPercentageChange()).isEqualByComparingTo(new BigDecimal("4.43"));
  }

  @Test
  void getCandleStickData_valid_futures() throws IOException {
    CandleStickData actual = gateioMarketDataService.getCandleStickData(btcUsdt, new DefaultCandleStickParam(null, null, 3600));

    assertThat(actual.getInstrument()).isEqualTo(btcUsdt);
    assertThat(actual.getCandleSticks()).hasSize(1);
    assertThat(actual.getCandleSticks().get(0).getOpen()).isEqualTo(new BigDecimal("100"));
    assertThat(actual.getCandleSticks().get(0).getHigh()).isEqualTo(new BigDecimal("110"));
    assertThat(actual.getCandleSticks().get(0).getLow()).isEqualTo(new BigDecimal("90"));
    assertThat(actual.getCandleSticks().get(0).getClose()).isEqualTo(new BigDecimal("105"));
    assertThat(actual.getCandleSticks().get(0).getVolume()).isEqualByComparingTo(new BigDecimal("0.001"));
    assertThat(actual.getCandleSticks().get(0).getTimestamp()).isEqualTo(Instant.ofEpochSecond(1600000000L));
  }
}
