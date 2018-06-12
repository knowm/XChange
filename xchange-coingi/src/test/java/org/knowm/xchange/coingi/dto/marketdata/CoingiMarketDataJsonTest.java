package org.knowm.xchange.coingi.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

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

    Iterator<Map.Entry<String, String>> bidIterator = bid.getCurrencyPair().entrySet().iterator();
    Iterator<Map.Entry<String, String>> askIterator = ask.getCurrencyPair().entrySet().iterator();

    assertThat(bidIterator.next().getValue()).isEqualTo("btc"); // base
    assertThat(bidIterator.next().getValue()).isEqualTo("eur"); // counter
    assertThat(bid.getPrice().doubleValue()).isEqualTo(6694.207);

    assertThat(askIterator.next().getValue()).isEqualTo("btc"); // base
    assertThat(askIterator.next().getValue()).isEqualTo("eur"); // counter
    assertThat(ask.getPrice().doubleValue()).isEqualTo(6847.153);
  }
}
