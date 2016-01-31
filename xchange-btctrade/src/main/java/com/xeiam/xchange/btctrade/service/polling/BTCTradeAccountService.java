package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btctrade.BTCTradeAdapters;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class BTCTradeAccountService extends BTCTradeAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCTradeAccountService(Exchange exchange) {

    super(exchange);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(BTCTradeAdapters.adaptWallet(getBTCTradeBalance()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return BTCTradeAdapters.adaptDepositAddress(getBTCTradeWallet());
  }

}
