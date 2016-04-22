package org.knowm.xchange.quoine.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test QuoineOrderDetailsResponse JSON parsing
 */
public class QuoineOrderDetailsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = QuoineOrderDetailsJSONTest.class.getResourceAsStream("/trade/example-order-details.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineOrderDetailsResponse quoineOrderDetailsResponse = mapper.readValue(is, QuoineOrderDetailsResponse.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(quoineOrderDetailsResponse.getId()).isEqualTo("52364");
    assertThat(quoineOrderDetailsResponse.getQuantity()).isEqualTo(new BigDecimal(".1"));
    assertThat(quoineOrderDetailsResponse.getExecutions().length).isEqualTo(1);
    assertThat(quoineOrderDetailsResponse.getExecutions()[0].getBuyFxRate()).isEqualTo(new BigDecimal("0.0"));
  }
}
