package org.knowm.xchange.independentreserve.dto.trade;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.Test;

public class IndependentReserveTransactionsResponseTest {

  @Test
  public void testUnmarshall() throws IOException {
    InputStream is =
        IndependentReserveTransactionsResponseTest.class.getResourceAsStream(
            "/org/knowm/xchange/independentreserve/dto/trade/GetTransactionsResponse.json");

    ObjectMapper mapper = new ObjectMapper();
    IndependentReserveTransactionsResponse response =
        mapper.readValue(is, IndependentReserveTransactionsResponse.class);

    assertThat(response.getPageSize()).isEqualTo(25);
    assertThat(response.getTotalItems()).isEqualTo(6);
    assertThat(response.getTotalPages()).isEqualTo(1);

    assertThat(response.getIndependentReserveTranasactions()).hasSize(1);

    IndependentReserveTransaction transaction =
        response.getIndependentReserveTranasactions().get(0);
    assertThat(transaction.getBalance()).isEqualByComparingTo(new BigDecimal("199954.27000000"));
    assertThat(transaction.getBitcoinTransactionId()).isNull();
    assertThat(transaction.getBitcoinTransactionOutputIndex()).isNull();
    assertThat(transaction.getEthereumTransactionId()).isNull();
    assertThat(transaction.getComment()).isNull();
    assertThat(transaction.getCreatedTimestamp())
        .isEqualTo(Date.from(ZonedDateTime.of(2014, 8, 3, 5, 33, 48, 23, UTC).toInstant()));
    assertThat(transaction.getCredit()).isNull();
    assertThat(transaction.getCurrencyCode()).isEqualTo("Usd");
    assertThat(transaction.getDebit()).isEqualByComparingTo(new BigDecimal("6.98000000"));
    assertThat(transaction.getSettleTimestamp())
        .isEqualTo(Date.from(ZonedDateTime.of(2014, 8, 3, 5, 36, 24, 55, UTC).toInstant()));
    assertThat(transaction.getStatus()).isEqualTo("Confirmed");
    assertThat(transaction.getType()).isEqualTo(IndependentReserveTransaction.Type.Brokerage);
  }
}
