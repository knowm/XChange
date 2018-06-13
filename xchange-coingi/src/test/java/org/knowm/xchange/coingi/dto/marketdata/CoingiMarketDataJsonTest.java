package org.knowm.xchange.coingi.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CoingiMarketDataJsonTest {

  @Test
  public void testDeserializeTicker() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        CoingiMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coingi/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoingiOrderBook ticker = mapper.readValue(is, CoingiOrderBook.class);

    assertThat(ticker).isNotNull();

    assertThat(ticker.getAsks()).isNotEmpty();
    assertThat(ticker.getBids()).isNotEmpty();

    CoingiOrderGroup bid = ticker.getBids().iterator().next();
    CoingiOrderGroup ask = ticker.getAsks().iterator().next();

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
