package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptotrade.CryptoTradeAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class CryptoTradeAccountService extends CryptoTradeAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoTradeAccountService(Exchange exchange) {

    super(exchange);

  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return CryptoTradeAdapters.adaptAccountInfo(exchange.getExchangeSpecification().getUserName(), getCryptoTradeAccountInfo());
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
