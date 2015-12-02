package com.xeiam.xchange.mercadobitcoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinAdapters;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

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
