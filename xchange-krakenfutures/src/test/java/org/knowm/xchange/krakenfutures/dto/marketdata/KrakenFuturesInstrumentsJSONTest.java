package org.knowm.xchange.krakenfutures.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.krakenfutures.Util;

/** @author Neil Panchen */
public class KrakenFuturesInstrumentsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenFuturesInstrumentsJSONTest.class.getResourceAsStream(
                "/org/knowm/xchange/krakenfutures/dto/marketdata/example-instruments-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenFuturesInstruments cryptoFacilitiesInstruments =
        mapper.readValue(is, KrakenFuturesInstruments.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesInstruments.isSuccess()).isTrue();

    List<KrakenFuturesInstrument> instruments = cryptoFacilitiesInstruments.getInstruments();
    assertThat(instruments.size()).isEqualTo(10);

    Iterator<KrakenFuturesInstrument> it = instruments.iterator();
    KrakenFuturesInstrument ct = it.next();

    assertThat(ct.isTradeable()).isTrue();
    assertThat(ct.getSymbol()).isEqualTo("f-xbt:usd-apr16-w5");
    assertThat(ct.getUnderlying()).isEqualTo("cf-hbpi");
    assertThat(ct.getContractSize()).isEqualTo(new BigDecimal("1"));
    assertThat(ct.getType()).isEqualTo("futures");
    assertThat(ct.getTickSize()).isEqualTo(new BigDecimal("0.01"));

    // 2016-04-29 17:00:00
    assertThat(ct.getLastTradingTime()).isEqualTo(Util.parseDate("2016-04-29T16:00:00.000Z"));
  }
}
