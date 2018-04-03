package org.knowm.xchange.quoine.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test QuoineOrderDetailsResponse JSON parsing */
public class QuoineOrdersListJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        QuoineOrdersListJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/quoine/dto/trade/example-orders-list.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineOrdersList quoineOrdersList = mapper.readValue(is, QuoineOrdersList.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(quoineOrdersList.getModels().length).isEqualTo(18);
    assertThat(quoineOrdersList.getModels()[0].getId()).isEqualTo("52364");
    assertThat(quoineOrdersList.getModels()[0].getPrice()).isEqualTo(new BigDecimal("200.0"));
    assertThat(quoineOrdersList.getModels()[0].getStatus()).isEqualTo("filled");
  }
}
