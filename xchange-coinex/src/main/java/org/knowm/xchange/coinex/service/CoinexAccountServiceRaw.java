package org.knowm.xchange.coinex.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.coinex.dto.account.CoinexBalanceInfo;

public class CoinexAccountServiceRaw extends CoinexBaseService {

  public CoinexAccountServiceRaw(CoinexExchange exchange) {
    super(exchange);
  }

  public List<CoinexBalanceInfo> getCoinexBalances() throws IOException {
    return coinexAuthenticated.balances(apiKey, exchange.getNonceFactory(), coinexV2ParamsDigest).getData();
  }
}
