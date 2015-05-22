package com.xeiam.xchange.coinbaseex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbaseex.CoinbaseEx;


public class CoinbaseExAccountServiceRaw extends CoinbaseExBasePollingService<CoinbaseEx> {

  public CoinbaseExAccountServiceRaw(Exchange exchange) {

    super(CoinbaseEx.class, exchange);
  }

  public void getCoinbaseExAccountInfo() throws IOException {
	  coinbaseEx.getAccounts(apiKey, digest, String.valueOf(getTimestamp()), passphrase);
	  
	  return;
  }
}
