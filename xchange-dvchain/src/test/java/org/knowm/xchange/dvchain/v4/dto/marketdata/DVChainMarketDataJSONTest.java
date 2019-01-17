package org.knowm.xchange.dvchain.v4.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainLevel;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainMarketResponse;

public class DVChainMarketDataJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        DVChainMarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dvchain/v4/marketdata/example-prices-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    DVChainMarketResponse readValue = mapper.readValue(is, DVChainMarketResponse.class);

    assertTrue(readValue.getMarketData().keySet().contains("BTC"));
    assertTrue(readValue.getMarketData().keySet().contains("ETH"));
    assertEquals(readValue.getMarketData().get("BTC").getExpiresAt().longValue(), 1539626145342L);
    assertEquals(readValue.getMarketData().get("ETH").getExpiresAt().longValue(), 1539626145341L);

    List<DVChainLevel> levels = readValue.getMarketData().get("BTC").getLevels();

    assertEquals(levels.get(0).getBuyPrice(), new BigDecimal("6218.61"));
    assertEquals(levels.get(0).getSellPrice(), new BigDecimal("6181.4"));
    assertEquals(levels.get(0).getMaxQuantity(), new BigDecimal("1"));

    assertEquals(levels.get(1).getBuyPrice(), new BigDecimal("6228.63"));
    assertEquals(levels.get(1).getSellPrice(), new BigDecimal("6171.43"));
    assertEquals(levels.get(1).getMaxQuantity(), new BigDecimal("5"));

    assertEquals(levels.get(2).getBuyPrice(), new BigDecimal("6238.66"));
    assertEquals(levels.get(2).getSellPrice(), new BigDecimal("6161.46"));
    assertEquals(levels.get(2).getMaxQuantity(), new BigDecimal("10"));
  }
}
