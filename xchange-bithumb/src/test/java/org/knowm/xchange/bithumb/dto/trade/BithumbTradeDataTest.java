package org.knowm.xchange.bithumb.dto.trade;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.bithumb.dto.account.BithumbAccountDataTest;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BithumbTradeDataTest {
  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testUnmarshallUserTransaction() throws IOException {

    final InputStream is =
        BithumbAccountDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/trade/example-user-transaction.json");
    final BithumbUserTransactionResponse response =
        mapper.readValue(is, BithumbUserTransactionResponse.class);

    assertThat(response.getStatus()).isEqualTo("0000");

    List<BithumbUserTransactionResponse.BithumbUserTransaction> data = response.getData();
    assertThat(data.size() == 2);

    BithumbUserTransactionResponse.BithumbUserTransaction transaction = data.get(0);

    assertThat(transaction.getSearch()).isEqualTo("1");
    assertThat(transaction.getTransferDate()).isEqualTo(1572252297148997L);
    assertThat(transaction.getOrderCurrency()).isEqualTo("BTC");
    assertThat(transaction.getPaymentCurrency()).isEqualTo("KRW");
    assertThat(transaction.getUnits()).isEqualTo(new BigDecimal("0.0001"));
    assertThat(transaction.getPrice()).isEqualTo(BigDecimal.valueOf(10000000));
    assertThat(transaction.getAmount()).isEqualTo(BigDecimal.valueOf(1000));
    assertThat(transaction.getFee()).isEqualTo(BigDecimal.valueOf(2.5));
    assertThat(transaction.getFeeCurrency()).isEqualTo("KRW");
    assertThat(transaction.getOrderBalance()).isEqualTo(new BigDecimal("6.498881591872"));
    assertThat(transaction.getPaymentBalance()).isEqualTo(BigDecimal.valueOf(1140499718));
  }
}
