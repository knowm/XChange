package org.knowm.xchange.bitmex;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BitmexAdaptersTest {

  @Test
  void values_transactStatus() {
    String[] exchangeValues = {"Completed", "Confirmed", "Pending", "Canceled"};

    assertThat(exchangeValues)
        .allSatisfy(
            value -> {
              assertThat(BitmexAdapters.toFundingRecordStatus(value)).isNotNull();
            });
  }
}
