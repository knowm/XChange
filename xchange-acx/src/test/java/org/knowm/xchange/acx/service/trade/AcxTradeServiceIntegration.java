package org.knowm.xchange.acx.service.trade;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.acx.ExchangeUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrades;

public class AcxTradeServiceIntegration {

  private Exchange exchange;

  public AcxTradeServiceIntegration() {
    exchange = ExchangeUtils.createExchangeFromProperties();
  }

  @Test
  public void testGetTradeHistory() throws IOException {
    AcxTradeService.AcxTradeHistoryParams tradeHistoryParams =
        (AcxTradeService.AcxTradeHistoryParams)
            exchange.getTradeService().createTradeHistoryParams();
    tradeHistoryParams.setCurrencyPair(CurrencyPair.BTC_AUD);
    tradeHistoryParams.setLimit(50);
    exchange.getTradeService().getTradeHistory(tradeHistoryParams);
  }

  @Test
  public void testGetTradeHistorySince() throws IOException {
    AcxTradeService.AcxTradeHistoryParams tradeHistoryParams =
        (AcxTradeService.AcxTradeHistoryParams)
            exchange.getTradeService().createTradeHistoryParams();
    tradeHistoryParams.setCurrencyPair(CurrencyPair.BTC_AUD);
    tradeHistoryParams.setLimit(50);
    UserTrades userTrades = exchange.getTradeService().getTradeHistory(tradeHistoryParams);
    tradeHistoryParams.setStartId(userTrades.getUserTrades().get(0).getId());
    exchange.getTradeService().getTradeHistory(tradeHistoryParams);
  }

  @Test
  public void testGetTradeHistoryUntil() throws IOException {
    AcxTradeService.AcxTradeHistoryParams tradeHistoryParams =
        (AcxTradeService.AcxTradeHistoryParams)
            exchange.getTradeService().createTradeHistoryParams();
    tradeHistoryParams.setCurrencyPair(CurrencyPair.BTC_AUD);
    tradeHistoryParams.setLimit(50);
    UserTrades userTrades = exchange.getTradeService().getTradeHistory(tradeHistoryParams);
    tradeHistoryParams.setEndId(userTrades.getUserTrades().get(10).getId());
    exchange.getTradeService().getTradeHistory(tradeHistoryParams);
  }
}
