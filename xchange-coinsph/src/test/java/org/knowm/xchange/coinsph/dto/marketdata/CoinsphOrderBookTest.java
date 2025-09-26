package org.knowm.xchange.coinsph.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CoinsphOrderBookTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalOrderBook() throws IOException {
    // given
    InputStream is =
        CoinsphOrderBookTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinsph/dto/marketdata/example-orderbook.json");

    // when
    CoinsphOrderBook orderBook = objectMapper.readValue(is, CoinsphOrderBook.class);

    // then
    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getLastUpdateId()).isEqualTo(1234567890L);

    List<CoinsphOrderBookEntry> bids = orderBook.getBids();
    assertThat(bids).isNotNull().hasSize(2);
    assertThat(bids.get(0).getPrice()).isEqualByComparingTo(new BigDecimal("5803095.5"));
    assertThat(bids.get(0).getQuantity()).isEqualByComparingTo(new BigDecimal("0.0000824"));
    assertThat(bids.get(1).getPrice()).isEqualByComparingTo(new BigDecimal("5803000.0"));
    assertThat(bids.get(1).getQuantity()).isEqualByComparingTo(new BigDecimal("0.0001000"));

    List<CoinsphOrderBookEntry> asks = orderBook.getAsks();
    assertThat(asks).isNotNull().hasSize(2);
    assertThat(asks.get(0).getPrice()).isEqualByComparingTo(new BigDecimal("5944316.4"));
    assertThat(asks.get(0).getQuantity()).isEqualByComparingTo(new BigDecimal("0.0008186"));
    assertThat(asks.get(1).getPrice()).isEqualByComparingTo(new BigDecimal("5944400.0"));
    assertThat(asks.get(1).getQuantity()).isEqualByComparingTo(new BigDecimal("0.0010000"));
  }

  @Test
  public void testUnmarshalOrderBookFromFile() throws IOException {
    // This test is already covered in testUnmarshalOrderBook
    // but we're adding it here to show how to read from a file
    String json =
        "{\n"
            + "  \"lastUpdateId\": 1234567890,\n"
            + "  \"bids\": [\n"
            + "    [\n"
            + "      \"5803095.5\",\n"
            + "      \"0.0000824\"\n"
            + "    ],\n"
            + "    [\n"
            + "      \"5803000.0\",\n"
            + "      \"0.0001000\"\n"
            + "    ]\n"
            + "  ],\n"
            + "  \"asks\": [\n"
            + "    [\n"
            + "      \"5944316.4\",\n"
            + "      \"0.0008186\"\n"
            + "    ],\n"
            + "    [\n"
            + "      \"5944400.0\",\n"
            + "      \"0.0010000\"\n"
            + "    ]\n"
            + "  ]\n"
            + "}";

    // when
    CoinsphOrderBook orderBook = objectMapper.readValue(json, CoinsphOrderBook.class);

    // then
    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getLastUpdateId()).isEqualTo(1234567890L);

    List<CoinsphOrderBookEntry> bids = orderBook.getBids();
    assertThat(bids).isNotNull().hasSize(2);
    assertThat(bids.get(0).getPrice()).isEqualByComparingTo(new BigDecimal("5803095.5"));
    assertThat(bids.get(0).getQuantity()).isEqualByComparingTo(new BigDecimal("0.0000824"));
    assertThat(bids.get(1).getPrice()).isEqualByComparingTo(new BigDecimal("5803000.0"));
    assertThat(bids.get(1).getQuantity()).isEqualByComparingTo(new BigDecimal("0.0001000"));

    List<CoinsphOrderBookEntry> asks = orderBook.getAsks();
    assertThat(asks).isNotNull().hasSize(2);
    assertThat(asks.get(0).getPrice()).isEqualByComparingTo(new BigDecimal("5944316.4"));
    assertThat(asks.get(0).getQuantity()).isEqualByComparingTo(new BigDecimal("0.0008186"));
    assertThat(asks.get(1).getPrice()).isEqualByComparingTo(new BigDecimal("5944400.0"));
    assertThat(asks.get(1).getQuantity()).isEqualByComparingTo(new BigDecimal("0.0010000"));
  }
}
