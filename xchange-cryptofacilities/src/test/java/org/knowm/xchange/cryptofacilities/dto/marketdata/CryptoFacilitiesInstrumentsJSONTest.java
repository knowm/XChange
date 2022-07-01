package org.knowm.xchange.cryptofacilities.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.cryptofacilities.Util;

/** @author Neil Panchen */
public class CryptoFacilitiesInstrumentsJSONTest {

  @Test
  public void testUnmarshal() throws IOException, ParseException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptoFacilitiesInstrumentsJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptofacilities/dto/marketdata/example-instruments-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesInstruments cryptoFacilitiesInstruments =
        mapper.readValue(is, CryptoFacilitiesInstruments.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesInstruments.isSuccess()).isTrue();

    List<CryptoFacilitiesInstrument> instruments = cryptoFacilitiesInstruments.getInstruments();
    assertThat(instruments.size()).isEqualTo(10);

    Iterator<CryptoFacilitiesInstrument> it = instruments.iterator();
    CryptoFacilitiesInstrument ct = it.next();

    assertThat(ct.getTradeable()).isTrue();
    assertThat(ct.getSymbol()).isEqualTo("f-xbt:usd-apr16-w5");
    assertThat(ct.getUnderlying()).isEqualTo("cf-hbpi");
    assertThat(ct.getContractSize()).isEqualTo(new BigDecimal("1"));
    assertThat(ct.getType()).isEqualTo("futures");
    assertThat(ct.getTickSize()).isEqualTo(new BigDecimal("0.01"));

    // 2016-04-29 17:00:00
    assertThat(ct.getLastTradingTime()).isEqualTo(Util.parseDate("2016-04-29T16:00:00.000Z"));
  }
}
