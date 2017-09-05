package org.knowm.xchange.dsx.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DSXAdapters;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test DSXTrade[] JSON parsing
 *
 * @author Mikhail Wall
 */
public class DSXTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    InputStream is = DSXTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXTradesWrapper dsxTradesWrapper = mapper.readValue(is, DSXTradesWrapper.class);

    assertThat(dsxTradesWrapper.getTrades(DSXAdapters.getPair(CurrencyPair.BTC_USD))[0].getPrice()).isEqualTo(new BigDecimal("1588.09000"));
    assertThat(dsxTradesWrapper.getTrades(DSXAdapters.getPair(CurrencyPair.BTC_USD)).length).isEqualTo(150);
  }
}
