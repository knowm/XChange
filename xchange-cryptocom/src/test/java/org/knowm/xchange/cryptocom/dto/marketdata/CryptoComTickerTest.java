package org.knowm.xchange.cryptocom.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;

public class CryptoComTickerTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalSingleTicker() throws IOException {
    // given
    String resource = "/org/knowm/xchange/cryptocom/dto/marketdata/get-ticker-single.json";

    // when
    CryptoComResponse response =
        CryptoComTestSupport.readResponse(CryptoComTickerTest.class, resource, objectMapper);
    List<CryptoComTicker> tickers =
        CryptoComTestSupport.readDataList(response, objectMapper, CryptoComTicker.class);

    // then
    assertThat(tickers).hasSize(1);
    CryptoComTicker ticker = tickers.get(0);
    assertThat(ticker.getInstrumentName()).isEqualTo("BTC_USDT");
    assertThat(new BigDecimal(ticker.getHigh24h()))
        .isEqualByComparingTo(new BigDecimal("64944.53"));
    assertThat(new BigDecimal(ticker.getLow24h())).isEqualByComparingTo(new BigDecimal("64188.17"));
    assertThat(new BigDecimal(ticker.getLatestTradePrice()))
        .isEqualByComparingTo(new BigDecimal("64685.97"));
    assertThat(new BigDecimal(ticker.getVolume24h()))
        .isEqualByComparingTo(new BigDecimal("418.1111"));
    assertThat(new BigDecimal(ticker.getVolume24hUsd()))
        .isEqualByComparingTo(new BigDecimal("26947224.38"));
    assertThat(new BigDecimal(ticker.getBestBidPrice()))
        .isEqualByComparingTo(new BigDecimal("64688.98"));
    assertThat(new BigDecimal(ticker.getBestAskPrice()))
        .isEqualByComparingTo(new BigDecimal("64688.99"));
    assertThat(ticker.getTimestamp()).isEqualTo(1785085694239L);
  }

  @Test
  public void testUnmarshalMultipleTickersWithNullField() throws IOException {
    // given
    String resource = "/org/knowm/xchange/cryptocom/dto/marketdata/get-tickers.json";

    // when
    CryptoComResponse response =
        CryptoComTestSupport.readResponse(CryptoComTickerTest.class, resource, objectMapper);
    List<CryptoComTicker> tickers =
        CryptoComTestSupport.readDataList(response, objectMapper, CryptoComTicker.class);

    // then
    assertThat(tickers).hasSize(3);
    assertThat(tickers.get(0).getInstrumentName()).isEqualTo("ACH_USD");
    assertThat(tickers.get(1).getInstrumentName()).isEqualTo("HEMI_USD");
    // "oi" is null for this instrument in the live payload
    assertThat(tickers.get(1).getOpenInterest()).isNull();
    assertThat(tickers.get(2).getInstrumentName()).isEqualTo("WALUSD-PERP");
    assertThat(tickers.get(2).getOpenInterest()).isEqualTo("133580");
  }
}
