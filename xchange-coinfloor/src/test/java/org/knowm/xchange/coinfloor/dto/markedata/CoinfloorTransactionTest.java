package org.knowm.xchange.coinfloor.dto.markedata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class CoinfloorTransactionTest {
  @Test
  public void unmarshalTest() throws IOException {
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/coinfloor/dto/marketdata/example-transactions.json");
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorTransaction[] transactions = mapper.readValue(is, CoinfloorTransaction[].class);

    assertThat(transactions.length).isEqualTo(58);
    assertThat(transactions[57].getDate()).isEqualTo(1489938414);
    assertThat(transactions[57].getTid()).isEqualTo(1489938414341179L);
    assertThat(transactions[57].getPrice()).isEqualTo("820.00");
    assertThat(transactions[57].getAmount()).isEqualTo("0.1280");
  }
}
