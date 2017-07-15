package org.knowm.xchange.dsx.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DSXAdapters;
import org.knowm.xchange.utils.DateUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test DSXTicker JSON parsing
 *
 * @author Mikhail Wall
 */
public class DSXTickerJSONTest {

  @Test
  public void testUnmarshall() throws IOException {

    InputStream is = DSXTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXTickerWrapper dsxTickerWrapper = mapper.readValue(is, DSXTickerWrapper.class);

    assertThat(dsxTickerWrapper.getTicker(DSXAdapters.getPair(CurrencyPair.BTC_USD)).getLast()).isEqualTo(new BigDecimal("101.773"));
    assertThat(dsxTickerWrapper.getTicker(DSXAdapters.getPair(CurrencyPair.BTC_USD)).getHigh()).isEqualTo(new BigDecimal("109.88"));
    assertThat(dsxTickerWrapper.getTicker(DSXAdapters.getPair(CurrencyPair.BTC_USD)).getLow()).isEqualTo(new BigDecimal("91.14"));
    assertThat(dsxTickerWrapper.getTicker(DSXAdapters.getPair(CurrencyPair.BTC_USD)).getVol()).isEqualTo(new BigDecimal("1632898.2249"));

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f
        .format(DateUtils.fromMillisUtc(dsxTickerWrapper.getTicker(DSXAdapters.getPair(CurrencyPair.BTC_USD)).getUpdated() * 1000L));
    assertThat(dateString).isEqualTo("2013-06-09 22:18:28");
  }
}
