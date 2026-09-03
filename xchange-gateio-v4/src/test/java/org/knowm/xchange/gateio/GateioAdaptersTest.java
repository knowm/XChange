package org.knowm.xchange.gateio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.gateio.dto.marketdata.GateioFuturesCandlestick;
import org.knowm.xchange.gateio.dto.marketdata.GateioInstrumentDetails;
import org.knowm.xchange.gateio.dto.marketdata.GateioSpotCandlestick;
import org.knowm.xchange.utils.ObjectMapperHelper;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class GateioAdaptersTest {

  @Test
  void testToCandleStickDataSpotFutures() throws IOException {
    try (InputStream is = getClass().getResourceAsStream("/__files/api_v4_futures_candlesticks.json")) {
      Assertions.assertNotNull(is);
      GateioFuturesCandlestick[] candlesticks = ObjectMapperHelper.readValue(new String(is.readAllBytes()), GateioFuturesCandlestick[].class);

      FuturesContract instrument = new FuturesContract("BTC/USDT/PERP");
      CandleStickData candleStickData = GateioAdapters.toCandleStickDataFutures(
          Arrays.asList(candlesticks), instrument, new BigDecimal("0.0001"));

      assertThat(candleStickData.getInstrument()).isEqualTo(instrument);
      assertThat(candleStickData.getCandleSticks()).hasSize(1);
      assertThat(candleStickData.getCandleSticks().get(0).getOpen()).isEqualTo(new BigDecimal("100"));
      assertThat(candleStickData.getCandleSticks().get(0).getHigh()).isEqualTo(new BigDecimal("110"));
      assertThat(candleStickData.getCandleSticks().get(0).getLow()).isEqualTo(new BigDecimal("90"));
      assertThat(candleStickData.getCandleSticks().get(0).getClose()).isEqualTo(new BigDecimal("105"));
      assertThat(candleStickData.getCandleSticks().get(0).getVolume()).isEqualTo(new BigDecimal("0.001"));
      assertThat(candleStickData.getCandleSticks().get(0).getQuotaVolume()).isEqualTo(new BigDecimal("1000"));
    }
  }

  @Test
  void testToCandleStickDataSpotSpot() throws IOException {
    try (InputStream is = getClass().getResourceAsStream("/__files/api_v4_spot_candlesticks.json")) {
      Assertions.assertNotNull(is);
      GateioSpotCandlestick[] candlesticks = ObjectMapperHelper.readValue(new String(is.readAllBytes()), GateioSpotCandlestick[].class);

      CurrencyPair instrument = CurrencyPair.BTC_USDT;
      CandleStickData candleStickData = GateioAdapters.toCandleStickDataSpot(
          Arrays.asList(candlesticks), instrument);

      assertThat(candleStickData.getInstrument()).isEqualTo(instrument);
      assertThat(candleStickData.getCandleSticks()).hasSize(1);
      assertThat(candleStickData.getCandleSticks().get(0).getOpen()).isEqualTo(new BigDecimal("100"));
      assertThat(candleStickData.getCandleSticks().get(0).getHigh()).isEqualTo(new BigDecimal("110"));
      assertThat(candleStickData.getCandleSticks().get(0).getLow()).isEqualTo(new BigDecimal("90"));
      assertThat(candleStickData.getCandleSticks().get(0).getClose()).isEqualTo(new BigDecimal("105"));
      assertThat(candleStickData.getCandleSticks().get(0).getVolume()).isEqualTo(new BigDecimal("1000"));
      assertThat(candleStickData.getCandleSticks().get(0).getQuotaVolume()).isEqualTo(new BigDecimal("10"));
    }
  }

  @Test
  void testInstrumentToInstrumentMetaData() throws IOException {
    try (InputStream is = getClass().getResourceAsStream("/__files/api_v4_instrument_details.json")) {
      Assertions.assertNotNull(is);
      GateioInstrumentDetails[] detailsArray = ObjectMapperHelper.readValue(new String(is.readAllBytes()), GateioInstrumentDetails[].class);
      GateioInstrumentDetails details = detailsArray[0];

      InstrumentMetaData metaData = GateioAdapters.instrumentToInstrumentMetaData(details);

      assertThat(metaData.getTradingFee()).isEqualByComparingTo(new BigDecimal("0.00075"));
      assertThat(metaData.getMinimumAmount()).isEqualByComparingTo(new BigDecimal("0.0001"));
      assertThat(metaData.getMaximumAmount()).isEqualByComparingTo(new BigDecimal("100"));
      assertThat(metaData.getPriceStepSize()).isEqualByComparingTo(new BigDecimal("0.1"));
      assertThat(metaData.getAmountStepSize()).isEqualByComparingTo(new BigDecimal("0.0001"));
      assertThat(metaData.getVolumeScale()).isEqualTo(4);
      assertThat(metaData.getPriceScale()).isEqualTo(1);
      assertThat(metaData.getContractValue()).isEqualByComparingTo(new BigDecimal("0.0001"));
    }
  }
}
