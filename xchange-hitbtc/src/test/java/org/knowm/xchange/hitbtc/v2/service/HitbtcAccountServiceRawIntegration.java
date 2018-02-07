package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.knowm.xchange.hitbtc.v2.BaseAuthenticatedServiceTest;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransaction;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransferType;

import si.mazi.rescu.HttpStatusIOException;

/**
 * Test ignored in default build because it requires production authentication credentials. See {@link BaseAuthenticatedServiceTest}.
 */
@Ignore
public class HitbtcAccountServiceRawIntegration extends BaseAuthenticatedServiceTest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  private HitbtcAccountServiceRaw service = (HitbtcAccountServiceRaw) exchange.getAccountService();

  @Test
  public void testGetMainBalance() throws IOException {

    List<HitbtcBalance> balance = service.getMainBalance();

    Map<Currency, HitbtcBalance> balanceMap = new HashMap<>();
    for (HitbtcBalance hitbtcBalance : balance) {
      balanceMap.put(Currency.getInstance(hitbtcBalance.getCurrency()), hitbtcBalance);
    }

    Assert.assertNotNull(balance);
    BigDecimal expected = new BigDecimal("0.00000000");
    Assert.assertTrue(expected.equals(balanceMap.get(Currency.BTC).getAvailable()));
  }

  @Test
  public void testGetTradingBalance() throws IOException {

    List<HitbtcBalance> balance = service.getTradingBalance();

    Map<Currency, HitbtcBalance> balanceMap = new HashMap<>();
    for (HitbtcBalance hitbtcBalance : balance) {
      balanceMap.put(Currency.getInstance(hitbtcBalance.getCurrency()), hitbtcBalance);
    }

    Assert.assertNotNull(balance);
    BigDecimal expected = new BigDecimal("0.040000000");
    Assert.assertTrue(expected.equals(balanceMap.get(Currency.BTC).getAvailable()));
  }

  @Test
  public void testGetPaymentBalance() throws IOException {

    List<HitbtcBalance> response = service.getMainBalance();

    Assert.assertTrue(!response.isEmpty());
  }

  @Test
  public void testGetDepositAddress() throws IOException {

    String response = service.getDepositAddress(Currency.BTC).getAddress();

    Assert.assertTrue(StringUtils.isNotEmpty(response));
  }

  @Test
  public void testGetTransactions() throws IOException {

    List<HitbtcTransaction> transactions = service.getTransactions(null, null, null);

    Assert.assertTrue(!transactions.isEmpty());
    Assert.assertTrue(StringUtils.isNotEmpty(transactions.get(0).getId()));
  }

  //Should return {"error":{"code":20001,"message":"Insufficient funds","description":"Check that the funds are sufficient, given commissions"}} --I'm poor
  @Test
  public void testTransferFunds() throws IOException {

    exception.expect(HttpStatusIOException.class);
    exception.expectMessage("HTTP status code was not OK: 400");
    service.transferFunds(Currency.USD, new BigDecimal("0.01"), HitbtcTransferType.BANK_TO_EXCHANGE);
  }

}
