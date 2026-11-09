package org.knowm.xchange.latoken.dto.exchangeinfo;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

class LatokenCurrencyTest {

  LatokenCurrency currency;

  @BeforeEach
  void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/exchangeinfo/latoken-currency-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    currency = mapper.readValue(is, LatokenCurrency.class);
  }

  @Test
  void latokenCurrency() {
    assertThat(currency).isNotNull();
  }

  @Test
  void getCurrencyId() {
    assertThat(currency.getCurrencyId()).isNotNull();
  }

  @Test
  void getSymbol() {
    assertThat(currency.getSymbol()).isNotNull();
  }

  @Test
  void getName() {
    assertThat(currency.getName()).isNotNull();
  }

  @Test
  void getPrecision() {
    assertThat(currency.getPrecision()).isNotNull();
  }

  @Test
  void getType() {
    assertThat(currency.getType()).isNotNull();
  }

  @Test
  void getFee() {
    assertThat(currency.getFee()).isNotNull();
  }

  @Test
  void testToString() {
    assertThat(currency.toString()).isNotNull();
  }
}
