package org.knowm.xchange.latoken.dto.exchangeinfo;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

public class LatokenRateLimitTest {

  LatokenRateLimit limit;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/exchangeinfo/latoken-limits-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    LatokenRateLimits limits = mapper.readValue(is, LatokenRateLimits.class);
    limit = limits.getSignedEndpoints().get(0);
  }

  @Test
  public void testLatokenRateLimit() {
    assertNotNull(limit);
  }

  @Test
  public void testGetEndpoint() {
    assertNotNull(limit.getEndpoint());
  }

  @Test
  public void testGetTimePeriod() {
    assertNotNull(limit.getTimePeriod());
  }

  @Test
  public void testGetRequestLimit() {
    assertNotNull(limit.getRequestLimit());
  }

  @Test
  public void testToString() {
    assertNotNull(limit.toString());
  }
}
