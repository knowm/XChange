package org.knowm.xchange.loyalbit.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.loyalbit.LoyalbitAdapters;
import org.knowm.xchange.service.account.AccountService;

/**
 * @author Matija Mazi
 */
public class LoyalbitAccountService extends LoyalbitAccountServiceRaw implements AccountService {

  public LoyalbitAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return LoyalbitAdapters.adaptAccountInfo(getLoyalbitBalance(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie. repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
