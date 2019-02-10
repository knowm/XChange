package org.knowm.xchange.oer.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** @author timmolter */
public class OERTickersTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        OERTickersTest.class.getResourceAsStream(
            "/org/knowm/xchange/oer/dto/marketdata/example-latest-rates.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    OERTickers oERTickers = mapper.readValue(is, OERTickers.class);

    // Verify that the example data was unmarshalled correctly
    System.out.println(oERTickers.getTimestamp().toString());
    assertThat(oERTickers.getTimestamp()).isEqualTo(1354687208L);

    System.out.println(oERTickers.getRates().getAED());
    assertThat(oERTickers.getRates().getAED()).isEqualTo(3.672989);
  }
}
