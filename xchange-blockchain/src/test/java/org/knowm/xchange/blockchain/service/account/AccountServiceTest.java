package org.knowm.xchange.blockchain.service.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.ACCOUNT_INFORMATION_JSON;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.ADDRESS_DEPOSIT;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.BENEFICIARY;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.DEPOSIT_FAILURE_JSON;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.DEPOSIT_HISTORY_SUCCESS_JSON;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.DEPOSIT_SUCCESS_JSON;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.FEES_JSON;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.STATUS_CODE_400;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.STATUS_CODE_401;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.SYMBOL_JSON;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.URL_ACCOUNT;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.URL_DEPOSITS;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.URL_DEPOSIT_BY_CURRENCY;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.URL_FEES;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.URL_SYMBOLS;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.URL_WITHDRAWALS;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.WITHDRAWAL_FAILURE_JSON;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.WITHDRAWAL_HISTORY_SUCCESS_JSON;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.WITHDRAWAL_ID;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.WITHDRAWAL_SUCCESS_JSON;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.params.BlockchainWithdrawalParams;
import org.knowm.xchange.blockchain.service.BlockchainBaseTest;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

class AccountServiceTest extends BlockchainBaseTest {
  private AccountService service;

  @BeforeEach
  void init() {
    BlockchainExchange exchange = createExchange();
    service = exchange.getAccountService();
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void getAccountInfoSuccess() throws Exception {
    AccountInfo response = getAccountInfo();
    //        System.out.println(response);
    assertThat(response).isNotNull();
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void withdrawSuccess() throws Exception {
    String response = withdraw(WITHDRAWAL_SUCCESS_JSON, 200);
    assertThat(response).isEqualTo(WITHDRAWAL_ID);
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void withdrawFailure() {
    Throwable exception = catchThrowable(() -> withdraw(WITHDRAWAL_FAILURE_JSON, 401));
    assertThat(exception).isInstanceOf(ExchangeSecurityException.class).hasMessage(STATUS_CODE_401);
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void requestDepositAddressSuccess() throws Exception {
    String response = requestDeposit(DEPOSIT_SUCCESS_JSON, 200);
    assertThat(response).isEqualTo(ADDRESS_DEPOSIT);
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void requestDepositAddressFailure() {
    Throwable exception = catchThrowable(() -> requestDeposit(DEPOSIT_FAILURE_JSON, 400));
    assertThat(exception).isInstanceOf(ExchangeException.class).hasMessage(STATUS_CODE_400);
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void getWithdrawFundingHistorySuccess() throws Exception {
    List<FundingRecord> response = withdrawFundingHistory();
    assertThat(response).isNotNull();

    response.forEach(
        record -> assertThat(record.getAmount().compareTo(BigDecimal.ZERO) > 0).isTrue());
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void getDepositFundingHistorySuccess() throws Exception {
    List<FundingRecord> response = depositFundingHistory();
    assertThat(response).isNotNull();

    response.forEach(
        record -> assertThat(record.getAmount().compareTo(BigDecimal.ZERO) > 0).isTrue());
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void getDynamicTradingFeesSuccess() throws Exception {
    Map<Instrument, Fee> response = tradingFees();
    assertThat(response).isNotNull();
  }

  private AccountInfo getAccountInfo() throws IOException {
    stubGet(ACCOUNT_INFORMATION_JSON, 200, URL_ACCOUNT);

    return service.getAccountInfo();
  }

  private String withdraw(String responseFileName, int statusCode) throws IOException {
    stubPost(responseFileName, statusCode, URL_WITHDRAWALS);
    WithdrawFundsParams params =
        BlockchainWithdrawalParams.builder()
            .beneficiary(BENEFICIARY)
            .currency(Currency.BTC)
            .amount(BigDecimal.valueOf(0.0005))
            .sendMax(false)
            .build();

    return service.withdrawFunds(params);
  }

  private String requestDeposit(String responseFileName, int statusCode) throws IOException {
    stubPost(responseFileName, statusCode, URL_DEPOSIT_BY_CURRENCY);

    return service.requestDepositAddress(Currency.BTC);
  }

  private List<FundingRecord> withdrawFundingHistory() throws IOException {
    stubGet(WITHDRAWAL_HISTORY_SUCCESS_JSON, 200, URL_WITHDRAWALS);
    TradeHistoryParams params = service.createFundingHistoryParams();
    if (params instanceof HistoryParamsFundingType) {
      ((HistoryParamsFundingType) params).setType(FundingRecord.Type.WITHDRAWAL);
    }

    return service.getFundingHistory(params);
  }

  private List<FundingRecord> depositFundingHistory() throws IOException {
    stubGet(DEPOSIT_HISTORY_SUCCESS_JSON, 200, URL_DEPOSITS);
    TradeHistoryParams params = service.createFundingHistoryParams();
    if (params instanceof HistoryParamsFundingType) {
      ((HistoryParamsFundingType) params).setType(FundingRecord.Type.DEPOSIT);
    }

    return service.getFundingHistory(params);
  }

  private Map<Instrument, Fee> tradingFees() throws IOException {
    stubGet(FEES_JSON, 200, URL_FEES);
    stubGet(SYMBOL_JSON, 200, URL_SYMBOLS);

    return service.getDynamicTradingFeesByInstrument();
  }
}
