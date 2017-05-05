package org.known.xchange.dsx.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.known.xchange.dsx.DSXAdapters;
import org.known.xchange.dsx.dto.marketdata.DSXTradesWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Mikhail Wall
 */
public class DSXTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    InputStream is = DSXTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXTradesWrapper dsxTradesWrapper = mapper.readValue(is, DSXTradesWrapper.class);

    assertThat(dsxTradesWrapper.getTrades(DSXAdapters.getPair(CurrencyPair.BTC_USD)) [0].getPrice()).isEqualTo(new BigDecimal("1588.09000"));
    assertThat(dsxTradesWrapper.getTrades(DSXAdapters.getPair(CurrencyPair.BTC_USD)).length).isEqualTo(150);
  }
}
