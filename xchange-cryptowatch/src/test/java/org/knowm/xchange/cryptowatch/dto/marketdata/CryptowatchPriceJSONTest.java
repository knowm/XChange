package org.knowm.xchange.cryptowatch.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.cryptowatch.TestUtils;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchPriceResult;

public class CryptowatchPriceJSONTest {

  @Test
  public void testUnmarshal() throws IOException {
    CryptowatchPriceResult result =
        TestUtils.getObject(
            "/org/knowm/xchange/cryptowatch/marketdata/example-price-results.json",
            CryptowatchPriceResult.class);

    assertNotNull(result);
    assertEquals(6977.42d, result.getResult().getPrice().doubleValue(), 0.0d);
  }
}
