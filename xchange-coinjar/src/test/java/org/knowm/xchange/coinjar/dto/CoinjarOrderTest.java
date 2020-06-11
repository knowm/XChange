package org.knowm.xchange.coinjar.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class CoinjarOrderTest {

  @Test
  public void testUnmarshal() throws IOException {

    InputStream is =
        CoinjarOrderTest.class.getResourceAsStream("/org/knowm/xchange/coinjar/dto/order.json");

    ObjectMapper mapper = new ObjectMapper();
    CoinjarOrder coinjarOrder = mapper.readValue(is, CoinjarOrder.class);

    assertThat(coinjarOrder.oid).isEqualTo(3267);
    assertThat(coinjarOrder.productId).isEqualTo("BTCAUD");
    assertThat(coinjarOrder.orderSide).isEqualTo("buy");
    assertThat(coinjarOrder.price).isEqualTo("6203.00000000");
    assertThat(coinjarOrder.size).isEqualTo("0.04000000");
    assertThat(coinjarOrder.status).isEqualTo("filled");
    assertThat(coinjarOrder.timestamp).isEqualTo("2017-10-12T15:39:27.000+11:00");
  }
}
