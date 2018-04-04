package org.knowm.xchange.abucoins.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAdapters;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoDepositRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoWithdrawalRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoDeposit;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoWithdrawal;
import org.knowm.xchange.abucoins.dto.account.AbucoinsDepositsHistory;
import org.knowm.xchange.abucoins.dto.account.AbucoinsWithdrawalsHistory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** Author: bryant_harris */
public class AbucoinsAccountService extends AbucoinsAccountServiceRaw implements AccountService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    AbucoinsAccount[] accounts = getAbucoinsAccounts();
    return AbucoinsAdapters.adaptAccountInfo(accounts);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    String method = abucoinsPaymentMethodForCurrency(currency.getCurrencyCode());
    AbucoinsCryptoDeposit cryptoDeposit =
        abucoinsDepositMake(new AbucoinsCryptoDepositRequest(currency.getCurrencyCode(), method));
    if (cryptoDeposit.getMessage() != null) throw new ExchangeException(cryptoDeposit.getMessage());

    return cryptoDeposit.getAddress();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defParams = (DefaultWithdrawFundsParams) params;

      String method = abucoinsPaymentMethodForCurrency(defParams.getCurrency().getCurrencyCode());
      AbucoinsCryptoWithdrawal withdrawal =
          abucoinsWithdrawalsMake(
              new AbucoinsCryptoWithdrawalRequest(
                  defParams.getAmount(),
                  defParams.getCurrency().getCurrencyCode(),
                  method,
                  defParams.getAddress(),
                  null));
      return withdrawal.getPayoutId();
    }

    if (params == null)
      throw new IllegalArgumentException(
          "Requires a DefaultWithdrawFundsParams object to describe the withdrawal");

    throw new IllegalArgumentException(
        "Abucoins only understands DefaultWithdrawFundsParams not " + params.getClass().getName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new AbucoinsTradeHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams param) throws IOException {
    AbucoinsDepositsHistory depositHistory = abucoinsDepositHistory();
    AbucoinsWithdrawalsHistory withdrawHistory = abucoinsWithdrawalsHistory();

    List<FundingRecord> retVal = new ArrayList<>();
    List<FundingRecord> some;
    some = AbucoinsAdapters.adaptFundingRecordsFromDepositsHistory(depositHistory);
    retVal.addAll(some);

    some = AbucoinsAdapters.adaptFundingRecords(withdrawHistory);
    retVal.addAll(some);

    // interleave the records based on time, newest first
    Collections.sort(
        retVal,
        (FundingRecord r1, FundingRecord r2) -> {
          return r2.getDate().compareTo(r1.getDate());
        });

    return retVal;
  }
}
