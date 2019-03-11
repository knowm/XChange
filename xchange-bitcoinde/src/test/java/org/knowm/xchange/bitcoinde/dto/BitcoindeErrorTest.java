package org.knowm.xchange.bitcoinde.dto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class BitcoindeErrorTest {

  @Test
  public void testBitcoindeOrderBook()
      throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoindeErrorTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/dto/errors.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeException bitcoindeException = mapper.readValue(is, BitcoindeException.class);
    System.out.println(bitcoindeException);

    assertThat(bitcoindeException.getMessage()).isEqualTo("Order not found (-3 credits)");

    assertThat(bitcoindeException.getCredits()).isEqualTo(-3);
    assertThat(bitcoindeException.getErrors()).hasSize(2);
    assertThat(bitcoindeException.getErrors()[1].getField()).isNull();
  }
}
