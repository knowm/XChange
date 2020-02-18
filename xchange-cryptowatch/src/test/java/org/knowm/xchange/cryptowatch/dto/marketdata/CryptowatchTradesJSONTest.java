package org.knowm.xchange.cryptowatch.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.cryptowatch.TestUtils;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchTradesResult;

public class CryptowatchTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {
    CryptowatchTradesResult result =
        TestUtils.getObject(
            "/org/knowm/xchange/cryptowatch/marketdata/example-trades-results.json",
            CryptowatchTradesResult.class);

    assertNotNull(result);
    assertEquals(4, result.getResult().size());
    assertEquals(1533402026, result.getResult().get(0).getTimestamp());
  }
}
