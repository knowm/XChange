package com.xeiam.xchange.bitcointoyou.service.polling.account;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * @author Matija Mazi
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouAccountService extends BitcoinToYouAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchangeSpecification
   *          The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitcoinToYouAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return BitcoinToYouAdapters.adaptAccountInfo(getBitcoinToYouBalance(), exchangeSpecification.getUserName());
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
