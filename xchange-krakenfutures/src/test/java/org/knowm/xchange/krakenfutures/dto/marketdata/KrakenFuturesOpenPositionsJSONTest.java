package org.knowm.xchange.krakenfutures.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

/** @author Panchen */
public class KrakenFuturesOpenPositionsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenFuturesOpenPositionsJSONTest.class.getResourceAsStream(
                "/org/knowm/xchange/krakenfutures/dto/marketdata/example-openPositions-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenFuturesOpenPositions cryptoFacilitiesOpenPositions =
        mapper.readValue(is, KrakenFuturesOpenPositions.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesOpenPositions.isSuccess()).isTrue();

    List<KrakenFuturesOpenPosition> openPositions =
        cryptoFacilitiesOpenPositions.getOpenPositions();

    assertThat(openPositions.size()).isEqualTo(2);

    Iterator<KrakenFuturesOpenPosition> it = openPositions.iterator();
    KrakenFuturesOpenPosition openPosition = it.next();

    assertThat(openPosition.getSymbol()).isEqualTo("f-xbt:usd-sep16");
    assertThat(openPosition.getSide()).isEqualTo("long");
    assertThat(openPosition.getSize()).isEqualTo(new BigDecimal("1"));
    assertThat(openPosition.getPrice()).isEqualTo(new BigDecimal("425.5"));
  }
}
