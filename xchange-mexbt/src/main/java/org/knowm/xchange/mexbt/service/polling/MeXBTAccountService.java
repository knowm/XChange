package org.knowm.xchange.mexbt.service.polling;

import static org.knowm.xchange.mexbt.MeXBTAdapters.adaptWallet;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.mexbt.MeXBTAdapters;
import org.knowm.xchange.service.polling.account.PollingAccountService;

public class MeXBTAccountService extends MeXBTAccountServiceRaw implements PollingAccountService {

  public MeXBTAccountService(Exchange exchange) {
    super(exchange);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountInfo getAccountInfo() throws ExchangeException, IOException {
    return new AccountInfo(exchange.getExchangeSpecification().getUserName(), adaptWallet(getBalance()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws ExchangeException, IOException {
    withdraw(currency.toString(), amount, address);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String requestDepositAddress(Currency currency, String... args) throws ExchangeException, IOException {
    return MeXBTAdapters.getDepositAddress(getDepositAddresses(), currency.toString());
  }

}
