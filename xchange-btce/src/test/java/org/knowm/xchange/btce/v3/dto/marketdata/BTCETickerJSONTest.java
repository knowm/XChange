package org.knowm.xchange.btce.v3.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;
import org.knowm.xchange.btce.v3.BTCEAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.DateUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test BTCETicker JSON parsing
 */
public class BTCETickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETickerJSONTest.class.getResourceAsStream("/v3/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETickerWrapper bTCETickerWrapper = mapper.readValue(is, BTCETickerWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bTCETickerWrapper.getTicker(BTCEAdapters.getPair(CurrencyPair.BTC_USD)).getLast()).isEqualTo(new BigDecimal("757"));
    assertThat(bTCETickerWrapper.getTicker(BTCEAdapters.getPair(CurrencyPair.BTC_USD)).getHigh()).isEqualTo(new BigDecimal("770"));
    assertThat(bTCETickerWrapper.getTicker(BTCEAdapters.getPair(CurrencyPair.BTC_USD)).getLow()).isEqualTo(new BigDecimal("655"));
    assertThat(bTCETickerWrapper.getTicker(BTCEAdapters.getPair(CurrencyPair.BTC_USD)).getVol()).isEqualTo(new BigDecimal("17512163.25736"));

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f
        .format(DateUtils.fromMillisUtc(bTCETickerWrapper.getTicker(BTCEAdapters.getPair(CurrencyPair.BTC_USD)).getUpdated() * 1000L));
    System.out.println(dateString);
    assertThat(dateString).isEqualTo("2013-11-23 11:13:39");
  }

}
