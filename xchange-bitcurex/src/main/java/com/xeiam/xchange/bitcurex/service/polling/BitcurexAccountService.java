package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcurex.BitcurexAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class BitcurexAccountService extends BitcurexAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   * @throws IOException
   */
  // TODO look at this IOException
  public BitcurexAccountService(Exchange exchange) throws IOException {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return BitcurexAdapters.adaptAccountInfo(getFunds(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    return getFunds().getAddress();
  }

}
