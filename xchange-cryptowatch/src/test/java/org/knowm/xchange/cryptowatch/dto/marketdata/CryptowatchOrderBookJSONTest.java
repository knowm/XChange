package org.knowm.xchange.cryptowatch.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.cryptowatch.TestUtils;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchOrderBookResult;

public class CryptowatchOrderBookJSONTest {

  @Test
  public void testUnmarshal() throws IOException {
    CryptowatchOrderBookResult result =
        TestUtils.getObject(
            "/org/knowm/xchange/cryptowatch/marketdata/example-orderbook-results.json",
            CryptowatchOrderBookResult.class);

    assertNotNull(result);
    assertEquals(4, result.getResult().getAsks().size());
    assertEquals(4, result.getResult().getBids().size());
    assertEquals(6998.05d, result.getResult().getAsks().get(0).getPrice().doubleValue(), 0.0d);
    CryptowatchPublicOrder order = result.getResult().getAsks().get(0);
    assertEquals(6998.05, order.getPrice().doubleValue(), 0.0d);
    assertEquals(29.135199, order.getVolume().doubleValue(), 0.0d);
    order = result.getResult().getBids().get(0);
    assertEquals(6998.04, order.getPrice().doubleValue(), 0.0d);
    assertEquals(16.102463, order.getVolume().doubleValue(), 0.0d);
  }
}
