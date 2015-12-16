package com.xeiam.xchange.btcmarkets.service.polling;

import org.junit.Test;

import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class BTCMarketsTradeServiceHistoryParamsTest {

  @Test
  public void shouldHoldPageLength() throws Exception {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.setPageLength(19);

    // then
    assertThat(historyParams.getPageLength()).isEqualTo(19);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldFailOnGetPageNumber() throws Exception {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.getPageNumber();

    // then
    fail("BTCMarketsTradeService.HistoryParams should throw UnsupportedOperationException when call getPageNumber");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldFailOnSetPageNumber() throws Exception {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.setPageNumber(1);

    // then
    fail("BTCMarketsTradeService.HistoryParams should throw UnsupportedOperationException when call setPageNumber");
  }

  @Test
  public void shouldHoldStartTime() throws Exception {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.setStartTime(new Date(1234567890L));

    // then
    assertThat(historyParams.getStartTime().getTime()).isEqualTo(1234567890L);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldFailOnGetEndTime() throws Exception {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.getEndTime();

    // then
    fail("BTCMarketsTradeService.HistoryParams should throw UnsupportedOperationException when call getEndTime");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldFailOnSetEndTime() throws Exception {
    // given
    BTCMarketsTradeService.HistoryParams historyParams = new BTCMarketsTradeService.HistoryParams();

    // when
    historyParams.setEndTime(new Date());

    // then
    fail("BTCMarketsTradeService.HistoryParams should throw UnsupportedOperationException when call setEndTime");
  }

}
