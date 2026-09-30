package org.knowm.xchange.latoken.dto.exchangeinfo;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

class LatokenTimeTest {
  LatokenTime time;

  @BeforeEach
  void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/exchangeinfo/latoken-time-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    time = mapper.readValue(is, LatokenTime.class);
  }

  @Test
  void latokenTime() {
    assertThat(time).isNotNull();
  }

  @Test
  void getTime() {
    assertThat(time.getTime()).isNotNull();
  }
}
