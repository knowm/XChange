package org.knowm.xchange.okcoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.service.polling.account.PollingAccountService;

public class OkCoinFuturesAccountService extends OkCoinAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinFuturesAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return OkCoinAdapters.adaptAccountInfoFutures(getFutureUserInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
