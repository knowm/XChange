package org.knowm.xchange.coinjar.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinjar.ExchangeUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class CoinjarAccountServiceIntegration {

  private Exchange exchange;

  public CoinjarAccountServiceIntegration() {
    exchange = ExchangeUtils.createExchangeFromProperties();
  }

  @Test
  void getAccountInfo() throws Exception {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
  }

  @Test
  void getTradeHistory() throws Exception {
    TradeHistoryParams tradeHistoryParams = exchange.getTradeService().createTradeHistoryParams();
    UserTrades userTrades = exchange.getTradeService().getTradeHistory(tradeHistoryParams);
    if (tradeHistoryParams instanceof TradeHistoryParamNextPageCursor) {
      ((TradeHistoryParamNextPageCursor) tradeHistoryParams)
          .setNextPageCursor(userTrades.getNextPageCursor());
      ;
    }
    userTrades = exchange.getTradeService().getTradeHistory(tradeHistoryParams);
  }

  @Test
  void getDepositAddress() throws Exception {
    String address = exchange.getAccountService().requestDepositAddress(Currency.BTC);
    assertThat(address).isNotNull();
  }
}
