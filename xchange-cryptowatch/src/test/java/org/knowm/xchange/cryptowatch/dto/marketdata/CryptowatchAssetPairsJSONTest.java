package org.knowm.xchange.cryptowatch.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.cryptowatch.TestUtils;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchAssetPairsResult;

public class CryptowatchAssetPairsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {
    CryptowatchAssetPairsResult result =
        TestUtils.getObject(
            "/org/knowm/xchange/cryptowatch/marketdata/example-pairs-results.json",
            CryptowatchAssetPairsResult.class);

    assertNotNull(result);
    assertEquals(2390, result.getResult().size());
    assertEquals("1stbtc", result.getResult().get(0).getSymbol());
  }
}
