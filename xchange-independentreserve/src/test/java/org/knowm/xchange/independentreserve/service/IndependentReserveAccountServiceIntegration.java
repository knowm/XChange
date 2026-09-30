package org.knowm.xchange.independentreserve.service;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.independentreserve.IndependentReserveExchange;
import org.knowm.xchange.utils.AuthUtils;

class IndependentReserveAccountServiceIntegration {

  static Exchange exchange;
  static IndependentReserveAccountService accountService;

  @BeforeAll
  static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(IndependentReserveExchange.class);
    AuthUtils.setApiAndSecretKey(exchange.getExchangeSpecification());
    exchange = ExchangeFactory.INSTANCE.createExchange(exchange.getExchangeSpecification());
    accountService = (IndependentReserveAccountService) exchange.getAccountService();
  }

  @Test
  void getOpenOrders() throws Exception {

    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    accountService.getAccountInfo();
  }

  @Test
  void getFudingHistoryWithGivenCurrency() throws Exception {
    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    IndependentReserveAccountService.IndependentReserveTradeHistoryParams params =
        (IndependentReserveAccountService.IndependentReserveTradeHistoryParams)
            accountService.createFundingHistoryParams();
    params.setCurrency(Currency.XBT);
    accountService.getFundingHistory(params);
  }

  @Test
  void getFudingHistoryWithoutCurrency() throws Exception {
    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    IndependentReserveAccountService.IndependentReserveTradeHistoryParams params =
        (IndependentReserveAccountService.IndependentReserveTradeHistoryParams)
            accountService.createFundingHistoryParams();
    accountService.getFundingHistory(params);
  }
}
