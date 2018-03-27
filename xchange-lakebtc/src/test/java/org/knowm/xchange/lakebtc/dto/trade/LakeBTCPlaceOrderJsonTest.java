package org.knowm.xchange.lakebtc.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** Created by cristian.lucaci on 12/19/2014. */
public class LakeBTCPlaceOrderJsonTest {

  @Test
  public void testDeserializeBuyOrder() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        LakeBTCPlaceOrderJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/lakebtc/dto/trade/example-place-order-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    LakeBTCOrderResponse order = mapper.readValue(is, LakeBTCOrderResponse.class);

    assertThat(order.getResult().getCurrency()).isEqualTo("CNY");
    assertThat(order.getResult().getTotalTradedBtc().toString()).isEqualTo("1.132");
    assertThat(order.getResult().getTotalTradedCurrency().toString()).isEqualTo("0");
    assertThat(order.getResult().getTrades().toString()).isEqualTo("0");
    assertThat(order.getResult().getPpc()).isNull();
  }

  @Test
  public void testDeserializeSellOrder() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        LakeBTCPlaceOrderJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/lakebtc/dto/trade/example-place-order2-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    LakeBTCOrderResponse order = mapper.readValue(is, LakeBTCOrderResponse.class);

    assertThat(order.getResult().getCurrency()).isEqualTo("CNY");
    assertThat(order.getResult().getTotalTradedBtc().toString()).isEqualTo("0");
    assertThat(order.getResult().getTotalTradedCurrency().toString()).isEqualTo("12");
    assertThat(order.getResult().getTrades().toString()).isEqualTo("1");
    assertThat(order.getResult().getPpc().toString()).isEqualTo("4756.5");
  }
}
