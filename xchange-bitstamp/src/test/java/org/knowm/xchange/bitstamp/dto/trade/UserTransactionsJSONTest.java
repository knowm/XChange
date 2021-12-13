package org.knowm.xchange.bitstamp.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.junit.Test;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction.TransactionType;

/** Test UserTransactions[] JSON parsing */
public class UserTransactionsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        UserTransactionsJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/trade/example-user-transactions.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampUserTransaction[] transactions = mapper.readValue(is, BitstampUserTransaction[].class);

    assertThat(transactions.length).isEqualTo(5);

    // Verify that the example data was unmarshalled correctly
    assertThat(transactions[0].getCounterAmount()).isEqualTo(new BigDecimal("-11.37"));
    assertThat(transactions[0].getBaseAmount()).isEqualTo(new BigDecimal("0.08650000"));
    assertThat(transactions[0].getPrice()).isEqualTo(new BigDecimal("131.50"));
    assertThat(transactions[0].getId()).isEqualTo(1296712L);
    assertThat(transactions[0].getOrderId()).isEqualTo(6877187L);

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(transactions[0].getDatetime());

    assertThat(dateString).isEqualTo("2013-09-02 13:17:49");
    assertThat(transactions[0].getType()).isEqualTo(TransactionType.trade);

    assertThat(transactions[1].getCounterAmount()).isEqualTo(new BigDecimal("11.37"));
    assertThat(transactions[1].getPrice()).isEqualTo(new BigDecimal("-131.50"));

    assertThat(transactions[4].getType()).isEqualTo(TransactionType.stakingReward);
    assertThat(transactions[4].getAmounts().get("ALGO")).isEqualTo(new BigDecimal("0.24178042"));
  }
}
