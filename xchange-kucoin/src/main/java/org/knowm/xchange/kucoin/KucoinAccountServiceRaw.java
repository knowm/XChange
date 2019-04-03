package org.knowm.xchange.kucoin;

import static org.knowm.xchange.kucoin.KucoinExceptionClassifier.classifyingExceptions;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.kucoin.dto.response.AccountBalancesResponse;

public class KucoinAccountServiceRaw extends KucoinBaseService {

  protected KucoinAccountServiceRaw(KucoinExchange exchange) {
    super(exchange);
  }

  public List<AccountBalancesResponse> getKucoinAccounts() throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () -> accountApi.getAccountList(apiKey, digest, nonceFactory, passphrase, null, null));
  }
}
