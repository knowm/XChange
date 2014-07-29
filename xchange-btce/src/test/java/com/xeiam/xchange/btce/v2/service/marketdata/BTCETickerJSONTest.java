package com.xeiam.xchange.btce.v2.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Test BTCETicker JSON parsing
 */
@Deprecated
@Ignore
public class BTCETickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETickerJSONTest.class.getResourceAsStream("/v2/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETickerWrapper BTCETicker = mapper.readValue(is, BTCETickerWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(BTCETicker.getTicker().getLast()).isEqualTo(new BigDecimal("13.07"));
    assertThat(BTCETicker.getTicker().getHigh()).isEqualTo(new BigDecimal("13.23"));
    assertThat(BTCETicker.getTicker().getLow()).isEqualTo(new BigDecimal("13"));
    assertThat(BTCETicker.getTicker().getVol()).isEqualTo(new BigDecimal("40418.44988"));

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(DateUtils.fromMillisUtc(BTCETicker.getTicker().getServerTime() * 1000L));
    assertThat(dateString).isEqualTo("2012-12-22 19:12:09");
  }

}
