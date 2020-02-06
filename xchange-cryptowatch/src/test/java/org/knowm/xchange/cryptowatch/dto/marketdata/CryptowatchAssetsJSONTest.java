package org.knowm.xchange.cryptowatch.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.cryptowatch.TestUtils;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchAssetsResult;

public class CryptowatchAssetsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {
    CryptowatchAssetsResult result =
        TestUtils.getObject(
            "/org/knowm/xchange/cryptowatch/marketdata/example-assets-results.json",
            CryptowatchAssetsResult.class);

    assertNotNull(result);
    assertEquals(725, result.getResult().size());
    assertEquals("1st", result.getResult().get(0).getSymbol());
  }
}
