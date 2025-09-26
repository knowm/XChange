package org.knowm.xchange.coinsph.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CoinsphOrderBookEntryTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalOrderBookEntry() throws IOException {
    // given
    String json = "[\"5803095.5\",\"0.0000824\"]";

    // when
    CoinsphOrderBookEntry entry = objectMapper.readValue(json, CoinsphOrderBookEntry.class);

    // then
    assertThat(entry).isNotNull();
    assertThat(entry.getPrice()).isEqualByComparingTo(new BigDecimal("5803095.5"));
    assertThat(entry.getQuantity()).isEqualByComparingTo(new BigDecimal("0.0000824"));
  }

  @Test
  public void testConstructor() {
    // given
    List<BigDecimal> values =
        Arrays.asList(new BigDecimal("5803095.5"), new BigDecimal("0.0000824"));

    // when
    CoinsphOrderBookEntry entry = new CoinsphOrderBookEntry(values);

    // then
    assertThat(entry).isNotNull();
    assertThat(entry.getPrice()).isEqualByComparingTo(new BigDecimal("5803095.5"));
    assertThat(entry.getQuantity()).isEqualByComparingTo(new BigDecimal("0.0000824"));
  }

  @Test
  public void testConstructorWithInvalidList() {
    // given
    List<BigDecimal> tooShort = Arrays.asList(new BigDecimal("5803095.5"));
    List<BigDecimal> tooLong =
        Arrays.asList(
            new BigDecimal("5803095.5"), new BigDecimal("0.0000824"), new BigDecimal("123.456"));

    // then
    try {
      new CoinsphOrderBookEntry(tooShort);
      assertThat(false).isTrue(); // Should not reach here
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("must be a list of two BigDecimals");
    }

    try {
      new CoinsphOrderBookEntry(tooLong);
      assertThat(false).isTrue(); // Should not reach here
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("must be a list of two BigDecimals");
    }

    try {
      new CoinsphOrderBookEntry(null);
      assertThat(false).isTrue(); // Should not reach here
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("must be a list of two BigDecimals");
    }
  }
}
