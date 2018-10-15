package org.knowm.xchange.bl3p.service;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bl3p.Bl3pAdapters;
import org.knowm.xchange.bl3p.dto.Bl3pUserTransactions;
import org.knowm.xchange.bl3p.dto.account.Bl3pAccountInfo;
import org.knowm.xchange.bl3p.dto.account.Bl3pNewDepositAddress;
import org.knowm.xchange.bl3p.dto.account.Bl3pWithdrawFunds;
import org.knowm.xchange.bl3p.service.params.Bl3pTradeHistoryParams;
import org.knowm.xchange.bl3p.service.params.Bl3pWithdrawFundsParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class Bl3pAccountService extends Bl3pBaseService implements AccountService {

  public Bl3pAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    Bl3pAccountInfo.Bl3pAccountInfoData accountInfo =
        this.bl3p.getAccountInfo(apiKey, signatureCreator, nonceFactory).getData();

    return new AccountInfo(
        "" + accountInfo.getUserId(),
        accountInfo.getTradeFee(),
        Bl3pAdapters.adaptBalances(accountInfo.getWallets()));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    Bl3pWithdrawFunds result;

    if (params instanceof Bl3pWithdrawFundsParams.Coins) {
      Bl3pWithdrawFundsParams.Coins coinParams = (Bl3pWithdrawFundsParams.Coins) params;

      result =
          this.bl3p.withdrawCoins(
              apiKey,
              signatureCreator,
              nonceFactory,
              coinParams.getCurrency(),
              coinParams.getAddress(),
              coinParams.isExtraFee() ? 1 : 0,
              coinParams.getAmount());
    } else if (params instanceof Bl3pWithdrawFundsParams.Euros) {
      Bl3pWithdrawFundsParams.Euros euroParams = (Bl3pWithdrawFundsParams.Euros) params;

      result =
          this.bl3p.withdrawEuros(
              apiKey,
              signatureCreator,
              nonceFactory,
              euroParams.getCurrency(),
              euroParams.getAccountId(),
              euroParams.getAccountName(),
              euroParams.getAmount());
    } else {
      throw new InvalidParameterException(
          "WithdrawFundsParams must be either Bl3pWithdrawFundsParams.Euros or Bl3pWithdrawFundsParams.Coins");
    }

    return Integer.toString(result.getData().id);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    Bl3pNewDepositAddress newDepositAddress =
        this.bl3p.createNewDepositAddress(
            apiKey, signatureCreator, nonceFactory, currency.getCurrencyCode());

    return newDepositAddress.getData().getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new Bl3pTradeHistoryParams(Currency.BTC, Bl3pTradeHistoryParams.TransactionType.DEPOSIT);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    if (!(params instanceof Bl3pTradeHistoryParams)) {
      params = this.createFundingHistoryParams();
    }

    Bl3pTradeHistoryParams bl3pParams = (Bl3pTradeHistoryParams) params;
    Bl3pUserTransactions transactions =
        this.bl3p.getUserTransactions(
            apiKey,
            signatureCreator,
            nonceFactory,
            bl3pParams.getCurrency().toString(),
            bl3pParams.getType().toString(),
            bl3pParams.getPageNumber(),
            bl3pParams.getPageLength());

    return Bl3pAdapters.adaptUserTransactionsToFundingRecords(transactions.getData().transactions);
  }
}
