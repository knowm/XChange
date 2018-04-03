package org.knowm.xchange.bitmex.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;

/** Test BitstampTicker JSON parsing */
public class BitmexTickersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitmexTickersJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitmex/example-tickers.json");

    ObjectMapper mapper = new ObjectMapper();
    BitmexTicker[] bitmexTickers = mapper.readValue(is, BitmexTicker[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitmexTickers.length).isEqualTo(2);
    assertThat(bitmexTickers[0].getSymbol()).isEqualTo("XBTZ14");
    assertThat(bitmexTickers[0].getReferenceSymbol()).isEqualTo(".XBT2H");
    assertThat(bitmexTickers[0].getRootSymbol()).isEqualTo("XBT");
    assertThat(bitmexTickers[0].getUnderlying()).isEqualTo("XBT");
    assertThat(bitmexTickers[0].getUnderlyingSymbol()).isEqualTo("XBT=");
  }
}
