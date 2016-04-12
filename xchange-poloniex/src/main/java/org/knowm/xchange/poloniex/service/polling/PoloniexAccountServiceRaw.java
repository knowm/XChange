package org.knowm.xchange.poloniex.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.poloniex.PoloniexAdapters;
import org.knowm.xchange.poloniex.PoloniexException;
import org.knowm.xchange.poloniex.dto.account.PoloniexBalance;

/**
 * @author Zach Holmes
 */

public class PoloniexAccountServiceRaw extends PoloniexBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<Balance> getWallets() throws IOException {
    try {
      HashMap<String, PoloniexBalance> response = poloniexAuthenticated.returnCompleteBalances(apiKey, signatureCreator, exchange.getNonceFactory());
      return PoloniexAdapters.adaptPoloniexBalances(response);
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }
  }

  public String getDepositAddress(String currency) throws IOException {

    HashMap<String, String> response = poloniexAuthenticated.returnDepositAddresses(apiKey, signatureCreator, exchange.getNonceFactory());

    if (response.containsKey("error")) {
      throw new ExchangeException(response.get("error"));
    }
    if (response.containsKey(currency)) {
      return response.get(currency);
    } else {
      throw new ExchangeException("Poloniex did not return a deposit address for " + currency);
    }
  }

  /**
   * @param paymentId For XMR withdrawals, you may optionally specify "paymentId".
   */
  public String withdrawFunds(Currency currency, BigDecimal amount, String address, @Nullable String paymentId) throws IOException {
    return poloniexAuthenticated
        .withdraw(apiKey, signatureCreator, exchange.getNonceFactory(), currency.getCurrencyCode(), amount, address, paymentId).getResponse();
  }

}
