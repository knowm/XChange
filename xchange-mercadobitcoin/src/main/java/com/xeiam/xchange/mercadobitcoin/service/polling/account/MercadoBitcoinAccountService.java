package com.xeiam.xchange.mercadobitcoin.service.polling.account;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinAdapters;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Matija Mazi
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinAccountService extends MercadoBitcoinAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchangeSpecification
   *          The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public MercadoBitcoinAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return MercadoBitcoinAdapters.adaptAccountInfo(getMercadoBitcoinAccountInfo(), exchangeSpecification.getUserName());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * This returns the currently set deposit address. It will not generate a
   * new address (ie. repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(String currency, String... arguments) throws IOException {

    throw new NotAvailableFromExchangeException();

  }
}
