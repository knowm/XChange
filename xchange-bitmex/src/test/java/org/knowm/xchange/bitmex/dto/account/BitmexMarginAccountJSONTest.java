package org.knowm.xchange.bitmex.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test BitstampTicker JSON parsing */
public class BitmexMarginAccountJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitmexMarginAccountJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitmex/dto/account/example-margin-account.json");

    ObjectMapper mapper = new ObjectMapper();
    BitmexMarginAccount bitmexMarginAccount = mapper.readValue(is, BitmexMarginAccount.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitmexMarginAccount.getAccount()).isEqualTo(0);
    assertThat(bitmexMarginAccount.getCurrency()).isEqualTo("string");
    assertThat(bitmexMarginAccount.getAmount()).isEqualTo(BigDecimal.ZERO);
    assertThat(bitmexMarginAccount.getAvailableMargin()).isEqualTo(BigDecimal.ZERO);
    assertThat(bitmexMarginAccount.getMaintMargin()).isEqualTo(BigDecimal.ZERO);
    assertThat(bitmexMarginAccount.getMarginBalance()).isEqualTo(BigDecimal.ZERO);
    assertThat(bitmexMarginAccount.getMarginLeverage()).isEqualTo(BigDecimal.ZERO);
    assertThat(bitmexMarginAccount.getTaxableMargin()).isEqualTo(BigDecimal.ZERO);
  }
}
