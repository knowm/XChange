package org.knowm.xchange.bitstamp.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;

public class WithdrawalFeesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WithdrawalFeesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/account/withdrawal-fees.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    List<WithdrawalFee> withdrawalFees =
        mapper.readValue(
            is, mapper.getTypeFactory().constructCollectionType(List.class, WithdrawalFee.class));

    assertThat(withdrawalFees.size()).isEqualTo(1);
    assertThat(withdrawalFees.get(0).getNetwork()).isEqualTo("bitcoin");
    assertThat(withdrawalFees.get(0).getCurrency().getCurrencyCode()).isEqualToIgnoringCase("btc");
    assertThat(withdrawalFees.get(0).getFee()).isEqualTo(new BigDecimal("0.00015000"));
  }
}
