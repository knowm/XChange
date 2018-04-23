package org.knowm.xchange.kuna.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;

/** @author Dat Bui */
public class KunaAccountService extends KunaAccountServiceRaw implements AccountService {

  /**
   * Constructor.
   *
   * @param exchange
   */
  public KunaAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
