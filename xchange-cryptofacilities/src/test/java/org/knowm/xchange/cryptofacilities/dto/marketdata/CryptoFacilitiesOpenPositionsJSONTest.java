package org.knowm.xchange.cryptofacilities.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

/** @author Panchen */
public class CryptoFacilitiesOpenPositionsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptoFacilitiesOpenPositionsJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptofacilities/dto/marketdata/example-openPositions-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesOpenPositions cryptoFacilitiesOpenPositions =
        mapper.readValue(is, CryptoFacilitiesOpenPositions.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesOpenPositions.isSuccess()).isTrue();

    List<CryptoFacilitiesOpenPosition> openPositions =
        cryptoFacilitiesOpenPositions.getOpenPositions();

    assertThat(openPositions.size()).isEqualTo(2);

    Iterator<CryptoFacilitiesOpenPosition> it = openPositions.iterator();
    CryptoFacilitiesOpenPosition openPosition = it.next();

    assertThat(openPosition.getSymbol()).isEqualTo("f-xbt:usd-sep16");
    assertThat(openPosition.getSide()).isEqualTo("long");
    assertThat(openPosition.getSize()).isEqualTo(new BigDecimal("1"));
    assertThat(openPosition.getPrice()).isEqualTo(new BigDecimal("425.5"));
  }
}
