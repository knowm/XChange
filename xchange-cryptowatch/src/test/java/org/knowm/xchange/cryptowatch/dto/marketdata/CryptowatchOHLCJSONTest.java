package org.knowm.xchange.cryptowatch.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.cryptowatch.TestUtils;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchOHLCResult;

public class CryptowatchOHLCJSONTest {

  @Test
  public void testUnmarshal() throws IOException {
    CryptowatchOHLCResult result =
        TestUtils.getObject(
            "/org/knowm/xchange/cryptowatch/marketdata/example-ohlc-results.json",
            CryptowatchOHLCResult.class);

    assertNotNull(result);
    assertNull(result.getError());
    assertNotNull(result.getResult());
    CryptowatchOHLCs cryptowatchOHLCs = result.getResult();
    assertFalse(cryptowatchOHLCs.getOHLCs().isEmpty());
    assertTrue(cryptowatchOHLCs.getOHLCs().containsKey(180));
    assertTrue(cryptowatchOHLCs.getOHLCs().containsKey(1800));
    assertEquals(1533304980, cryptowatchOHLCs.getOHLCs().get(180).get(0).getTime());
  }

  @Test
  public void testUnmarshalError() throws IOException {
    InputStream is =
        CryptowatchOHLCJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptowatch/marketdata/example-ohlc-errors.json");

    ObjectMapper mapper = new ObjectMapper();
    CryptowatchOHLCResult result = mapper.readValue(is, CryptowatchOHLCResult.class);

    assertNotNull(result);
    assertNull(result.getResult());
    assertEquals("Market not found", result.getError());
  }
}
