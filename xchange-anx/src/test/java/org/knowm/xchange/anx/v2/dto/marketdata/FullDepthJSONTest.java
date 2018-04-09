package org.knowm.xchange.anx.v2.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

/** Test ANXFullDepth JSON parsing */
public class FullDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        FullDepthJSONTest.class.getResourceAsStream("/v2/marketdata/example-fulldepth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    ANXDepth anxFullDepth = mapper.readValue(is, ANXDepth.class);

    // Verify that the example data was unmarshalled correctly
    // assertThat(anxFullDepth.getAsks().get(0).getAmountInt()).isEqualTo(727610000L);

    Assert.assertEquals(new Long(1392693096241000L), anxFullDepth.getMicroTime());
    Assert.assertEquals(3, anxFullDepth.getAsks().size());
    Assert.assertEquals(new BigDecimal("3260.40000"), anxFullDepth.getAsks().get(0).getPrice());
    Assert.assertEquals(326040000L, anxFullDepth.getAsks().get(0).getPriceInt());
    Assert.assertEquals(new BigDecimal("16.00000000"), anxFullDepth.getAsks().get(0).getAmount());
    Assert.assertEquals(1600000000L, anxFullDepth.getAsks().get(0).getAmountInt());

    Assert.assertEquals(4, anxFullDepth.getBids().size());
    Assert.assertEquals(new BigDecimal("2000.00000"), anxFullDepth.getBids().get(0).getPrice());
    Assert.assertEquals(200000000L, anxFullDepth.getBids().get(0).getPriceInt());
    Assert.assertEquals(new BigDecimal("3.00000000"), anxFullDepth.getBids().get(0).getAmount());
    Assert.assertEquals(300000000L, anxFullDepth.getBids().get(0).getAmountInt());
  }
}
