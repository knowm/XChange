package org.knowm.xchange.bitstamp.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

/**
 * @author ujjwal on 08/02/18.
 */
class DepositAddressJSONTest {

  private BitstampDepositAddress unmarshall(String file) throws IOException {
    InputStream is = getClass().getResourceAsStream(file);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(is, BitstampDepositAddress.class);
  }

  @Test
  void error() throws Exception {
    BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-deposit-error.json");
    assertThat(address.getError()).isNotBlank();
    assertThat(address.getDepositAddress()).isNullOrEmpty();
  }

  @Test
  void v1DepositResponse() throws Exception {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }

  @Test
  void v2DepositResponse() throws Exception {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-v2-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }

  @Test
  void usdtDepositResponse() throws Exception {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-usdt-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }

  @Test
  void xlmDepositResponse() throws Exception {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-xlm-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }

  @Test
  void dogeDepositResponse() throws Exception {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-doge-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }

  @Test
  void usdcDepositResponse() throws Exception {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-usdc-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }

  @Test
  void linkDepositResponse() throws Exception {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-link-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }

  @Test
  void shibDepositResponse() throws Exception {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-shib-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }

  @Test
  void etcDepositResponse() throws Exception {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-etc-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }

  @Test
  void suiDepositResponse() throws Exception {
    final BitstampDepositAddress address =
        unmarshall("/org/knowm/xchange/bitstamp/dto/account/example-sui-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
  }

  @Test
  void multiChainDepositResponse() throws Exception {
    final BitstampDepositAddress address =
        unmarshall(
            "/org/knowm/xchange/bitstamp/dto/account/example-multichain-deposit-response.json");
    assertThat(address.getError()).isNullOrEmpty();
    assertThat(address.getDepositAddress()).isNotBlank();
    assertThat(address.getMemoId()).isEqualTo("299576079");
    assertThat(address.getDestinationTag()).isEqualTo(89473951L);
    assertThat(address.getTransferId()).isEqualTo(89473951L);
  }
}
