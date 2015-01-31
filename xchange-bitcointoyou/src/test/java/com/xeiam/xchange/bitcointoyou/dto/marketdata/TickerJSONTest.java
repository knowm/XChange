package com.xeiam.xchange.bitcointoyou.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTicker;

/**
 * Test BitcoinToYouTicker JSON parsing
 * 
 * @author Felipe Micaroni Lalli
 */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    ObjectMapper mapper = new ObjectMapper();
    BitcoinToYouTicker bitcoinToYouTicker = mapper.readValue(is, BitcoinToYouTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitcoinToYouTicker.getTicker().getLast()).isEqualTo(new BigDecimal("955.04"));
    assertThat(bitcoinToYouTicker.getTicker().getHigh()).isEqualTo(new BigDecimal("990.00"));
    assertThat(bitcoinToYouTicker.getTicker().getLow()).isEqualTo(new BigDecimal("905.01"));
    assertThat(bitcoinToYouTicker.getTicker().getVol()).isEqualTo(new BigDecimal("58.16484697"));
    assertThat(bitcoinToYouTicker.getTicker().getBuy()).isEqualTo(new BigDecimal("954.7900000000"));
    assertThat(bitcoinToYouTicker.getTicker().getSell()).isEqualTo(new BigDecimal("982.4800000000"));
    assertThat(bitcoinToYouTicker.getTicker().getDate()).isEqualTo(1412878640L);
  }

}
