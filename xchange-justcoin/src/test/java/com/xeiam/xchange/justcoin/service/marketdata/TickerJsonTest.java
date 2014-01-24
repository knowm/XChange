package com.xeiam.xchange.justcoin.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;

/**
 * @author jamespedwards42
 */
public class TickerJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = TickerJsonTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final JustcoinTicker[] tickers = mapper.readValue(is, new JustcoinTicker[0].getClass());

    // Verify that the example data was unmarshalled correctly
    assertThat(tickers.length).isEqualTo(5);

    final JustcoinTicker xrpTicker = tickers[4];
    assertThat(xrpTicker.getId()).isEqualTo(JustcoinUtils.getApiCurrencyPair("BTC", "XRP"));
    assertThat(xrpTicker.getLast()).isEqualTo(new BigDecimal("40900.000"));
    assertThat(xrpTicker.getHigh()).isEqualTo(new BigDecimal("43998.000"));
    assertThat(xrpTicker.getLow()).isEqualTo(new BigDecimal("40782.944"));
    assertThat(xrpTicker.getBid()).isEqualTo(new BigDecimal("40905.000"));
    assertThat(xrpTicker.getAsk()).isEqualTo(new BigDecimal("43239.000"));
    assertThat(xrpTicker.getVolume()).isEqualTo(new BigDecimal("26.39377"));
    assertThat(xrpTicker.getScale()).isEqualTo(3);
  }
}
