package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kucoin.dto.KucoinAdapters;
import org.knowm.xchange.kucoin.dto.account.KucoinCoinBalance;
import org.knowm.xchange.kucoin.dto.account.KucoinCoinBalances;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class KucoinAccountService extends KucoinAccountServiceRaw implements AccountService {

  public KucoinAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    // Kucoins balances are available only in paged form
    // We load the first page, then loop over the remaining pages
    List<KucoinCoinBalance> balances = new LinkedList<>();
    // 20 is the maximum page size
    KucoinCoinBalances balancesInfo = accountBalances(20, 1).getData();
    balances.addAll(balancesInfo.getBalances());
    for (int page = 2; page < balancesInfo.getPageNos(); page++) {
      balances.addAll(accountBalances(20, page).getData().getBalances());
    }
    return KucoinAdapters.adaptAccountInfo(balances);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (!(params instanceof DefaultWithdrawFundsParams)) {
      throw new ExchangeException("Need DefaultWithdrawFundsParams to apply for withdrawal!");
    }
    DefaultWithdrawFundsParams defParams = (DefaultWithdrawFundsParams) params;
    return withdrawalApply(defParams.currency, defParams.amount, defParams.address).getCode();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return walletAddress(currency).getDepositAddress().getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

}
