package org.knowm.xchange.cointrader.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cointrader.CointraderAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.account.PollingAccountService;

/**
 * @author Matija Mazi
 */
public class CointraderAccountService extends CointraderAccountServiceRaw implements PollingAccountService {

  public CointraderAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(exchange.getExchangeSpecification().getUserName(), CointraderAdapters.adaptWallet(getCointraderBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
