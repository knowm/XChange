package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.hitbtc.v2.BaseAuthenticatedServiceTest;

/**
 * Test ignored in default build because it requires production authentication credentials. See {@link BaseAuthenticatedServiceTest}.
 */
@Ignore
public class HitbtcAccountServiceIntegration extends BaseAuthenticatedServiceTest {

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

  @Test
  public void testGetFundingHistory() throws IOException {

    HitbtcFundingHistoryParams hitbtcTradeHistoryParams = HitbtcFundingHistoryParams.builder().build();

    List<FundingRecord> records = service.getFundingHistory(hitbtcTradeHistoryParams);

    Assert.assertTrue(!records.isEmpty());
  }

  @Test
  public void testGetFundingHistory_withParams() throws IOException {

    HitbtcFundingHistoryParams hitbtcTradeHistoryParams = HitbtcFundingHistoryParams.builder()
        .limit(2)
        .build();

    List<FundingRecord> records = service.getFundingHistory(hitbtcTradeHistoryParams);

    Assert.assertTrue(!records.isEmpty());
  }

}
