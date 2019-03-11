package org.knowm.xchange.ripple.dto.account.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.ripple.dto.trade.RippleUserTrade;
import org.knowm.xchange.ripple.service.params.RippleTradeHistoryParams;
import org.knowm.xchange.service.trade.TradeService;

public class RippleTradeHistoryIntegration {

  // The number of trades returned by this test depends on the recent trading activity of the
  // example
  // account. The important result is that is does not throw an exception, even if no trades are
  // found.

  @Test
  public void getTradeHistoryTest() throws Exception {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(RippleExchange.class.getName());
    final TradeService tradeService = exchange.getTradeService();

    final RippleTradeHistoryParams params =
        (RippleTradeHistoryParams) tradeService.createTradeHistoryParams();
    params.setPageLength(25);
    params.setTradeCountLimit(2);

    params.addPreferredBaseCurrency(Currency.BTC);
    params.addPreferredCounterCurrency(Currency.XRP);

    // An example address taken from https://www.ripplecharts.com/#/active_accounts
    // that was actively trading at the time this was last updated.
    params.setAccount("rwBYyfufTzk77zUSKEu4MvixfarC35av1J");

    // Only search for trades done in the last day
    final Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.DATE, -1);
    params.setStartTime(cal.getTime());

    final UserTrades trades = tradeService.getTradeHistory(params);
    System.out.println(
        String.format(
            "Found %d/%d trades with %d/%d API calls",
            params.getTradeCount(),
            params.getTradeCountLimit(),
            params.getApiCallCount(),
            params.getApiCallCountLimit()));
    for (final Trade trade : trades.getTrades()) {
      assertThat(trade).isInstanceOf(RippleUserTrade.class);
      System.out.println(trade);
    }

    // There are no guarantees that any trades will have been found
    // but make sure that at least one query has been run.
    assertThat(params.getApiCallCount()).isGreaterThan(0);
  }
}
