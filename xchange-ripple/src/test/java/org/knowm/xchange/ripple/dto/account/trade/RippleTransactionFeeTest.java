package org.knowm.xchange.ripple.dto.account.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ripple.dto.trade.RippleTransactionFee;

class RippleTransactionFeeTest {

  @Test
  void transactionFeeUnmarshalTest() throws Exception {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/trade/example-transaction-fee.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleTransactionFee response = mapper.readValue(is, RippleTransactionFee.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(response.isSuccess()).isEqualTo(true);
    assertThat(response.getFee()).isGreaterThan(BigDecimal.ZERO);
  }
}
