package org.knowm.xchange.anx.v2.dto.marketdata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

/** Test ANXTicker JSON parsing */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        TickerJSONTest.class.getResourceAsStream("/v2/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    ANXTicker anxTicker = mapper.readValue(is, ANXTicker.class);
    //
    System.out.println(anxTicker.toString());

    // Verify that the example data was unmarshalled correctly
    Assert.assertEquals(new BigDecimal("725.38123"), anxTicker.getHigh().getValue());
    Assert.assertEquals(new BigDecimal("380.00000"), anxTicker.getLow().getValue());
    Assert.assertEquals(new BigDecimal("429.34018"), anxTicker.getAvg().getValue());
    Assert.assertEquals(new BigDecimal("429.34018"), anxTicker.getVwap().getValue());
    Assert.assertEquals(new BigDecimal("7.00000000"), anxTicker.getVol().getValue());
    Assert.assertEquals(new BigDecimal("725.38123"), anxTicker.getLast().getValue());
    Assert.assertEquals(new BigDecimal("38.85148"), anxTicker.getBuy().getValue());
    Assert.assertEquals(new BigDecimal("897.25596"), anxTicker.getSell().getValue());
    Assert.assertEquals(1393388594814000L, anxTicker.getNow());
  }
}
