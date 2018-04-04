package org.xchange.bitz.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.xchange.bitz.dto.marketdata.result.BitZTradesResult;

public class BitZOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitZOrdersJSONTest.class.getResourceAsStream(
            "/org/xchange/bitz/dto/marketdata/example-orders-data.json");

    // Parse JSON Example Using Jackson
    ObjectMapper mapper = new ObjectMapper();
    BitZTradesResult bitzOrdersResult = mapper.readValue(is, BitZTradesResult.class);
    BitZTrades bitzOrders = bitzOrdersResult.getData();

    // Verify The Depth Result Unmarshalls Correctly
    assertThat(bitzOrdersResult.getCode()).isEqualTo(0);
    assertThat(bitzOrdersResult.getMessage()).isEqualTo("Success");
    assertThat(bitzOrdersResult.getData()).isNotNull();

    // Verify The Depth Unmarshalls Correctly
    assertThat(bitzOrders.getMax()).isEqualTo(new BigDecimal("0.00100000"));
    assertThat(bitzOrders.getMin()).isEqualTo(new BigDecimal("0.00089504"));
    assertThat(bitzOrders.getSum()).isEqualTo(new BigDecimal("15263.8989"));
  }
}
