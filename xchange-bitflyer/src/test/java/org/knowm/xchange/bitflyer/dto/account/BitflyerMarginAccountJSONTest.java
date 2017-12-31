package org.knowm.xchange.bitflyer.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BitflyerMarginAccountJSONTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitflyerMarginAccountJSONTest.class.getResourceAsStream("/account/example-margin-accounts.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitflyerMarginAccount[] response = mapper.readValue(is, BitflyerMarginAccount[].class);

    // then
    assertThat(response.length).isEqualTo(2);

    assertThat(response[0].getAmount()).isEqualTo(new BigDecimal(10000));
    assertThat(response[0].getCurrencyCode()).isEqualTo("JPY");

    assertThat(response[1].getAmount()).isEqualTo(new BigDecimal("1.23"));
    assertThat(response[1].getCurrencyCode()).isEqualTo("BTC");
  }
}
