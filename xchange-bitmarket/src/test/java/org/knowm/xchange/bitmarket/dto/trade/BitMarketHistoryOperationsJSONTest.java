package org.knowm.xchange.bitmarket.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.bitmarket.dto.BitMarketDtoTestSupport;

/** @author kfonal */
public class BitMarketHistoryOperationsJSONTest extends BitMarketDtoTestSupport {

  @Test
  public void testUnmarshal() throws IOException {
    // when
    BitMarketHistoryOperationsResponse response =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-operations-data",
            BitMarketHistoryOperationsResponse.class);

    // then
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
