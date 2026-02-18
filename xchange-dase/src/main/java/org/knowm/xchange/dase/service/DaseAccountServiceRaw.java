package org.knowm.xchange.dase.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dase.dto.DaseApiException;
import org.knowm.xchange.dase.dto.account.ApiGetAccountTxnsOutput;
import org.knowm.xchange.dase.dto.account.DaseBalancesResponse;
import org.knowm.xchange.dase.dto.account.DaseSingleBalance;
import org.knowm.xchange.dase.dto.user.DaseUserProfile;

/** Raw access to authenticated DASE endpoints. */
public class DaseAccountServiceRaw extends DaseBaseService {

  public DaseAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public DaseUserProfile getUserProfile() throws IOException {
    ensureCredentialsPresent();
    try {
      return daseAuth.getUserProfile(apiKey, signatureCreator, timestampFactory.createValue());
    } catch (DaseApiException e) {
      throw e.toExchangeException();
    }
  }

  public ApiGetAccountTxnsOutput getAccountTransactions(Integer limit, String before)
      throws IOException {
    ensureCredentialsPresent();
    try {
      return daseAuth.getAccountTransactions(
          apiKey, signatureCreator, timestampFactory.createValue(), limit, before);
    } catch (DaseApiException e) {
      throw e.toExchangeException();
    }
  }

  public DaseBalancesResponse getDaseBalances() throws IOException {
    ensureCredentialsPresent();
    try {
      return daseAuth.getBalances(apiKey, signatureCreator, timestampFactory.createValue());
    } catch (DaseApiException e) {
      throw e.toExchangeException();
    }
  }

  public DaseSingleBalance getDaseBalance(String currency) throws IOException {
    ensureCredentialsPresent();
    try {
      return daseAuth.getBalance(
          currency, apiKey, signatureCreator, timestampFactory.createValue());
    } catch (DaseApiException e) {
      throw e.toExchangeException();
    }
  }
}
