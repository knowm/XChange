package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.hitbtc.v2.BaseAuthenticatedServiceTest;

/**
 * Test ignored in default build because it requires production authentication credentials. See {@link BaseAuthenticatedServiceTest}.
 */
@Ignore
public class HitbtcAccountServiceTest extends BaseAuthenticatedServiceTest {

  private HitbtcAccountService service = (HitbtcAccountService) exchange.getAccountService();

  @Test
  public void testGetAccountInfo() throws IOException {

    AccountInfo accountInfo = service.getAccountInfo();

    Assert.assertNotNull(accountInfo);
  }

  @Test
  public void testRequestDepositAddress() throws IOException {

    String address = service.requestDepositAddress(Currency.BTC);

    Assert.assertTrue(StringUtils.isNotEmpty(address));
  }

}
