package org.knowm.xchange.bitfinex.v2.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class MovementTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        MovementTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitfinex/v2/dto/account/example_movements_data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    ArrayType constructCollectionType = mapper.getTypeFactory().constructArrayType(Movement.class);

    Movement[] movements = mapper.readValue(is, constructCollectionType);
    assertThat(movements).hasSize(2);

    assertThat(movements[0].getId()).isEqualTo("13105603");
    assertThat(movements[0].getCurency()).isEqualTo("ETH");
    assertThat(movements[0].getCurrencyName()).isEqualTo("ETHEREUM");
    assertThat(movements[0].getMtsStarted()).isEqualTo("2019-09-24T18:12:54Z");
    assertThat(movements[0].getMtsUpdated()).isEqualTo("2019-09-24T18:12:54Z");
    assertThat(movements[0].getStatus()).isEqualTo("COMPLETED");
    assertThat(movements[0].getAmount()).isEqualByComparingTo("0.26300954");
    assertThat(movements[0].getFees()).isEqualByComparingTo("-0.00135");
    assertThat(movements[0].getDestinationAddress()).isEqualTo("DESTINATION_ADDRESS");
    assertThat(movements[0].getTransactionId()).isEqualTo("TRANSACTION_ID");

    assertThat(movements[1].getId()).isEqualTo("13293039");
    assertThat(movements[1].getCurency()).isEqualTo("ETH");
    assertThat(movements[1].getCurrencyName()).isEqualTo("ETHEREUM");
    assertThat(movements[1].getMtsStarted()).isEqualTo("2019-11-19T14:50:52Z");
    assertThat(movements[1].getMtsUpdated()).isEqualTo("2019-11-19T16:35:26Z");
    assertThat(movements[1].getStatus()).isEqualTo("CANCELED");
    assertThat(movements[1].getAmount()).isEqualByComparingTo("-0.24");
    assertThat(movements[1].getFees()).isEqualByComparingTo("-0.00135");
    assertThat(movements[1].getDestinationAddress()).isEqualTo("DESTINATION_ADDRESS");
    assertThat(movements[1].getTransactionId()).isEqualTo("TRANSACTION_ID");
  }
}
