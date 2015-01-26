package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btctrade.BTCTradeAdapters;
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

    return BTCTradeAdapters.adaptAccountInfo(getBTCTradeBalance());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException {

    return BTCTradeAdapters.adaptDepositAddress(getBTCTradeWallet());
  }

}
