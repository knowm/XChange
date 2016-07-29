package org.knowm.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcTimeJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcTimeJsonTest.class.getResourceAsStream("/marketdata/example-time-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    HitbtcTime time = mapper.readValue(is, HitbtcTime.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(time.getTimestamp()).isEqualTo(1447130720605L);
  }
}
