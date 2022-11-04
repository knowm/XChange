package org.knowm.xchange.btcmarkets.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.ExchangeUtils;
import org.knowm.xchange.btcmarkets.dto.v3.BTCMarketsExceptionV3;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BTCMarketsAccountServiceIntegration {

  private Exchange exchange;

  public BTCMarketsAccountServiceIntegration() {
    exchange = ExchangeUtils.createExchangeFromProperties();
  }

  @Ignore("NotYetImplementedForExchangeException")
  @Test
  public void testGetAccountInfo() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
  }

  @Ignore("NotYetImplementedForExchangeException")
  @Test
  public void testGetTradeHistory() throws IOException {
    BTCMarketsTradeService.HistoryParams tradeHistoryParams =
        (BTCMarketsTradeService.HistoryParams)
            exchange.getTradeService().createTradeHistoryParams();
    tradeHistoryParams.setStartId("0");
    tradeHistoryParams.setCurrencyPair(CurrencyPair.BTC_AUD);
    exchange.getTradeService().getTradeHistory(tradeHistoryParams);
  }

  @Ignore("NotYetImplementedForExchangeException")
  @Test
  public void testGetDepositAddress() throws IOException {
    String address = exchange.getAccountService().requestDepositAddress(Currency.BTC);
    assertThat(address).isNotNull();
  }

  @Ignore("NotYetImplementedForExchangeException")
  @Test
  public void testPlaceInvalidOrderReturnsV3Error() throws IOException {
    LimitOrder order =
        new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_AUD)
            .limitPrice(BigDecimal.ONE)
            .originalAmount(new BigDecimal("-1"))
            .build();
    assertThatThrownBy(() -> exchange.getTradeService().placeLimitOrder(order))
        .isInstanceOf(BTCMarketsExceptionV3.class);
  }
}
