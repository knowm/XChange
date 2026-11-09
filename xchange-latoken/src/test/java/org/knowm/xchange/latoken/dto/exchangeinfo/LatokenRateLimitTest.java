package org.knowm.xchange.latoken.dto.exchangeinfo;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

class LatokenRateLimitTest {

  LatokenRateLimit limit;

  @BeforeEach
  void testSetup() throws Exception {
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
  void latokenRateLimit() {
    assertThat(limit).isNotNull();
  }

  @Test
  void getEndpoint() {
    assertThat(limit.getEndpoint()).isNotNull();
  }

  @Test
  void getTimePeriod() {
    assertThat(limit.getTimePeriod()).isNotNull();
  }

  @Test
  void getRequestLimit() {
    assertThat(limit.getRequestLimit()).isNotNull();
  }

  @Test
  void testToString() {
    assertThat(limit.toString()).isNotNull();
  }
}
