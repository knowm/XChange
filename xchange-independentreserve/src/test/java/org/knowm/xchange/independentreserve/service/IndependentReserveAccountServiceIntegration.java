package org.knowm.xchange.independentreserve.service;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.independentreserve.IndependentReserveExchange;
import org.knowm.xchange.utils.AuthUtils;

public class IndependentReserveAccountServiceIntegration {

  static Exchange exchange;
  static IndependentReserveAccountService accountService;

  @BeforeClass
  public static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(IndependentReserveExchange.class);
    AuthUtils.setApiAndSecretKey(exchange.getExchangeSpecification());
    exchange = ExchangeFactory.INSTANCE.createExchange(exchange.getExchangeSpecification());
    accountService = (IndependentReserveAccountService) exchange.getAccountService();
  }

  @Test
  public void testGetOpenOrders() throws Exception {

    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());

    accountService.getAccountInfo();
  }

  @Test
  public void getFudingHistoryWithGivenCurrency() throws Exception {
    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());

    IndependentReserveAccountService.IndependentReserveTradeHistoryParams params =
        (IndependentReserveAccountService.IndependentReserveTradeHistoryParams)
            accountService.createFundingHistoryParams();
    params.setCurrency(Currency.XBT);
    accountService.getFundingHistory(params);
  }

  @Test
  public void getFudingHistoryWithoutCurrency() throws Exception {
    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());

    IndependentReserveAccountService.IndependentReserveTradeHistoryParams params =
        (IndependentReserveAccountService.IndependentReserveTradeHistoryParams)
            accountService.createFundingHistoryParams();
    accountService.getFundingHistory(params);
  }
}
