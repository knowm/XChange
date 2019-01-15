package org.knowm.xchange.dvchain.v4.dto.trades;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.Test;
import org.knowm.xchange.dvchain.dto.trade.DVChainNewMarketOrder;
import org.knowm.xchange.dvchain.dto.trade.DVChainTradesResponse;

public class DVChainTradesJSONTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        DVChainTradesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dvchain/v4/trades/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    DVChainTradesResponse readValue = mapper.readValue(is, DVChainTradesResponse.class);

    assertEquals(readValue.getTotal().intValue(), 1);
    assertEquals(readValue.getPageCount().intValue(), 1);
    assertEquals(readValue.getData().get(0).getId(), "5bbd1c6709ac22627841ad32");
    assertEquals(
        readValue.getData().get(0).getCreatedAt(), Instant.parse("2018-10-09T21:23:51.757Z"));
    assertEquals(readValue.getData().get(0).getPrice(), new BigDecimal(("513.3")));
    assertEquals(readValue.getData().get(0).getQuantity(), new BigDecimal(".1"));
    assertEquals(readValue.getData().get(0).getSide(), "Buy");
    assertEquals(readValue.getData().get(0).getAsset(), "BCH");
    assertEquals(readValue.getData().get(0).getStatus(), "Complete");
    assertEquals(readValue.getData().get(0).getUser().getFirstName(), "Roger");
    assertEquals(readValue.getData().get(0).getUser().getLastName(), "Ver");
    assertEquals(readValue.getData().get(0).getUser().getId(), "5ab545a4b933aa1f78e25f34");
  }

  @Test
  public void testPlaceOrder() throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    DVChainNewMarketOrder newTrade =
        new DVChainNewMarketOrder("Buy", new BigDecimal("527.51"), new BigDecimal(".1"), "BCH");

    // Read in the JSON from the example resources
    InputStream is =
        DVChainTradesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dvchain/v4/trades/example-new-order.json");

    String trade = mapper.writeValueAsString(newTrade);

    assertEquals(
        trade,
        "{\"side\":\"Buy\",\"price\":527.51,\"qty\":0.1,\"asset\":\"BCH\",\"orderType\":\"market\"}");
  }
}
