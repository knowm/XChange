package org.knowm.xchange.latoken.dto.exchangeinfo;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

class LatokenRateLimitsTest {

  LatokenRateLimits limits;

  @BeforeEach
  void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/exchangeinfo/latoken-limits-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    limits = mapper.readValue(is, LatokenRateLimits.class);
  }

  @Test
  void latokenRateLimits() {
    assertThat(limits).isNotNull();
  }

  @Test
  void getPublicEndpoints() {
    assertThat(limits.getPublicEndpoints()).isNotNull();
    assertThat(limits.getPublicEndpoints().size()).isEqualTo(1);
  }

  @Test
  void getSignedEndpoints() {
    assertThat(limits.getSignedEndpoints()).isNotNull();
    assertThat(limits.getSignedEndpoints().size()).isEqualTo(1);
  }

  @Test
  void testToString() {
    assertThat(limits.toString()).isNotNull();
  }
}
