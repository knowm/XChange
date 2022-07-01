package org.knowm.xchange.latoken.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.latoken.LatokenAdapters;
import org.knowm.xchange.latoken.dto.LatokenException;
import org.knowm.xchange.latoken.dto.account.LatokenBalance;

public class LatokenAccountServiceRaw extends LatokenBaseService {

  public LatokenAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  /**
   * Returns all balances in the account.
   *
   * @return
   * @throws LatokenException
   * @throws IOException
   */
  public List<LatokenBalance> getLatokenBalances() throws LatokenException, IOException {

    return latoken.getBalances(System.currentTimeMillis(), apiKey, signatureCreator);
  }

  /**
   * Returns the balance of a given {@link Currency}.
   *
   * @param currency
   * @return
   * @throws LatokenException
   * @throws IOException
   */
  public LatokenBalance getLatokenBalance(Currency currency) throws LatokenException, IOException {

    return latoken.getBalance(
        LatokenAdapters.toSymbol(currency), System.currentTimeMillis(), apiKey, signatureCreator);
  }
}
