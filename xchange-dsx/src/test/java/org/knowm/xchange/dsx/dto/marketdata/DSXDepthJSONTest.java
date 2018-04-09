package org.knowm.xchange.dsx.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DSXAdapters;

/**
 * Test DSXOrderbook JSON parsing
 *
 * @author Mikhail Wall
 */
public class DSXDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    InputStream is =
        DSXDepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dsx/dto/marketdata/example-depth-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXOrderbookWrapper dsxOrderbookWrapper = mapper.readValue(is, DSXOrderbookWrapper.class);

    assertThat(
            dsxOrderbookWrapper
                .getOrderbook(DSXAdapters.currencyPairToMarketName(CurrencyPair.BTC_USD))
                .getAsks()
                .get(0)[0])
        .isEqualTo(new BigDecimal("103.426"));
    assertThat(
            dsxOrderbookWrapper
                .getOrderbook(DSXAdapters.currencyPairToMarketName(CurrencyPair.BTC_RUB))
                .getAsks())
        .hasSize(4);
  }
}
