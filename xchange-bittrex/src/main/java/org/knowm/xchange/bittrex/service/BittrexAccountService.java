package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.dto.account.BittrexDepositHistory;
import org.knowm.xchange.bittrex.dto.account.BittrexWithdrawalHistory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class BittrexAccountService extends BittrexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(BittrexAdapters.adaptWallet(getBittrexBalances()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    return withdraw(currency.getCurrencyCode(), amount, address, null);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof RippleWithdrawFundsParams) {
      RippleWithdrawFundsParams defaultParams = (RippleWithdrawFundsParams) params;
      return withdraw(defaultParams.currency.getCurrencyCode(), defaultParams.amount, defaultParams.address, defaultParams.tag);
    }
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(defaultParams.currency, defaultParams.amount, defaultParams.address);
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    return getBittrexDepositAddress(currency.toString());
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    List<FundingRecord> res = new ArrayList<>();

    Currency currency = null;
    if (params instanceof TradeHistoryParamCurrency) {
      currency = ((TradeHistoryParamCurrency) params).getCurrency();
    }

    List<BittrexDepositHistory> depositsHistory = getDepositsHistory(currency);
    for (BittrexDepositHistory depositHistory : depositsHistory) {
      res.add(new FundingRecord(
          depositHistory.getCryptoAddress(),
          depositHistory.getLastUpdated(),
          Currency.getInstance(depositHistory.getCurrency()),
          depositHistory.getAmount(),
          String.valueOf(depositHistory.getId()),
          depositHistory.getTxId(),
          FundingRecord.Type.DEPOSIT,
          FundingRecord.Status.COMPLETE,
          null,
          null,
          null
      ));
    }

    List<BittrexWithdrawalHistory> withdrawalsHistory = getWithdrawalsHistory(currency);
    for (BittrexWithdrawalHistory withdrawalHistory : withdrawalsHistory) {
      FundingRecord.Status status = FundingRecord.Status.COMPLETE;
      if (withdrawalHistory.getCanceled())
        status = FundingRecord.Status.CANCELLED;
      else if (withdrawalHistory.getInvalidAddress())
        status = FundingRecord.Status.FAILED;
      else if (!withdrawalHistory.getAuthorized())
        status = FundingRecord.Status.PROCESSING;

      if (withdrawalHistory.getCanceled())
        continue;
      if (withdrawalHistory.getInvalidAddress())
        continue;

      res.add(new FundingRecord(
          withdrawalHistory.getAddress(),
          withdrawalHistory.getOpened(),
          Currency.getInstance(withdrawalHistory.getCurrency()),
          withdrawalHistory.getAmount(),
          withdrawalHistory.getPaymentUuid(),
          withdrawalHistory.getTxId(),
          FundingRecord.Type.WITHDRAWAL,
          status,
          null,
          withdrawalHistory.getTxCost(),
          null
      ));
    }

    return res;
  }
}
