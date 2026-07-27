package org.knowm.xchange.cryptocom.service.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.knowm.xchange.cryptocom.CryptoComExchangeIntegration;
import org.knowm.xchange.cryptocom.service.CryptoComAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled("Integration tests are disabled by default. Enable for manual execution against sandbox.")
public class AccountServiceIntegration extends CryptoComExchangeIntegration {

  private static final Logger logger = LoggerFactory.getLogger(AccountServiceIntegration.class);

  private CryptoComAccountService accountService;

  @BeforeAll
  @Override
  public void setUp() {
    super.setUp();
    accountService = (CryptoComAccountService) super.exchange.getAccountService();
  }

  @Test
  void requestDepositAddress_shouldReturnAddress() throws IOException {
    String address = accountService.requestDepositAddress(Currency.USDT);
    assertThat(address).isNotNull().isNotEmpty();
    logger.info("Deposit address for USDT: {}", address);
  }

  @Test
  void getFundingHistory_shouldReturnRecords() throws IOException {
    List<FundingRecord> records =
        accountService.getFundingHistory(accountService.createFundingHistoryParams());
    assertThat(records).isNotNull();
    logger.info("Funding history entries: {}", records.size());
  }
}
