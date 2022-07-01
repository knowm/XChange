package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dsx.DsxAdapters;
import org.knowm.xchange.dsx.dto.DsxTransaction;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.MoneroWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class DsxAccountService extends DsxAccountServiceRaw implements AccountService {

  public DsxAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(DsxAdapters.adaptWallet("Trading", getTradingBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof MoneroWithdrawFundsParams) {
      MoneroWithdrawFundsParams moneroWithdrawFundsParams = (MoneroWithdrawFundsParams) params;
      return withdrawFundsRaw(
          moneroWithdrawFundsParams.getCurrency(),
          moneroWithdrawFundsParams.getAmount(),
          moneroWithdrawFundsParams.getAddress(),
          moneroWithdrawFundsParams.getPaymentId());
    } else if (params instanceof RippleWithdrawFundsParams) {
      RippleWithdrawFundsParams rippleWithdrawFundsParams = (RippleWithdrawFundsParams) params;
      return withdrawFundsRaw(
          rippleWithdrawFundsParams.getCurrency(),
          rippleWithdrawFundsParams.getAmount(),
          rippleWithdrawFundsParams.getAddress(),
          rippleWithdrawFundsParams.getTag());
    } else if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFundsRaw(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress(), null);
    }

    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return getDepositAddress(currency).getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new DsxFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    List<DsxTransaction> transactions;
    String currencyCode = null;
    Integer limit = 1000;
    int offset = 0;

    if (params instanceof TradeHistoryParamCurrency) {
      Currency currency = ((TradeHistoryParamCurrency) params).getCurrency();
      currencyCode = currency != null ? currency.getCurrencyCode() : null;
    }

    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset().intValue();
    }

    transactions = getTransactions(currencyCode, limit, offset);

    List<FundingRecord> records = new ArrayList<>();
    for (DsxTransaction transaction : transactions) {
      records.add(DsxAdapters.adapt(transaction));
    }
    return records;
  }
}
