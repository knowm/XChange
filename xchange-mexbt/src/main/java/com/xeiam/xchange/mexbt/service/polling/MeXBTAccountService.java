package com.xeiam.xchange.mexbt.service.polling;

import static com.xeiam.xchange.mexbt.MeXBTAdapters.adaptWallet;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.mexbt.MeXBTAdapters;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

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
