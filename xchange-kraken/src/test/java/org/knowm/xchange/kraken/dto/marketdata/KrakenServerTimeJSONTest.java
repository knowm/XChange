package org.knowm.xchange.kraken.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.junit.Test;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenServerTimeResult;
import org.knowm.xchange.utils.DateUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KrakenServerTimeJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenServerTimeJSONTest.class.getResourceAsStream("/marketdata/example-servertime-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenServerTimeResult krakenResult = mapper.readValue(is, KrakenServerTimeResult.class);
    KrakenServerTime serverTime = krakenResult.getResult();

    assertThat(serverTime.getUnixTime()).isEqualTo(1391835876);
    assertThat(serverTime.getRfc1123Time()).isEqualTo(DateUtils.fromRfc1123DateString("Sat,  8 Feb 14 05:04:36 +0000", Locale.US));
  }
}
