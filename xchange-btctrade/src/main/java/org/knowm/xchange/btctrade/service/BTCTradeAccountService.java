package org.knowm.xchange.btctrade.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btctrade.BTCTradeAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class BTCTradeAccountService extends BTCTradeAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCTradeAccountService(Exchange exchange) {

    super(exchange);
  }

  /** {@inheritDoc} */
  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(BTCTradeAdapters.adaptWallet(getBTCTradeBalance()));
  }

  /** {@inheritDoc} */
  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  /** {@inheritDoc} */
  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return BTCTradeAdapters.adaptDepositAddress(getBTCTradeWallet());
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
