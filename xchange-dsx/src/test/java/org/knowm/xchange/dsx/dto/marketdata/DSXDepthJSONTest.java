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
 * Test DSXOrderbook JSON parsing
 *
 * @author Mikhail Wall
 */
public class DSXDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    InputStream is = DSXDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXOrderbookWrapper dsxOrderbookWrapper = mapper.readValue(is, DSXOrderbookWrapper.class);

    assertThat(dsxOrderbookWrapper.getOrderbook(DSXAdapters.getPair(CurrencyPair.BTC_USD)).getAsks().get(0)[0]).isEqualTo(new BigDecimal("103.426"));
    assertThat(dsxOrderbookWrapper.getOrderbook(DSXAdapters.getPair(CurrencyPair.BTC_RUB)).getAsks()).hasSize(4);

  }
}
