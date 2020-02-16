package org.knowm.xchange.cryptowatch.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.cryptowatch.TestUtils;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchSummaryResult;

public class CryptowatchSummaryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {
    InputStream is =
        CryptowatchSummaryJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptowatch/marketdata/example-summary-results.json");

    ObjectMapper mapper = new ObjectMapper();
    CryptowatchSummaryResult result =
        TestUtils.getObject(
            "/org/knowm/xchange/cryptowatch/marketdata/example-summary-results.json",
            CryptowatchSummaryResult.class);

    assertNotNull(result);
    assertEquals(6987.97d, result.getResult().getPrice().getLast().doubleValue(), 0.0d);
    assertEquals(7538.53d, result.getResult().getPrice().getHigh().doubleValue(), 0.0d);
    assertEquals(6956.15, result.getResult().getPrice().getLow().doubleValue(), 0.0d);
    assertEquals(
        -0.06702546, result.getResult().getPrice().getChange().getPercentage().doubleValue(), 0.0d);
    assertEquals(
        -502.02002, result.getResult().getPrice().getChange().getAbsolute().doubleValue(), 0.0d);

    assertEquals(10302.224, result.getResult().getVolume().doubleValue(), 0.0d);
    assertEquals(74716370, result.getResult().getVolumeQuote().doubleValue(), 0.0d);
  }
}
