package org.knowm.xchange.huobi.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.huobi.HuobiAdapters;
import org.knowm.xchange.service.polling.account.PollingAccountService;

public class HuobiAccountService extends HuobiAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HuobiAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(HuobiAdapters.adaptHuobiWallet(getHuobiAccountInfo()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) {
    throw new NotAvailableFromExchangeException();
  }

}
