package org.knowm.xchange.bitso.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.BitsoAdapters;
import org.knowm.xchange.bitso.dto.account.BitsoDepositAddress;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

/**
 * @author Matija Mazi
 */
public class BitsoAccountService extends BitsoAccountServiceRaw implements AccountService {

  public BitsoAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(exchange.getExchangeSpecification().getUserName(), BitsoAdapters.adaptWallet(getBitsoBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    return withdrawBitsoFunds(amount, address);
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie. repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    final BitsoDepositAddress response = getBitsoBitcoinDepositAddress();
    return response.getDepositAddress();

  }
}
