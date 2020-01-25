package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;

public class BitfinexStatusJSONTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitfinexStatusJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitfinex/v2/dto/marketdata/example-status-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CollectionType constructCollectionType =
        mapper.getTypeFactory().constructCollectionType(List.class, Status.class);

    List<Status> status = mapper.readValue(is, constructCollectionType);

    Status s0 = status.get(0);
    assertThat(s0.getSymbol()).isEqualTo("tBTCF0:USTF0");
    assertThat(s0.getTimestamp()).isEqualTo(1579781546000L);
    assertThat(s0.getPlaceHolder0()).isNull();
    assertThat(s0.getDerivPrice()).isEqualByComparingTo("8413.6440137");
    assertThat(s0.getSpotPrice()).isEqualByComparingTo("8418.2");
    assertThat(s0.getPlaceHolder1()).isNull();
    assertThat(s0.getInsuranceFundBalance()).isEqualByComparingTo("402688.36164408");
    assertThat(s0.getPlaceHolder2()).isNull();
  }
}
