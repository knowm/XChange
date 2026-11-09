package org.knowm.xchange.latoken.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LatokenBalanceTest {

  LatokenBalance balance;

  @BeforeEach
  void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/account/latoken-balances-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<List<LatokenBalance>> readValues =
        mapper.readValue(is, new TypeReference<List<List<LatokenBalance>>>() {});
    assertThat(readValues.size()).isEqualTo(1);
    assertThat(readValues.get(0).size()).isEqualTo(1);

    balance = readValues.get(0).get(0);
  }

  @Test
  void latokenBalance() {
    assertThat(balance).isNotNull();
  }

  @Test
  void getCurrencyId() {
    assertThat(balance.getCurrencyId()).isNotNull();
  }

  @Test
  void getSymbol() {
    assertThat(balance.getSymbol()).isNotNull();
  }

  @Test
  void getName() {
    assertThat(balance.getName()).isNotNull();
  }

  @Test
  void getAmount() {
    assertThat(balance.getAmount()).isNotNull();
  }

  @Test
  void getAvailable() {
    assertThat(balance.getAvailable()).isNotNull();
  }

  @Test
  void getFrozen() {
    assertThat(balance.getFrozen()).isNotNull();
  }

  @Test
  void getPending() {
    assertThat(balance.getPending()).isNotNull();
  }

  @Test
  void testToString() {
    assertThat(balance.toString()).isNotNull();
  }
}
