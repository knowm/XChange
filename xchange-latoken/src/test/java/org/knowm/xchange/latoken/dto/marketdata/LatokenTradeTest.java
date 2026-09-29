package org.knowm.xchange.latoken.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

class LatokenTradeTest {
  LatokenTrade trade;

  @BeforeEach
  void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/marketdata/latoken-trades-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    LatokenTrades trades = mapper.readValue(is, LatokenTrades.class);
    assertThat(trades.getTrades().size()).isEqualTo(1);

    trade = trades.getTrades().get(0);
  }

  @Test
  void latokenTrade() {
    assertThat(trade).isNotNull();
  }

  @Test
  void getSide() {
    assertThat(trade.getSide()).isNotNull();
  }

  @Test
  void getPrice() {
    assertThat(trade.getPrice()).isNotNull();
  }

  @Test
  void getAmount() {
    assertThat(trade.getAmount()).isNotNull();
  }

  @Test
  void getTimestamp() {
    assertThat(trade.getTimestamp()).isNotNull();
  }

  @Test
  void testToString() {
    assertThat(trade.toString()).isNotNull();
  }
}
