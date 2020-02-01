package org.knowm.xchange.coindeal.dto.marketdata;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class CoindealMarketDataDtoTest {

  ObjectMapper mapper = new ObjectMapper();

  @Test
  public void coindealOrderbookDtoTest() throws IOException {

    InputStream is =
        ClassLoader.getSystemClassLoader()
            .getResourceAsStream(
                "org/knowm/xchange/coindeal/dto/marketdata/example-orderbook.json");

    CoindealOrderBook coindealOrderBook = mapper.readValue(is, CoindealOrderBook.class);

    // verify that the example data was unmarshalled correctly
    assertThat(coindealOrderBook.getAsks().get(0).getPrice()).isEqualTo("5636.99000000");
    assertThat(coindealOrderBook.getAsks().get(0).getAmount()).isEqualTo("2.07368963");
    assertThat(coindealOrderBook.getBids().get(0).getPrice()).isEqualTo("5598.67000000");
    assertThat(coindealOrderBook.getBids().get(0).getAmount()).isEqualTo("0.10000000");
  }
}
