package org.knowm.xchange.coinfloor.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.dto.Order.OrderType;

public class CoinfloorOpenOrdersTest {
  @Test
  public void unmarshalTest() throws IOException {
    InputStream is =
        getClass()
            .getResourceAsStream("/org/knowm/xchange/coinfloor/dto/trade/example-open-orders.json");
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorOrder[] orders = mapper.readValue(is, CoinfloorOrder[].class);

    assertThat(orders).hasSize(2);

    CoinfloorOrder order0 = orders[0];
    assertThat(order0.getId()).isEqualTo(66688608);
    assertThat(order0.getDatetime()).isEqualTo("2017-03-20 11:31:24");
    assertThat(order0.getSide()).isEqualTo(OrderType.ASK);
    assertThat(order0.getPrice()).isEqualTo("2301.00");
    assertThat(order0.getAmount()).isEqualTo("0.0001");

    CoinfloorOrder order1 = orders[1];
    assertThat(order1.getId()).isEqualTo(66688691);
    assertThat(order1.getDatetime()).isEqualTo("2017-04-05 11:34:04");
    assertThat(order1.getSide()).isEqualTo(OrderType.BID);
    assertThat(order1.getPrice()).isEqualTo("2303.00");
    assertThat(order1.getAmount()).isEqualTo("1.0001");
  }
}
