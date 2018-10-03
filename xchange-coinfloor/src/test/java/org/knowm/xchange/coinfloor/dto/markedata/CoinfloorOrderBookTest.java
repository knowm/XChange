package org.knowm.xchange.coinfloor.dto.markedata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class CoinfloorOrderBookTest {
  @Test
  public void unmarshalTest() throws IOException {
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/coinfloor/dto/marketdata/example-order-book.json");
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorOrderBook orderBook = mapper.readValue(is, CoinfloorOrderBook.class);

    assertThat(orderBook.getBids()).hasSize(68);
    assertThat(orderBook.getAsks()).hasSize(103);

    assertThat(orderBook.getBids().get(1).get(1)).isEqualTo("5.1322");
    assertThat(orderBook.getAsks().get(0).get(0)).isEqualTo("801.00");
  }
}
