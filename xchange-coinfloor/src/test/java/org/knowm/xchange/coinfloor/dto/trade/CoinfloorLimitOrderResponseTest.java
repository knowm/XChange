package org.knowm.xchange.coinfloor.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class CoinfloorLimitOrderResponseTest {
  @Test
  public void unmarshalTest() throws IOException {
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/coinfloor/dto/trade/example-place-limit-order.json");
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorOrder response = mapper.readValue(is, CoinfloorOrder.class);

    assertThat(response.getId()).isEqualTo(66648479);
    assertThat(response.getDatetime()).isEqualTo("2017-03-20 07:56:41");
    assertThat(response.getType()).isEqualTo(1);
    assertThat(response.getPrice()).isEqualTo("1300.00");
    assertThat(response.getAmount()).isEqualTo("0.0001");
  }
}
