package org.knowm.xchange.btcmarkets.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;

class BTCMarketsTradeServiceHistoryParamsTest {

  @Test
  void shouldHoldPageLength() {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.setPageLength(19);

    // then
    assertThat(historyParams.getPageLength()).isEqualTo(19);
  }

  @Test
  void shouldFailOnGetPageNumber() {
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // then
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> historyParams.getPageNumber());
  }

  @Test
  void shouldFailOnSetPageNumber() {
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // then
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> historyParams.setPageNumber(1));
  }
}
