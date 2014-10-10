package com.xeiam.xchange.vaultofsatoshi.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiAdapters;

/**
 * @author veken0m
 */
public class VaultOfSatoshiAccountService extends VaultOfSatoshiAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   *          The {@link ExchangeSpecification}
   */
  public VaultOfSatoshiAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return VaultOfSatoshiAdapters.adaptAccountInfo(getAccount(), exchangeSpecification.getUserName());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    // TODO: implement withdraw
    throw new NotAvailableFromExchangeException();
  }

  /**
   * This returns the currently set deposit address. It will not generate a
   * new address (ie. repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(String currency, String... arguments) throws IOException {

    return getWalletAddress(currency).getWallet_address();

  }
}
