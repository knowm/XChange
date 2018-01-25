package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.kucoin.dto.KucoinAdapters;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class KucoinAccountService extends KucoinAccountServiceRaw implements AccountService {

  public KucoinAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return KucoinAdapters.adaptAccountInfo(userInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    // TODO Auto-generated method stub
    return null;
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
