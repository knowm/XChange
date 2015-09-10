package com.xeiam.xchange.bitmarket.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author kfonal
 */
public class BitMarketHistoryOperationsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitMarketHistoryOperationsJSONTest.class.getResourceAsStream("/trade/example-history-operations-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitMarketHistoryOperationsResponse response = mapper.readValue(is, BitMarketHistoryOperationsResponse.class);

    // Verify that the example data was unmarshalled correctly
    BitMarketHistoryOperations operations = response.getData();

    assertThat(response.getSuccess()).isTrue();
    assertThat(operations.getTotal()).isEqualTo(8);
    assertThat(operations.getOperations().size()).isEqualTo(8);

    BitMarketHistoryOperation operation = operations.getOperations().get(1);

    assertThat(operation.getAmount()).isEqualTo(new BigDecimal("75.43901688"));
    assertThat(operation.getCurrency()).isEqualTo("PLN");
    assertThat(operation.getRate()).isEqualTo(new BigDecimal("842.00030000"));
    assertThat(operation.getType()).isEqualTo("trade");
    assertThat(operation.getId()).isEqualTo(11852548);
  }
}
