package org.knowm.xchange.kucoin;

import java.util.List;

import com.kucoin.sdk.rest.response.AccountBalancesResponse;

public class KucoinAccountServiceRaw extends KucoinBaseService {

  protected KucoinAccountServiceRaw(KucoinExchange exchange) {
    super(exchange);
  }

  public List<AccountBalancesResponse> getKucoinAccounts() {
    return kucoinRestClient.accountAPI().listAccounts(null, null);
  }
}