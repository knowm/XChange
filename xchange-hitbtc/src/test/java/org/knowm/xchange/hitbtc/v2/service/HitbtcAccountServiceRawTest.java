package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.hitbtc.v2.BaseAuthenticatedServiceTest;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;

/**
 * Test ignored in default build because it requires production authentication credentials. See {@link BaseAuthenticatedServiceTest}.
 */
@Ignore
public class HitbtcAccountServiceRawTest extends BaseAuthenticatedServiceTest {


  @Test
  public void testGetWalletRaw() throws IOException {

    HitbtcAccountServiceRaw service = (HitbtcAccountServiceRaw) exchange.getAccountService();

    List<HitbtcBalance> balance = service.getWalletRaw();

    Assert.assertNotNull(balance);
  }

}
