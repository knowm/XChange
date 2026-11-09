package org.knowm.xchange.dvchain.v4.dto.trades;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dvchain.dto.trade.DVChainNewMarketOrder;
import org.knowm.xchange.dvchain.dto.trade.DVChainTradesResponse;

class DVChainTradesJSONTest {
  @Test
  void unmarshal() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        DVChainTradesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dvchain/v4/trades/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    DVChainTradesResponse readValue = mapper.readValue(is, DVChainTradesResponse.class);

    assertThat(readValue.getTotal().intValue()).isEqualTo(1);
    assertThat(readValue.getPageCount().intValue()).isEqualTo(1);
    assertThat(readValue.getData().get(0).getId()).isEqualTo("5bbd1c6709ac22627841ad32");
    assertThat(Instant.parse("2018-10-09T21:23:51.757Z"))
        .isEqualTo(readValue.getData().get(0).getCreatedAt());
    assertThat(new BigDecimal(("513.3"))).isEqualTo(readValue.getData().get(0).getPrice());
    assertThat(new BigDecimal(".1")).isEqualTo(readValue.getData().get(0).getQuantity());
    assertThat(readValue.getData().get(0).getSide()).isEqualTo("Buy");
    assertThat(readValue.getData().get(0).getAsset()).isEqualTo("BCH");
    assertThat(readValue.getData().get(0).getStatus()).isEqualTo("Complete");
    assertThat(readValue.getData().get(0).getUser().getFirstName()).isEqualTo("Roger");
    assertThat(readValue.getData().get(0).getUser().getLastName()).isEqualTo("Ver");
    assertThat(readValue.getData().get(0).getUser().getId()).isEqualTo("5ab545a4b933aa1f78e25f34");
  }

  @Test
  void placeOrder() throws Exception {
    ObjectMapper mapper = new ObjectMapper();

    DVChainNewMarketOrder newTrade =
        new DVChainNewMarketOrder("Buy", new BigDecimal("527.51"), new BigDecimal(".1"), "BCH");

    // Read in the JSON from the example resources
    InputStream is =
        DVChainTradesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dvchain/v4/trades/example-new-order.json");

    String trade = mapper.writeValueAsString(newTrade);

    assertThat(trade)
        .isEqualTo(
            "{\"side\":\"Buy\",\"price\":527.51,\"qty\":0.1,\"asset\":\"BCH\",\"orderType\":\"market\"}");
  }
}
