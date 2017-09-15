package org.knowm.xchange.therock.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
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
import org.knowm.xchange.therock.TheRockAdapters;
import org.knowm.xchange.therock.dto.account.TheRockWithdrawalResponse;
import org.knowm.xchange.therock.dto.trade.TheRockTransaction;
import org.knowm.xchange.therock.dto.trade.TheRockTransactions;

/**
 * @author Matija Mazi
 */
public class TheRockAccountService extends TheRockAccountServiceRaw implements AccountService {

  public TheRockAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return TheRockAdapters.adaptAccountInfo(balances(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    final TheRockWithdrawalResponse response = withdrawDefault(currency, amount, address);
    return String.format("%d", response.getTransactionId());
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
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(
      TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Currency currency = null;
    if (params instanceof TradeHistoryParamCurrency) {
      TradeHistoryParamCurrency tradeHistoryParamCurrency = (TradeHistoryParamCurrency) params;
      currency = tradeHistoryParamCurrency.getCurrency();
    }

    List<FundingRecord> all = new ArrayList<>();

    int page = 1;
    while (true) {
      TheRockTransactions txns = deposits(currency, null, null, page++);
      if (txns.getTransactions().length == 0)
        break;

      for (TheRockTransaction txn : txns.getTransactions()) {
        all.add(adapt(txn, FundingRecord.Type.DEPOSIT));
      }
    }

    page = 1;
    while (true) {
      TheRockTransactions txns = withdrawls(currency, null, null, page++);
      if (txns.getTransactions().length == 0)
        break;

      for (TheRockTransaction txn : txns.getTransactions()) {
        all.add(adapt(txn, FundingRecord.Type.WITHDRAWAL));
      }
    }

    return all;
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
        null
    );
  }
}
