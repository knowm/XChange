package org.knowm.xchange.btcmarkets.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

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
    fail("BTCMarketsTradeService.HistoryParams should throw UnsupportedOperationException when call getPageNumber");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldFailOnSetPageNumber() {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.setPageNumber(1);

    // then
    fail("BTCMarketsTradeService.HistoryParams should throw UnsupportedOperationException when call setPageNumber");
  }

  @Test
  public void shouldHoldStartTime() {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.setStartTime(new Date(1234567890L));

    // then
    assertThat(historyParams.getStartTime().getTime()).isEqualTo(1234567890L);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldFailOnGetEndTime() {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.getEndTime();

    // then
    fail("BTCMarketsTradeService.HistoryParams should throw UnsupportedOperationException when call getEndTime");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldFailOnSetEndTime() {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.setEndTime(new Date());

    // then
    fail("BTCMarketsTradeService.HistoryParams should throw UnsupportedOperationException when call setEndTime");
  }

}
