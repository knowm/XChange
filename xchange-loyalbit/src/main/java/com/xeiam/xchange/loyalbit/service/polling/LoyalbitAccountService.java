package com.xeiam.xchange.loyalbit.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.loyalbit.LoyalbitAdapters;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * @author Matija Mazi
 */
public class LoyalbitAccountService extends LoyalbitAccountServiceRaw implements PollingAccountService {

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
