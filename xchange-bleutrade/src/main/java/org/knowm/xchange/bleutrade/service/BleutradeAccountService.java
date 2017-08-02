package org.knowm.xchange.bleutrade.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bleutrade.BleutradeAdapters;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalance;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BleutradeAccountService extends BleutradeAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    List<BleutradeBalance> bleutradeBalances = getBleutradeBalances();
    return new AccountInfo(BleutradeAdapters.adaptBleutradeBalances(bleutradeBalances));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdraw(currency, amount, address);
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
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return getBleutradeDepositAddress(currency.toString()).getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(
      TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
