package org.knowm.xchange.cryptopia.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.cryptopia.CryptopiaErrorAdapter;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.cryptopia.dto.CryptopiaException;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class CryptopiaAccountService extends CryptopiaAccountServiceRaw implements AccountService {

  public CryptopiaAccountService(CryptopiaExchange exchange) {
    super(exchange);
  }

  private static Integer DEFAULT_RESULTS_LIMIT = 100;

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new CryptopiaFundingHistoryParams(null, DEFAULT_RESULTS_LIMIT);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      List<Balance> balances = getBalances();
      return new AccountInfo(Wallet.Builder.from(balances).build());
    } catch (CryptopiaException e) {
      throw CryptopiaErrorAdapter.adapt(e);
    }
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    try {
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
    } catch (CryptopiaException e) {
      throw CryptopiaErrorAdapter.adapt(e);
    }
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return getDepositAddress(currency);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    try {
      String cryptopiaType = "";
      Integer limit = DEFAULT_RESULTS_LIMIT;

      if (params instanceof HistoryParamsFundingType) {
        final FundingRecord.Type type = ((HistoryParamsFundingType) params).getType();
        cryptopiaType =
            type == FundingRecord.Type.DEPOSIT
                ? CryptopiaFundingType.Deposit.name()
                : type == FundingRecord.Type.WITHDRAWAL
                    ? CryptopiaFundingType.Withdraw.name()
                    : null;
      }

      if (params instanceof TradeHistoryParamLimit) {
        limit = ((TradeHistoryParamLimit) params).getLimit();
      }

      return getTransactions(cryptopiaType, limit);
    } catch (CryptopiaException e) {
      throw CryptopiaErrorAdapter.adapt(e);
    }
  }

  public enum CryptopiaFundingType {
    Deposit,
    Withdraw
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
