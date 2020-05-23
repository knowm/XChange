package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dsx.BaseAuthenticatedServiceTest;
import org.knowm.xchange.dsx.dto.DsxBalance;
import org.knowm.xchange.dsx.dto.DsxSort;
import org.knowm.xchange.dsx.dto.DsxTransaction;
import org.knowm.xchange.dsx.dto.DsxTransferType;
import si.mazi.rescu.HttpStatusIOException;

/**
 * Test ignored in default build because it requires production authentication credentials. See
 * {@link BaseAuthenticatedServiceTest}.
 */
@Ignore
public class DsxAccountServiceRawIntegration extends BaseAuthenticatedServiceTest {

  @Rule public final ExpectedException exception = ExpectedException.none();

  private DsxAccountServiceRaw service = (DsxAccountServiceRaw) exchange.getAccountService();

  @Test
  public void testGetMainBalance() throws IOException {

    List<DsxBalance> balance = service.getMainBalance();

    Map<Currency, DsxBalance> balanceMap = new HashMap<>();
    for (DsxBalance dsxBalance : balance) {
      balanceMap.put(Currency.getInstance(dsxBalance.getCurrency()), dsxBalance);
    }

    Assert.assertNotNull(balance);
    BigDecimal expected = new BigDecimal("0.00000000");
    Assert.assertTrue(expected.equals(balanceMap.get(Currency.BTC).getAvailable()));
  }

  @Test
  public void testGetTradingBalance() throws IOException {

    List<DsxBalance> balance = service.getTradingBalance();

    Map<Currency, DsxBalance> balanceMap = new HashMap<>();
    for (DsxBalance dsxBalance : balance) {
      balanceMap.put(Currency.getInstance(dsxBalance.getCurrency()), dsxBalance);
    }

    Assert.assertNotNull(balance);
    BigDecimal expected = new BigDecimal("0.040000000");
    Assert.assertTrue(expected.equals(balanceMap.get(Currency.BTC).getAvailable()));
  }

  @Test
  public void testGetPaymentBalance() throws IOException {

    List<DsxBalance> response = service.getMainBalance();

    Assert.assertTrue(!response.isEmpty());
  }

  @Test
  public void testGetDepositAddress() throws IOException {

    String response = service.getDepositAddress(Currency.BTC).getAddress();

    Assert.assertTrue(StringUtils.isNotEmpty(response));
  }

  @Test
  public void testGetTransactions() throws IOException {
    List<DsxTransaction> transactions;

    transactions =
        service.getTransactions(null, DsxSort.ASC, new Date(1520949577579L), new Date(), 100, null);
    Assert.assertTrue(!transactions.isEmpty());
    Assert.assertTrue(StringUtils.isNotEmpty(transactions.get(0).getId()));

    transactions =
        service.getTransactions(
            Currency.LTC.getCurrencyCode(), DsxSort.DESC, new Date(0), new Date(), 100, null);
    Assert.assertTrue(!transactions.isEmpty());
    Assert.assertTrue(StringUtils.isNotEmpty(transactions.get(0).getId()));

    transactions =
        service.getTransactions(
            Currency.LTC.getCurrencyCode(), DsxSort.DESC, new Date(0), new Date(), 100, null);
    Assert.assertTrue(!transactions.isEmpty());
    Assert.assertTrue(StringUtils.isNotEmpty(transactions.get(0).getId()));

    transactions = service.getTransactions(null, null, null);
    Assert.assertTrue(!transactions.isEmpty());
    Assert.assertTrue(StringUtils.isNotEmpty(transactions.get(0).getId()));

    transactions =
        service.getTransactions(
            Currency.LTC.getCurrencyCode(), null, new Date(0), new Date(), null, null);
    Assert.assertTrue(!transactions.isEmpty());
    Assert.assertTrue(StringUtils.isNotEmpty(transactions.get(0).getId()));

    transactions =
        service.getTransactions(
            Currency.LTC.getCurrencyCode(), null, 0L, Long.MAX_VALUE, null, null);
    Assert.assertTrue(!transactions.isEmpty());
    Assert.assertTrue(StringUtils.isNotEmpty(transactions.get(0).getId()));
  }

  // Should return {"error":{"code":20001,"message":"Insufficient funds","description":"Check that
  // the funds are sufficient, given commissions"}} --I'm poor
  @Test
  public void testTransferFunds() throws IOException {

    exception.expect(HttpStatusIOException.class);
    exception.expectMessage("HTTP status code was not OK: 400");
    service.transferFunds(Currency.USD, new BigDecimal("0.01"), DsxTransferType.BANK_TO_EXCHANGE);
  }
}
