package com.xeiam.xchange.bitcurex.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcurex.dto.marketdata.account.BitcurexFunds;

public class BitcurexAccountJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexAccountJSONTest.class.getResourceAsStream("/marketdata/example-funds-eur-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexFunds bitcurexFunds = mapper.readValue(is, BitcurexFunds.class);

    // Verify that the example data was unmarshalled correctly
//    assertThat(bitcurexFunds.ge().equals("1ABcdEfgKEqBPMQ6D2ocuYNJNsXgKPcfV7"));
    assertThat(bitcurexFunds.getBtc().compareTo(new BigDecimal("2.59033845")) == 0);
    assertThat(bitcurexFunds.getEur().compareTo(new BigDecimal("6160.06838790")) == 0);
  }

  @Test
  public void testUnmarshalPLN() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexAccountJSONTest.class.getResourceAsStream("/marketdata/example-funds-pln-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexFunds bitcurexFunds = mapper.readValue(is, BitcurexFunds.class);

    // Verify that the example data was unmarshalled correctly
//    assertThat(bitcurexFunds.getAddress().equals("1ABcdEfgKEqBPMQ6D2ocuYNJNsXgKPcfV7"));
    assertThat(bitcurexFunds.getBtc().compareTo(new BigDecimal("2.59033845")) == 0);
    assertThat(bitcurexFunds.getPln().compareTo(new BigDecimal("6160.06838790")) == 0);
  }

}
