package org.knowm.xchange.therock.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.knowm.xchange.therock.TheRockAdapters;
import org.knowm.xchange.therock.dto.account.TheRockWithdrawalResponse;
import org.knowm.xchange.therock.dto.trade.TheRockTransaction;
import org.knowm.xchange.therock.dto.trade.TheRockTransactions;

/** @author Matija Mazi */
public class TheRockAccountService extends TheRockAccountServiceRaw implements AccountService {

  public TheRockAccountService(Exchange exchange) {
    super(exchange);
  }

  private static FundingRecord adapt(TheRockTransaction txn, FundingRecord.Type type) {
    TheRockTransaction.TransferDetail transferDetail = txn.getTransferDetail();

    String transferDetailId = null;
    String address = null;
    if (transferDetail != null) {
      transferDetailId = transferDetail.getId();
      address = transferDetail.getRecipient();
    }

    return new FundingRecord(
        address,
        txn.getDate(),
        Currency.getInstance(txn.getCurrency()),
        txn.getPrice(),
        String.valueOf(txn.getId()),
        transferDetailId,
        type,
        FundingRecord.Status.COMPLETE,
        null,
        null,
        null);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return TheRockAdapters.adaptAccountInfo(
        balances(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    final TheRockWithdrawalResponse response = withdrawDefault(currency, amount, address);
    return String.format("%d", response.getTransactionId());
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new TheRockFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    Currency currency = null;
    Date after = null;
    Date before = null;
    FundingRecord.Type type = null;

    if (params instanceof TradeHistoryParamCurrency) {
      TradeHistoryParamCurrency tradeHistoryParamCurrency = (TradeHistoryParamCurrency) params;
      currency = tradeHistoryParamCurrency.getCurrency();
    }

    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan tradeHistoryParamsTimeSpan = (TradeHistoryParamsTimeSpan) params;
      after = tradeHistoryParamsTimeSpan.getStartTime();
      before = tradeHistoryParamsTimeSpan.getEndTime();
    }

    if (params instanceof HistoryParamsFundingType) {
      HistoryParamsFundingType historyParamsFundingType = (HistoryParamsFundingType) params;
      type = historyParamsFundingType.getType();
    }

    List<FundingRecord> all = new ArrayList<>();

    if (type == null || type == FundingRecord.Type.DEPOSIT) {
      int page = 1;
      while (true) {
        TheRockTransactions txns = deposits(currency, after, before, page++);
        if (txns.getTransactions().length == 0) break;

        for (TheRockTransaction txn : txns.getTransactions()) {
          all.add(adapt(txn, FundingRecord.Type.DEPOSIT));
        }
      }
    }
    if (type == null || type == FundingRecord.Type.WITHDRAWAL) {
      int page = 1;
      while (true) {
        TheRockTransactions txns = withdrawls(currency, after, before, page++);
        if (txns.getTransactions().length == 0) break;

        for (TheRockTransaction txn : txns.getTransactions()) {
          all.add(adapt(txn, FundingRecord.Type.WITHDRAWAL));
        }
      }
    }
    return all;
  }
}
