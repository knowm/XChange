package org.knowm.xchange.bitstamp.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** @author ujjwal on 08/02/18. */
public class DepositAddressJSONTest {

  private BitstampDepositAddress unmarshall(String file) throws IOException {
    InputStream is = getClass().getResourceAsStream(file);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(is, BitstampDepositAddress.class);
  }

  @Test
  public void testError() throws IOException {
    BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-deposit-error.json");
    assertThat(address.getError()).isNotBlank();
    assertThat(address.getDepositAddress()).isNullOrEmpty();
  }

  @Test
  public void testV1DepositResponse() throws IOException {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }

  @Test
  public void testV2DepositResponse() throws IOException {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-v2-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }
}
