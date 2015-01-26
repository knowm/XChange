package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.lakebtc.LakeBTCAdapters;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * Created by cristian.lucaci on 12/19/2014.
 */
public class LakeBTCAccountService extends LakeBTCAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   * @param tonceFactory
   */
  public LakeBTCAccountService(Exchange exchange, SynchronizedValueFactory<Long> tonceFactory) {
    super(exchange, tonceFactory);
  }

  @Override
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    LakeBTCAccountInfoResponse response = super.getLakeBTCAccountInfo();
    return LakeBTCAdapters.adaptAccountInfo(response.getResult());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
