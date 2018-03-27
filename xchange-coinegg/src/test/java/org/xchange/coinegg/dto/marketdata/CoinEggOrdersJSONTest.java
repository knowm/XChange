package org.xchange.coinegg.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class CoinEggOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinEggOrdersJSONTest.class.getResourceAsStream(
            "/org/xchange/coinegg/dto/marketdata/example-depth-data.json");

    // Parse JSON Example Using Jackson
    ObjectMapper mapper = new ObjectMapper();
    CoinEggOrders coinEggOrders = mapper.readValue(is, CoinEggOrders.class);

    // Verify The Trades Unmarshalls Correctly
    assertThat(coinEggOrders).isNotNull();

    assertThat(coinEggOrders.getAsks()).isNotNull();
    assertThat(coinEggOrders.getAsks()).isNotEmpty();
    assertThat(coinEggOrders.getAsks().length).isEqualTo(31);

    assertThat(coinEggOrders.getAsks()[0].getPrice()).isEqualTo(new BigDecimal("70000"));
    assertThat(coinEggOrders.getAsks()[0].getQuantity()).isEqualTo(new BigDecimal("5"));

    assertThat(coinEggOrders.getBids()).isNotNull();
    assertThat(coinEggOrders.getBids()).isNotEmpty();
    assertThat(coinEggOrders.getBids().length).isEqualTo(32);

    assertThat(coinEggOrders.getBids()[0].getPrice()).isEqualTo(new BigDecimal("38300"));
    assertThat(coinEggOrders.getBids()[0].getQuantity()).isEqualTo(new BigDecimal("1.879"));
  }
}
