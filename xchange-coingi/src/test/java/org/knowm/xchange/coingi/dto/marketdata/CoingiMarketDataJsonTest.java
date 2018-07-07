package org.knowm.xchange.coingi.dto.marketdata;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class CoingiMarketDataJsonTest {

  @Test
  public void testDeserializeOrderBook() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        CoingiMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coingi/dto/marketdata/example-orderbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoingiOrderBook orderBook = mapper.readValue(is, CoingiOrderBook.class);

    assertThat(orderBook).isNotNull();

    assertThat(orderBook.getAsks()).isNotEmpty();
    assertThat(orderBook.getBids()).isNotEmpty();

    CoingiOrderGroup bid = orderBook.getBids().iterator().next();
    CoingiOrderGroup ask = orderBook.getAsks().iterator().next();

    assertThat(bid).isNotNull();
    assertThat(ask).isNotNull();

    assertThat(bid.getCurrencyPair().get("base")).isEqualTo("btc");
    assertThat(bid.getCurrencyPair().get("counter")).isEqualTo("eur");
    assertThat(bid.getPrice().doubleValue()).isEqualTo(6694.207);

    assertThat(ask.getCurrencyPair().get("base")).isEqualTo("btc");
    assertThat(ask.getCurrencyPair().get("counter")).isEqualTo("eur");
    assertThat(ask.getPrice().doubleValue()).isEqualTo(6847.153);
  }
}
