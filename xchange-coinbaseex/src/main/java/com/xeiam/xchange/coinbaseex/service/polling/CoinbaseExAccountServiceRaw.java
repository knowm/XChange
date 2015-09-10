package com.xeiam.xchange.coinbaseex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbaseex.CoinbaseEx;
import com.xeiam.xchange.coinbaseex.dto.account.CoinbaseExAccount;

public class CoinbaseExAccountServiceRaw extends CoinbaseExBasePollingService<CoinbaseEx> {

  public CoinbaseExAccountServiceRaw(Exchange exchange) {

    super(CoinbaseEx.class, exchange);
  }

  public CoinbaseExAccount[] getCoinbaseExAccountInfo() throws IOException {
    return coinbaseEx.getAccounts(apiKey, digest, getTimestamp(), passphrase);
  }
}
