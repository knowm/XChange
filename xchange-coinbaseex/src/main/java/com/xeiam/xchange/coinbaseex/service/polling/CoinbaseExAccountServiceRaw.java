package com.xeiam.xchange.coinbaseex.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbaseex.CoinbaseEx;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProduct;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBook;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductStats;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductTicker;
import com.xeiam.xchange.currency.CurrencyPair;

import java.io.IOException;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class CoinbaseExAccountServiceRaw extends CoinbaseExBasePollingService<CoinbaseEx> {

  public CoinbaseExAccountServiceRaw(Exchange exchange) {

    super(CoinbaseEx.class, exchange);
  }

//  public CoinbaseExProductTicker getCoinbaseExProductTicker(CurrencyPair currencyPair) throws IOException {
//
//    if (!this.checkProductExists(currencyPair)) {
//      return null;
//    }
//
//    CoinbaseExProductTicker tickerReturn = this.coinbaseEx.getProductTicker(currencyPair.baseSymbol, currencyPair.counterSymbol);
//    return tickerReturn;
//  }
  
  public void getCoinbaseExAccountInfo() throws IOException {
	  coinbaseEx.getAccounts(apiKey, digest, String.valueOf(getTimestamp()), passphrase);
	  
	  return;
  }
  
  

}
