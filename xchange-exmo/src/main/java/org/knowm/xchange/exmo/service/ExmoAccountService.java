package org.knowm.xchange.exmo.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exmo.ExmoExchange;
import org.knowm.xchange.exmo.dto.account.ExmoFundingHistoryParams;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class ExmoAccountService extends ExmoAccountServiceRaw implements AccountService {
  public ExmoAccountService(ExmoExchange exmoExchange) {
    super(exmoExchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(new Wallet(balances()));
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return depositAddresses().get(currency.getCurrencyCode());
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    Map<String, Object> result;

    if (params instanceof RippleWithdrawFundsParams) {
      RippleWithdrawFundsParams rippleWithdrawFundsParams = (RippleWithdrawFundsParams) params;
      result =
          exmo.withdrawCrypt(
              signatureCreator,
              apiKey,
              exchange.getNonceFactory(),
              rippleWithdrawFundsParams.getAmount(),
              rippleWithdrawFundsParams.getCurrency().getCurrencyCode(),
              rippleWithdrawFundsParams.getAddress(),
              rippleWithdrawFundsParams.getTag());
    } else if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultWithdrawFundsParams = (DefaultWithdrawFundsParams) params;
      result =
          exmo.withdrawCrypt(
              signatureCreator,
              apiKey,
              exchange.getNonceFactory(),
              defaultWithdrawFundsParams.getAmount(),
              defaultWithdrawFundsParams.getCurrency().getCurrencyCode(),
              defaultWithdrawFundsParams.getAddress(),
              null);
    } else {
      throw new IllegalStateException("Don't understand " + params);
    }

    Boolean success = (Boolean) result.get("result");
    if (success) {
      return result.get("task_id").toString();
    } else {
      throw new ExchangeException("Withdrawal failed: " + result.get("error").toString());
    }
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    Date since = null;
    if (params instanceof ExmoFundingHistoryParams) {
      ExmoFundingHistoryParams thp = (ExmoFundingHistoryParams) params;
      since = thp.getDay();
    }
    return getFundingHistory(since);
  }
}
