package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.hitbtc.v2.HitbtcAdapters;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransaction;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class HitbtcAccountService extends HitbtcAccountServiceRaw implements AccountService {

  public HitbtcAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(HitbtcAdapters.adaptWallet("Main", getMainBalance()), HitbtcAdapters.adaptWallet("Trading", getTradingBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    return withdrawFundsRaw(currency, amount, address, null);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {

    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(defaultParams.currency, defaultParams.amount, defaultParams.address);
    }

    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return getDepositAddress(currency).getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    List<HitbtcTransaction> transactions;

    if (params instanceof TradeHistoryParams) {
      HitbtcFundingHistoryParams hitbtcTradeHistoryParams = (HitbtcFundingHistoryParams) params;

      String currency = hitbtcTradeHistoryParams.getCurrency() != null ? hitbtcTradeHistoryParams.getCurrency().getCurrencyCode() : null;

      transactions = getTransactions(currency, hitbtcTradeHistoryParams.getLimit(), hitbtcTradeHistoryParams.getOffset());
    }

    else {
      transactions = getTransactions(null, null, null);
    }

    List<FundingRecord> records = new ArrayList<>();
    for (HitbtcTransaction transaction : transactions) {
      records.add(HitbtcAdapters.adapt(transaction));
    }
    return records;
  }

}
