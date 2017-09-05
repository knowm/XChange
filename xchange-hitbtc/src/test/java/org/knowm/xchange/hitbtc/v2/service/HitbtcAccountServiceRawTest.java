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
public class HitbtcAccountServiceRawTest extends BaseAuthenticatedServiceTest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  private HitbtcAccountServiceRaw service = (HitbtcAccountServiceRaw) exchange.getAccountService();

  @Test
  public void testGetWalletRaw() throws IOException {

    List<HitbtcBalance> balance = service.getWalletRaw();

    Map<Currency, HitbtcBalance> balanceMap = new HashMap<>();
    for (HitbtcBalance hitbtcBalance : balance) {
      balanceMap.put(Currency.getInstance(hitbtcBalance.getCurrency()), hitbtcBalance);
    }

    Assert.assertNotNull(balance);
    BigDecimal expected = new BigDecimal("0.05000000");
    Assert.assertTrue(expected.equals(balanceMap.get(Currency.BTC).getAvailable()));
  }

  @Test
  public void testGetPaymentBalance() throws IOException {

    List<HitbtcBalance> response = service.getPaymentBalance();

    Assert.assertTrue(!response.isEmpty());
  }

  @Test
  public void testGetDepositAddress() throws IOException {

    String response = service.getDepositAddress("BTC");

    Assert.assertTrue(StringUtils.isNotEmpty(response));
  }

  @Test
  public void testGetTransactions() throws IOException {

    List<HitbtcTransaction> transactions = service.getTransactions();

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