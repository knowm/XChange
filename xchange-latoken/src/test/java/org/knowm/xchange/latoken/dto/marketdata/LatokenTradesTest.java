package org.knowm.xchange.latoken.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

class LatokenTradesTest {
  LatokenTrades trades;

  @BeforeEach
  void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/marketdata/latoken-trades-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    trades = mapper.readValue(is, LatokenTrades.class);
  }

  @Test
  void latokenTrades() {
    assertThat(trades).isNotNull();
  }

  @Test
  void getPairId() {
    assertThat(trades.getPairId()).isNotNull();
  }

  @Test
  void getSymbol() {
    assertThat(trades.getSymbol()).isNotNull();
  }

  @Test
  void getTradeCount() {
    assertThat(trades.getTradeCount()).isNotNull();
  }

  @Test
  void getTrades() {
    assertThat(trades.getTrades()).isNotNull();
    assertThat(trades.getTrades().size()).isEqualTo(1);
  }

  @Test
  void testToString() {
    assertThat(trades.toString()).isNotNull();
  }
}
