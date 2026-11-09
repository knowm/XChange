package org.knowm.xchange.latoken.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

class LatokenTickerTest {
  LatokenTicker ticker;

  @BeforeEach
  void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/marketdata/latoken-ticker-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    ticker = mapper.readValue(is, LatokenTicker.class);
  }

  @Test
  void latokenTicker() {
    assertThat(ticker).isNotNull();
  }

  @Test
  void getPairId() {
    assertThat(ticker.getPairId()).isNotNull();
  }

  @Test
  void getSymbol() {
    assertThat(ticker.getSymbol()).isNotNull();
  }

  @Test
  void getVolume() {
    assertThat(ticker.getVolume()).isNotNull();
  }

  @Test
  void getOpen() {
    assertThat(ticker.getOpen()).isNotNull();
  }

  @Test
  void getLow() {
    assertThat(ticker.getLow()).isNotNull();
  }

  @Test
  void getHigh() {
    assertThat(ticker.getHigh()).isNotNull();
  }

  @Test
  void getClose() {
    assertThat(ticker.getClose()).isNotNull();
  }

  @Test
  void getPriceChange() {
    assertThat(ticker.getPriceChange()).isNotNull();
  }

  @Test
  void testToString() {
    assertThat(ticker.toString()).isNotNull();
  }
}
