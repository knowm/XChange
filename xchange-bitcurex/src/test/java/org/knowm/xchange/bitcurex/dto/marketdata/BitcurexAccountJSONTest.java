package org.knowm.xchange.bitcurex.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BitcurexAccountJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexAccountJSONTest.class.getResourceAsStream("/marketdata/example-funds-eur-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexFunds bitcurexFunds = mapper.readValue(is, BitcurexFunds.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitcurexFunds.getAddress().equals("1ABcdEfgKEqBPMQ6D2ocuYNJNsXgKPcfV7"));
    assertThat(bitcurexFunds.getBtcs().compareTo(new BigDecimal("2.59033845")) == 0);
    assertThat(bitcurexFunds.getEurs().compareTo(new BigDecimal("6160.06838790")) == 0);
  }

  @Test
  public void testUnmarshalPLN() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexAccountJSONTest.class.getResourceAsStream("/marketdata/example-funds-pln-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexFunds bitcurexFunds = mapper.readValue(is, BitcurexFunds.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitcurexFunds.getAddress().equals("1ABcdEfgKEqBPMQ6D2ocuYNJNsXgKPcfV7"));
    assertThat(bitcurexFunds.getBtcs().compareTo(new BigDecimal("2.59033845")) == 0);
    assertThat(bitcurexFunds.getPlns().compareTo(new BigDecimal("6160.06838790")) == 0);
  }

}
