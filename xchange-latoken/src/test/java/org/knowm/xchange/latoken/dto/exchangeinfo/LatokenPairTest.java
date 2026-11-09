package org.knowm.xchange.latoken.dto.exchangeinfo;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

class LatokenPairTest {

  LatokenPair pair;

  @BeforeEach
  void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/exchangeinfo/latoken-pair-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    pair = mapper.readValue(is, LatokenPair.class);
  }

  @Test
  void latokenPair() {
    assertThat(pair).isNotNull();
  }

  @Test
  void getPairId() {
    assertThat(pair.getPairId()).isNotNull();
  }

  @Test
  void getSymbol() {
    assertThat(pair.getSymbol()).isNotNull();
  }

  @Test
  void getBaseCurrency() {
    assertThat(pair.getBaseCurrency()).isNotNull();
  }

  @Test
  void getCounterCurrency() {
    assertThat(pair.getCounterCurrency()).isNotNull();
  }

  @Test
  void getMakerFee() {
    assertThat(pair.getMakerFee()).isNotNull();
  }

  @Test
  void getTakerFee() {
    assertThat(pair.getTakerFee()).isNotNull();
  }

  @Test
  void getPricePrecision() {
    assertThat(pair.getPricePrecision()).isNotNull();
  }

  @Test
  void getAmountPrecision() {
    assertThat(pair.getAmountPrecision()).isNotNull();
  }

  @Test
  void getMinOrderAmount() {
    assertThat(pair.getMinOrderAmount()).isNotNull();
  }

  @Test
  void testToString() {
    assertThat(pair.toString()).isNotNull();
  }
}
