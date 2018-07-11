package org.knowm.xchange.coinbene.service;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbene.dto.account.CoinbeneCoinBalances;

public class CoinbeneAccountServiceRaw extends CoinbeneBaseService {

  protected CoinbeneAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CoinbeneCoinBalances getCoinbeneBalances(String account) throws IOException {
    Map<String, String> params = getCommonParams();
    params.put("account", account);

    return checkSuccess(coinbene.getBalance(formAndSignRequestJson(params)));
  }

  public CoinbeneCoinBalances getCoinbeneBalances() throws IOException {
    return getCoinbeneBalances("exchange");
  }
}
