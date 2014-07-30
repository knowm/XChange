package com.xeiam.xchange.btce.v3.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btce.v3.BTCEUtils;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.DateUtils;

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
    assertThat(bTCETickerWrapper.getTicker(BTCEUtils.getPair(CurrencyPair.BTC_USD)).getLast()).isEqualTo(new BigDecimal("757"));
    assertThat(bTCETickerWrapper.getTicker(BTCEUtils.getPair(CurrencyPair.BTC_USD)).getHigh()).isEqualTo(new BigDecimal("770"));
    assertThat(bTCETickerWrapper.getTicker(BTCEUtils.getPair(CurrencyPair.BTC_USD)).getLow()).isEqualTo(new BigDecimal("655"));
    assertThat(bTCETickerWrapper.getTicker(BTCEUtils.getPair(CurrencyPair.BTC_USD)).getVol()).isEqualTo(new BigDecimal("17512163.25736"));

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(DateUtils.fromMillisUtc(bTCETickerWrapper.getTicker(BTCEUtils.getPair(CurrencyPair.BTC_USD)).getUpdated() * 1000L));
    System.out.println(dateString);
    assertThat(dateString).isEqualTo("2013-11-23 11:13:39");
  }

}
