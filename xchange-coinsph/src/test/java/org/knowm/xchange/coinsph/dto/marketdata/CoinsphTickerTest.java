package org.knowm.xchange.coinsph.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CoinsphTickerTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalTicker() throws IOException {
    // given
    InputStream is =
        CoinsphTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinsph/dto/marketdata/example-ticker.json");

    // when
    CoinsphTicker ticker = objectMapper.readValue(is, CoinsphTicker.class);

    // then
    assertThat(ticker).isNotNull();
    assertThat(ticker.getSymbol()).isEqualTo("BTCPHP");
    assertThat(ticker.getLastPrice()).isEqualByComparingTo(new BigDecimal("5803758.7"));
    assertThat(ticker.getBidPrice()).isEqualByComparingTo(new BigDecimal("5803095.5"));
    assertThat(ticker.getAskPrice()).isEqualByComparingTo(new BigDecimal("5944316.4"));
    assertThat(ticker.getVolume()).isEqualByComparingTo(new BigDecimal("2.0446395"));
    assertThat(ticker.getQuoteVolume()).isEqualByComparingTo(new BigDecimal("11762126.4"));
    assertThat(ticker.getHighPrice()).isEqualByComparingTo(new BigDecimal("5944316.4"));
    assertThat(ticker.getLowPrice()).isEqualByComparingTo(new BigDecimal("5674429.4"));
    assertThat(ticker.getOpenPrice()).isEqualByComparingTo(new BigDecimal("5769229.2"));
    assertThat(ticker.getPriceChangePercent()).isEqualByComparingTo(new BigDecimal("0.6"));
  }

  @Test
  public void testMarshalTicker() throws IOException {
    // given
    CoinsphTicker ticker =
        new CoinsphTicker(
            "BTCPHP",
            new BigDecimal("34529.5"),
            new BigDecimal("0.6"),
            new BigDecimal("5803758.7"),
            new BigDecimal("5769229.2"),
            new BigDecimal("5803758.7"),
            new BigDecimal("0.0001"),
            new BigDecimal("5803095.5"),
            new BigDecimal("0.0000824"),
            new BigDecimal("5944316.4"),
            new BigDecimal("0.0008186"),
            new BigDecimal("5769229.2"),
            new BigDecimal("5944316.4"),
            new BigDecimal("5674429.4"),
            new BigDecimal("2.0446395"),
            new BigDecimal("11762126.4"),
            1747330920000L,
            1747330920249L,
            12345L,
            12346L,
            100L);

    // when
    String json = objectMapper.writeValueAsString(ticker);

    // then
    assertThat(json).isNotNull();
    CoinsphTicker unmarshalledTicker = objectMapper.readValue(json, CoinsphTicker.class);
    assertThat(unmarshalledTicker).isNotNull();
    assertThat(unmarshalledTicker.getSymbol()).isEqualTo(ticker.getSymbol());
    assertThat(unmarshalledTicker.getLastPrice()).isEqualByComparingTo(ticker.getLastPrice());
    assertThat(unmarshalledTicker.getBidPrice()).isEqualByComparingTo(ticker.getBidPrice());
    assertThat(unmarshalledTicker.getAskPrice()).isEqualByComparingTo(ticker.getAskPrice());
    assertThat(unmarshalledTicker.getVolume()).isEqualByComparingTo(ticker.getVolume());
    assertThat(unmarshalledTicker.getQuoteVolume()).isEqualByComparingTo(ticker.getQuoteVolume());
    assertThat(unmarshalledTicker.getHighPrice()).isEqualByComparingTo(ticker.getHighPrice());
    assertThat(unmarshalledTicker.getLowPrice()).isEqualByComparingTo(ticker.getLowPrice());
    assertThat(unmarshalledTicker.getOpenPrice()).isEqualByComparingTo(ticker.getOpenPrice());
    assertThat(unmarshalledTicker.getPriceChangePercent())
        .isEqualByComparingTo(ticker.getPriceChangePercent());
  }
}
