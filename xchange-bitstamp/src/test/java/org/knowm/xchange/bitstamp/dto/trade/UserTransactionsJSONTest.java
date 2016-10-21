package org.knowm.xchange.bitstamp.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction.TransactionType;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test UserTransactions[] JSON parsing
 */
public class UserTransactionsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = UserTransactionsJSONTest.class.getResourceAsStream("/trade/example-user-transactions.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampUserTransaction[] transactions = mapper.readValue(is, BitstampUserTransaction[].class);

    assertThat(transactions.length).isEqualTo(4);

    // Verify that the example data was unmarshalled correctly
    assertThat(transactions[0].getUsd()).isEqualTo(new BigDecimal("-11.37"));
    assertThat(transactions[0].getBtc()).isEqualTo(new BigDecimal("0.08650000"));
    assertThat(transactions[0].getPrice()).isEqualTo(new BigDecimal("131.50"));
    assertThat(transactions[0].getId()).isEqualTo(1296712L);
    assertThat(transactions[0].getOrderId()).isEqualTo(6877187L);
    assertThat(transactions[0].getDatetime()).isEqualTo("2013-09-02 13:17:49");
    assertThat(transactions[0].getType()).isEqualTo(TransactionType.trade);

    assertThat(transactions[1].getUsd()).isEqualTo(new BigDecimal("11.37"));
    assertThat(transactions[1].getPrice()).isEqualTo(new BigDecimal("-131.50"));

  }
}
