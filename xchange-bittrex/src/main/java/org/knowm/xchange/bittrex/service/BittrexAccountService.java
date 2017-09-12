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
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
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
  public String withdrawFunds(WithdrawFundsParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
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
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    List<FundingRecord> res = new ArrayList<>();

    Currency currency = null;
    if (params instanceof TradeHistoryParamCurrency) {
      TradeHistoryParamCurrency tradeHistoryParamCurrency = (TradeHistoryParamCurrency) params;
      currency = tradeHistoryParamCurrency.getCurrency();
    }

    for (BittrexDepositHistory depositHistory : getDepositsHistory(currency)) {
      FundingRecord.Status status = FundingRecord.Status.COMPLETE;

      if (depositHistory.getTxId().equals("0x59f8c0cd28a55818ba32355d47aab5ba8bed6a5f941efb59303b796f66d72df2"))
        System.out.println();
      res.add(new FundingRecord(
          depositHistory.getCryptoAddress(),
          depositHistory.getLastUpdated(),
          Currency.getInstance(depositHistory.getCurrency()),
          depositHistory.getAmount(),
          String.valueOf(depositHistory.getId()),
          depositHistory.getTxId(),
          FundingRecord.Type.DEPOSIT,
          status,
          null,
          null,
          null
      ));
    }

    for (BittrexWithdrawalHistory withdrawalHistory : getWithdrawalsHistory(currency)) {
      FundingRecord.Status status = FundingRecord.Status.COMPLETE;
      if (withdrawalHistory.getCanceled())
        status = FundingRecord.Status.CANCELLED;
      else if (withdrawalHistory.getInvalidAddress())
        status = FundingRecord.Status.FAILED;
      else if (!withdrawalHistory.getAuthorized())
        status = FundingRecord.Status.PROCESSING;

      res.add(new FundingRecord(
          null,
          withdrawalHistory.getOpened(),
          Currency.getInstance(withdrawalHistory.getCurrency()),
          withdrawalHistory.getAmount(),
          null,
          withdrawalHistory.getTxId(),
          FundingRecord.Type.DEPOSIT,
          status,
          null,
          null,
          null
      ));
    }

    return res;
  }
}