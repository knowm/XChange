package org.knowm.xchange.mercadobitcoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinAdapters;
import org.knowm.xchange.service.polling.account.PollingAccountService;

/**
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinAccountService extends MercadoBitcoinAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public MercadoBitcoinAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return MercadoBitcoinAdapters.adaptAccountInfo(getMercadoBitcoinAccountInfo(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    throw new NotAvailableFromExchangeException();

  }
}
