package org.knowm.xchange.latoken.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

class LatokenOrderbookTest {
  LatokenOrderbook orderbook;

  @BeforeEach
  void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/marketdata/latoken-orderbook-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    orderbook = mapper.readValue(is, LatokenOrderbook.class);
  }

  @Test
  void latokenOrderbook() {
    assertThat(orderbook).isNotNull();
  }

  @Test
  void getPairId() {
    assertThat(orderbook.getPairId()).isNotNull();
  }

  @Test
  void getSymbol() {
    assertThat(orderbook.getSymbol()).isNotNull();
  }

  @Test
  void getSpread() {
    assertThat(orderbook.getSpread()).isNotNull();
  }

  @Test
  void getAsks() {
    assertThat(orderbook.getAsks()).isNotNull();
    assertThat(orderbook.getAsks().size()).isEqualTo(1);

    PriceLevel level = orderbook.getAsks().get(0);
    assertThat(level.getPrice()).isNotNull();
    assertThat(level.getAmount()).isNotNull();
  }

  @Test
  void getBids() {
    assertThat(orderbook.getBids()).isNotNull();
    assertThat(orderbook.getBids().size()).isEqualTo(1);

    PriceLevel level = orderbook.getAsks().get(0);
    assertThat(level.getPrice()).isNotNull();
    assertThat(level.getAmount()).isNotNull();
  }

  @Test
  void testToString() {
    assertThat(orderbook.toString()).isNotNull();
  }
}
