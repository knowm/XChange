package org.knowm.xchange.latoken.dto.exchangeinfo;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

public class LatokenTimeTest {
  LatokenTime time;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/exchangeinfo/latoken-time-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    time = mapper.readValue(is, LatokenTime.class);
  }

  @Test
  public void testLatokenTime() {
    assertNotNull(time);
  }

  @Test
  public void testGetTime() {
    assertNotNull(time.getTime());
  }
}
