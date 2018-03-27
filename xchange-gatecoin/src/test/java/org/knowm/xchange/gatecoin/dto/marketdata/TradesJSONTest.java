package org.knowm.xchange.gatecoin.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinTransactionResult;

/** Test Transaction[] JSON parsing */
public class TradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        TradesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gatecoin/dto/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinTransactionResult transactions = mapper.readValue(is, GatecoinTransactionResult.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(transactions.getTransactions()[0].getTransacationTime()).isEqualTo(1435630071);
    assertThat(transactions.getTransactions()[0].getTransactionId()).isEqualTo(1392484);
    assertThat(transactions.getTransactions()[0].getPrice().compareTo((BigDecimal.valueOf(229.29))))
        .isEqualTo(0);
    assertThat(
            transactions.getTransactions()[0].getQuantity().compareTo((BigDecimal.valueOf(0.07))))
        .isEqualTo(0);
  }
}
