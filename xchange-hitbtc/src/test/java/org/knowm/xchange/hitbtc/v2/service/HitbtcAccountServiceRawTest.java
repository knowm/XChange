package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.hitbtc.v2.BaseAuthenticatedServiceTest;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

/**
 * Test ignored in default build because it requires production authentication credentials. See {@link BaseAuthenticatedServiceTest}.
 */
@Ignore
public class HitbtcAccountServiceRawTest extends BaseAuthenticatedServiceTest {

  private HitbtcAccountServiceRaw service = (HitbtcAccountServiceRaw) exchange.getAccountService();


  @Test
  public void testGetWalletRaw() throws IOException {


    List<HitbtcBalance> balance = service.getWalletRaw();

    Map<Currency, HitbtcBalance> balanceMap = Maps.uniqueIndex(balance, new Function<HitbtcBalance, Currency>() {
      @Nullable
      @Override
      public Currency apply(@Nullable HitbtcBalance hitbtcBalance) {
        return Currency.getInstance(hitbtcBalance.getCurrency());
      }
    });

    Assert.assertNotNull(balance);
    BigDecimal expected = new BigDecimal("0.05000000");
    Assert.assertTrue( expected.equals(balanceMap.get(Currency.BTC).getAvailable()));
  }

  @Test
  public void testGetPaymentBalance() throws IOException {

    List<HitbtcBalance> response = service.getPaymentBalance();

    Assert.assertTrue(!response.isEmpty());
  }

}
