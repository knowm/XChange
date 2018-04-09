package org.knowm.xchange.liqui.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.liqui.LiquiAdapters;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class LiquiAccountService extends LiquiAccountServiceRaw implements AccountService {

  public LiquiAccountService(final Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return LiquiAdapters.adaptAccountInfo(getAccountInfoRaw());
  }

  @Override
  public String withdrawFunds(
      final Currency currency, final BigDecimal amount, final String address) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(final WithdrawFundsParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(final Currency currency, final String... args)
      throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(final TradeHistoryParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }
}
