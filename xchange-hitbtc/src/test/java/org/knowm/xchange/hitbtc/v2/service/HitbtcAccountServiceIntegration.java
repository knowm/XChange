package org.knowm.xchange.hitbtc.v2.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.hitbtc.v2.BaseAuthenticatedServiceTest;

/**
 * Test ignored in default build because it requires production authentication credentials. See
 * {@link BaseAuthenticatedServiceTest}.
 */
@Disabled
class HitbtcAccountServiceIntegration extends BaseAuthenticatedServiceTest {

  private HitbtcAccountService service = (HitbtcAccountService) exchange.getAccountService();

  @Test
  void getAccountInfo() throws Exception {

    AccountInfo accountInfo = service.getAccountInfo();

    assertThat(accountInfo).isNotNull();
  }

  @Test
  void requestDepositAddress() throws Exception {

    String address = service.requestDepositAddress(Currency.BTC);

    assertThat(StringUtils.isNotEmpty(address)).isTrue();
  }

  @Test
  void getFundingHistory() throws Exception {

    HitbtcFundingHistoryParams hitbtcTradeHistoryParams =
        HitbtcFundingHistoryParams.builder().build();

    List<FundingRecord> records = service.getFundingHistory(hitbtcTradeHistoryParams);

    assertThat(records.isEmpty()).isFalse();
  }

  @Test
  void getFundingHistoryWithParams() throws Exception {

    HitbtcFundingHistoryParams hitbtcTradeHistoryParams =
        HitbtcFundingHistoryParams.builder().limit(2).build();

    List<FundingRecord> records = service.getFundingHistory(hitbtcTradeHistoryParams);

    assertThat(records.isEmpty()).isFalse();
  }
}
