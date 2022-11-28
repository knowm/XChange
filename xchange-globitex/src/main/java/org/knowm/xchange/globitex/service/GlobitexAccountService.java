package org.knowm.xchange.globitex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.globitex.GlobitexAdapters;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class GlobitexAccountService extends GlobitexAccountServiceRaw implements AccountService {

  public GlobitexAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return GlobitexAdapters.adaptToAccountInfo(getGlobitexAccounts());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new TradeHistoryParamsAll();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) {
    throw new NotYetImplementedForExchangeException();
  }
}
