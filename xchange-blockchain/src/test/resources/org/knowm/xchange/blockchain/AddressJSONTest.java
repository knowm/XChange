package org.knowm.xchange.blockchain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.blockchain.dto.BitcoinAddress;

/** @author timmolter */
public class AddressJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = AddressJSONTest.class.getResourceAsStream("/address.json");

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    BitcoinAddress address = mapper.readValue(is, BitcoinAddress.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(address.getNumTransactions()).isEqualTo(59);
    assertThat(address.getFinalBalance()).isEqualTo(78399012);
    assertThat(address.getFinalBalanceDecimal()).isEqualTo(new BigDecimal(".78399012"));
    assertThat(address.getAddress()).isEqualTo("17dQktcAmU4urXz7tGk2sbuiCqykm3WLs6");
  }
}
