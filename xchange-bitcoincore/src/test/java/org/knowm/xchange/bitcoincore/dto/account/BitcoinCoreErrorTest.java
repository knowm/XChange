package org.knowm.xchange.bitcoincore.dto.account;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class BitcoinCoreErrorTest {

  @Test(expected = JsonMappingException.class)
  public void testUnmarshal() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = getClass().getResourceAsStream("/account/example-error.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.readValue(is, BitcoinCoreBalanceResponse.class);
  }
}
