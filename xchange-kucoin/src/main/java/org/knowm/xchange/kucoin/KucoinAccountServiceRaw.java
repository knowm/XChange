package org.knowm.xchange.kucoin;

import static org.knowm.xchange.kucoin.KucoinExceptionClassifier.classifyingExceptions;

import java.io.IOException;
import java.util.List;

import com.kucoin.sdk.rest.response.AccountBalancesResponse;

public class KucoinAccountServiceRaw extends KucoinBaseService {

  protected KucoinAccountServiceRaw(KucoinExchange exchange) {
    super(exchange);
  }

  public List<AccountBalancesResponse> getKucoinAccounts() throws IOException {
    return classifyingExceptions(() ->
      kucoinRestClient.accountAPI().listAccounts(null, null));
  }
}