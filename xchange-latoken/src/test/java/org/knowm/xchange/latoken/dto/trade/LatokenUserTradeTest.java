package org.knowm.xchange.latoken.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

class LatokenUserTradeTest {
  LatokenUserTrade trade;

  @BeforeEach
  void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/trade/latoken-user-trades-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    LatokenUserTrades trades = mapper.readValue(is, LatokenUserTrades.class);

    trade = trades.getTrades().get(0);
  }

  @Test
  void latokenUserTrade() {
    assertThat(trade).isNotNull();
  }

  @Test
  void getId() {
    assertThat(trade.getId()).isNotNull();
  }

  @Test
  void getOrderId() {
    assertThat(trade.getOrderId()).isNotNull();
  }

  @Test
  void getFee() {
    assertThat(trade.getFee()).isNotNull();
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
  void getTime() {
    assertThat(trade.getTime()).isNotNull();
  }

  @Test
  void testToString() {
    assertThat(trade.toString()).isNotNull();
  }
}
