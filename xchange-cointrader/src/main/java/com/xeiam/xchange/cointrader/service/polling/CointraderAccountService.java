package com.xeiam.xchange.cointrader.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cointrader.CointraderAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * @author Matija Mazi
 */
public class CointraderAccountService extends CointraderAccountServiceRaw implements PollingAccountService {

  public CointraderAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return CointraderAdapters.adaptAccountInfo(getCointraderBalance(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... arguments) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
