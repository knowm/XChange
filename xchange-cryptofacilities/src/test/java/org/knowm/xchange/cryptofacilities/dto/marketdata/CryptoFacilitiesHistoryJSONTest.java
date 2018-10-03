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
public class CryptoFacilitiesHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptoFacilitiesFillsJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptofacilities/dto/marketdata/example-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesPublicFills cryptoFacilitiesPublicFills =
        mapper.readValue(is, CryptoFacilitiesPublicFills.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesPublicFills.isSuccess()).isTrue();

    List<CryptoFacilitiesPublicFill> fills = cryptoFacilitiesPublicFills.getFills();

    assertThat(fills.size()).isEqualTo(2);

    Iterator<CryptoFacilitiesPublicFill> it = fills.iterator();
    CryptoFacilitiesPublicFill fill = it.next();

    assertThat(fill.getFillId()).isEqualTo("865");
    assertThat(fill.getPrice()).isEqualTo(new BigDecimal("4322"));
    assertThat(fill.getSize()).isEqualTo(new BigDecimal("5000"));
    assertThat(fill.getSide()).isEqualTo("buy");
    assertThat(fill.getFillType()).isEqualTo("fill");
  }
}
