package org.knowm.xchange.bitcoinde.v4.dto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class BitcoindeErrorTest {

  @Test
  public void testBitcoindeError() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoindeErrorTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/errors.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeException bitcoindeException = mapper.readValue(is, BitcoindeException.class);
    assertThat(bitcoindeException.getMessage())
        .isEqualTo("Error 13: Order not found (Field: order_id), Error 99: Made up Error");

    assertThat(bitcoindeException.getCredits()).isEqualTo(-3);
    assertThat(bitcoindeException.getErrors()).hasSize(2);
    assertThat(bitcoindeException.getErrors()[0].getCode()).isEqualTo(13);
    assertThat(bitcoindeException.getErrors()[0].getMessage()).isEqualTo("Order not found");
    assertThat(bitcoindeException.getErrors()[0].getField()).isEqualTo("order_id");
    assertThat(bitcoindeException.getErrors()[1].getCode()).isEqualTo(99);
    assertThat(bitcoindeException.getErrors()[1].getMessage()).isEqualTo("Made up Error");
    assertThat(bitcoindeException.getErrors()[1].getField()).isNull();
  }
}
