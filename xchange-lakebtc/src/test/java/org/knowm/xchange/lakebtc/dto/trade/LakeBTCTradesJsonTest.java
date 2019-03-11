package org.knowm.xchange.lakebtc.dto.trade;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Created by cristian.lucaci on 12/19/2014. */
public class LakeBTCTradesJsonTest {

  @Test
  public void testDeserializeBuyOrder() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        LakeBTCTradesJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/lakebtc/dto/trade/example-past-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    LakeBTCTradeResponse[] responses = mapper.readValue(is, LakeBTCTradeResponse[].class);

    assertEquals("USD", responses[0].getCurrency());
    assertEquals(new BigDecimal("0.8284"), responses[0].getAmount());
    assertEquals(new BigDecimal("506.62"), responses[0].getTotal());
    assertEquals(1403078138, responses[0].getAt());
    assertEquals("sell", responses[0].getType());

    assertEquals("USD", responses[1].getCurrency());
    assertEquals(new BigDecimal("0.3816"), responses[1].getAmount());
    assertEquals(new BigDecimal("233.37"), responses[1].getTotal());
    assertEquals(1403078141, responses[1].getAt());
    assertEquals("sell", responses[1].getType());
  }
}
