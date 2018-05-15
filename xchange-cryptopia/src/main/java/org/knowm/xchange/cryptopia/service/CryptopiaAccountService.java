package org.knowm.xchange.cryptopia.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;

public class CryptopiaAccountService extends CryptopiaAccountServiceRaw implements AccountService {
  public CryptopiaAccountService(CryptopiaExchange exchange) {
    super(exchange);
  }

  public enum CryptopiaType {
    Deposit,
    Withdraw
  }

  private static Integer MAX_FUNDING_RESULTS = 100;

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Balance> balances = getBalances();

    return new AccountInfo(new Wallet(balances));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultWithdrawFundsParams = (DefaultWithdrawFundsParams) params;
      return submitWithdraw(
          defaultWithdrawFundsParams.getCurrency(),
          defaultWithdrawFundsParams.getAmount(),
          defaultWithdrawFundsParams.getAddress(),
          null);
    } else {
      throw new IllegalStateException("Don't understand " + params);
    }
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return getDepositAddress(currency);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new CryptopiaFundingHistoryParams(null, 100);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    String cryptopiaType = "";
    Integer limit = MAX_FUNDING_RESULTS;

    if (params instanceof HistoryParamsFundingType) {
      final FundingRecord.Type type = ((HistoryParamsFundingType) params).getType();
      cryptopiaType =
          type == FundingRecord.Type.DEPOSIT
              ? CryptopiaType.Deposit.name()
              : type == FundingRecord.Type.WITHDRAWAL ? CryptopiaType.Withdraw.name() : null;
    }

    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    return getTransactions(cryptopiaType, limit);
  }

  public static class CryptopiaFundingHistoryParams
      implements TradeHistoryParams, HistoryParamsFundingType, TradeHistoryParamLimit {

    public FundingRecord.Type type;

    private Integer limit;

    public CryptopiaFundingHistoryParams(FundingRecord.Type type, Integer limit) {
      this.type = type;
      this.limit = limit;
    }

    @Override
    public FundingRecord.Type getType() {
      return type;
    }

    @Override
    public void setType(FundingRecord.Type type) {
      this.type = type;
    }

    @Override
    public Integer getLimit() {
      return limit;
    }

    @Override
    public void setLimit(Integer limit) {
      this.limit = limit;
    }
  }
}
