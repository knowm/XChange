package org.knowm.xchange.latoken.dto.exchangeinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

public class LatokenRateLimitsTest {

  LatokenRateLimits limits;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/exchangeinfo/latoken-limits-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    limits = mapper.readValue(is, LatokenRateLimits.class);
  }

  @Test
  public void testLatokenRateLimits() {
    assertNotNull(limits);
  }

  @Test
  public void testGetPublicEndpoints() {
    assertNotNull(limits.getPublicEndpoints());
    assertEquals(1, limits.getPublicEndpoints().size());
  }

  @Test
  public void testGetSignedEndpoints() {
    assertNotNull(limits.getSignedEndpoints());
    assertEquals(1, limits.getSignedEndpoints().size());
  }

  @Test
  public void testToString() {
    assertNotNull(limits.toString());
  }
}
