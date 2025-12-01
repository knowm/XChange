package org.knowm.xchange.bitstamp.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;

public class BitstampEarnTransactionJSONTest {

  @Test
  public void testUnmarshal() throws IOException {
    InputStream is =
        BitstampEarnTransactionJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/account/earn-transactions.json");

    ObjectMapper mapper = new ObjectMapper();
    CollectionType collectionType =
        mapper.getTypeFactory().constructCollectionType(List.class, BitstampEarnTransaction.class);

    List<BitstampEarnTransaction> transactions = mapper.readValue(is, collectionType);

    assertThat(transactions).isNotEmpty();
    BitstampEarnTransaction transaction = transactions.get(0);

    assertThat(transaction.getCurrency()).isNotNull();
    assertThat(transaction.getType()).isNotNull();
    assertThat(transaction.getAmount()).isNotNull();
    assertThat(transaction.getDatetime()).isNotNull();
    assertThat(transaction.getStatus()).isNotNull();
  }

  @Test
  public void testUnmarshalSingle() throws IOException {
    InputStream is =
        BitstampEarnTransactionJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/account/earn-transaction-single.json");

    ObjectMapper mapper = new ObjectMapper();
    BitstampEarnTransaction transaction = mapper.readValue(is, BitstampEarnTransaction.class);

    assertThat(transaction.getCurrency()).isEqualTo(Currency.getInstance("SUI"));
    assertThat(transaction.getType())
        .isEqualTo(BitstampEarnTransaction.TransactionType.REWARD_RECEIVED);
    assertThat(transaction.getAmount()).isNotNull();
    assertThat(transaction.getDatetime()).isNotNull();
    assertThat(transaction.getStatus())
        .isEqualTo(BitstampEarnTransaction.TransactionStatus.COMPLETED);
    assertThat(transaction.getQuoteCurrency()).isEqualTo(Currency.USD);
  }

  @Test
  public void testUnmarshalWithIso8601Date() throws IOException {
    InputStream is =
        BitstampEarnTransactionJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/account/earn-transaction-single.json");

    ObjectMapper mapper = new ObjectMapper();
    BitstampEarnTransaction transaction = mapper.readValue(is, BitstampEarnTransaction.class);

    // Verify ISO 8601 date format is parsed correctly
    assertThat(transaction.getDatetime()).isNotNull();
  }
}
