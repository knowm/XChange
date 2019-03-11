package org.knowm.xchange.btcmarkets.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class BTCMarketsTradeServiceHistoryParamsTest {

  @Test
  public void shouldHoldPageLength() {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.setPageLength(19);

    // then
    assertThat(historyParams.getPageLength()).isEqualTo(19);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldFailOnGetPageNumber() {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.getPageNumber();

    // then
    fail(
        "BTCMarketsTradeService.HistoryParams should throw UnsupportedOperationException when call getPageNumber");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldFailOnSetPageNumber() {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.setPageNumber(1);

    // then
    fail(
        "BTCMarketsTradeService.HistoryParams should throw UnsupportedOperationException when call setPageNumber");
  }
}
