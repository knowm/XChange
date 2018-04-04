package org.xchange.coinegg.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.xchange.coinegg.CoinEggAdapters;

public class CoinEggAccountService extends CoinEggAccountServiceRaw implements AccountService {

  public CoinEggAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return CoinEggAdapters.adaptAccountInfo(getCoinEggBalance(), exchange);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }
}
