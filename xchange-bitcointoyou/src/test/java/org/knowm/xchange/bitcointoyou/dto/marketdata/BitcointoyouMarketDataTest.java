package org.knowm.xchange.bitcointoyou.dto.marketdata;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.assertj.core.api.SoftAssertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.bitcointoyou.BitcointoyouAdaptersTest;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests the {@link BitcointoyouMarketData} class.
 *
 * @author Danilo Guimaraes
 */
public class BitcointoyouMarketDataTest {

  private static BitcointoyouMarketData marketData;

  @BeforeClass
  public static void setUp() throws Exception {
    marketData = loadBitcointoyouTickerFromExampleData();
  }

  @Test
  public void testMarketData() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    softly.assertThat(marketData.getLast().toString()).isEqualTo("49349.16");
    softly.assertThat(marketData.getBuy().toString()).isEqualTo("48968.290000000000000");
    softly.assertThat(marketData.getSell().toString()).isEqualTo("49349.150000000000000");
    softly.assertThat(marketData.getHigh().toString()).isEqualTo("52990.00");
    softly.assertThat(marketData.getLow().toString()).isEqualTo("47000.00");
    softly.assertThat(marketData.getVolume()).isEqualTo(new BigDecimal("136.99427076"));
    softly.assertThat(marketData.getDate()).isEqualTo(1515625901L);

    softly.assertAll();
  }

  private static BitcointoyouMarketData loadBitcointoyouTickerFromExampleData() throws IOException {

    InputStream is = BitcointoyouAdaptersTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(is, BitcointoyouMarketData.class);
  }


}
