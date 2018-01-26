package org.knowm.xchange.bitmex.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test BitstampTicker JSON parsing
 */
public class BitmexTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitmexTradesJSONTest.class.getResourceAsStream("/trade/example-trades.json");

    ObjectMapper mapper = new ObjectMapper();
    BitmexTrade[] bitmexTrades = mapper.readValue(is, BitmexTrade[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitmexTrades.length).isEqualTo(23);

    BitmexTrade bitmexTrade = bitmexTrades[0];
    assertThat(bitmexTrade.getSymbol()).isEqualTo(".XBTUSDPI");
    assertThat(bitmexTrade.getPrice()).isEqualTo(new BigDecimal("0.002924"));
    assertThat(bitmexTrade.getSide()).isEqualTo(BitmexSide.BUY);
    assertThat(bitmexTrade.getSize()).isEqualTo(BigDecimal.ZERO);
    assertThat(bitmexTrade.getForeignNotional()).isNull();
    assertThat(bitmexTrade.getGrossValue()).isNull();
    assertThat(bitmexTrade.getHomeNotional()).isNull();
    assertThat(bitmexTrade.getTickDirection()).isEqualTo(BitmexTickDirection.PLUSTICK);
  }

}