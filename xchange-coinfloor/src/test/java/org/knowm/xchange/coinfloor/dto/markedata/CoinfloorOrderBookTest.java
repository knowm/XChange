package org.knowm.xchange.coinfloor.dto.markedata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinfloorOrderBookTest {
  @Test
  public void unmarshalTest() throws IOException {
    InputStream is = getClass().getResourceAsStream("/marketdata/example-order-book.json");
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorOrderBook orderBook = mapper.readValue(is, CoinfloorOrderBook.class);

    assertThat(orderBook.getBids()).hasSize(68);
    assertThat(orderBook.getAsks()).hasSize(103);

    assertThat(orderBook.getBids().get(1).get(1)).isEqualTo("5.1322");
    assertThat(orderBook.getAsks().get(0).get(0)).isEqualTo("801.00");
  }
}
