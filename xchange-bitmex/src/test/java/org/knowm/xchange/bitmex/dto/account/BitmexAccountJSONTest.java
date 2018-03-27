package org.knowm.xchange.bitmex.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** Test BitstampTicker JSON parsing */
public class BitmexAccountJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitmexAccountJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitmex/dto/account/example-account.json");

    ObjectMapper mapper = new ObjectMapper();
    BitmexAccount bitmexAccount = mapper.readValue(is, BitmexAccount.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitmexAccount.getFirstname()).isEqualTo("string");
    assertThat(bitmexAccount.getLastname()).isEqualTo("string");
    assertThat(bitmexAccount.getId()).isEqualTo(0);
    assertThat(bitmexAccount.getOwnerId()).isEqualTo(0);
    assertThat(bitmexAccount.getEmail()).isEqualTo("string");
    assertThat(bitmexAccount.getUsername()).isEqualTo("string");
    assertThat(bitmexAccount.getPhone()).isEqualTo("string");
  }
}
