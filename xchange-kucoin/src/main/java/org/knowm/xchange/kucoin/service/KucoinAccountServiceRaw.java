package org.knowm.xchange.kucoin.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.kucoin.dto.account.KucoinDepositAddressResponse;
import org.knowm.xchange.kucoin.dto.account.KucoinUserInfoResponse;

public class KucoinAccountServiceRaw extends KucoinBaseService {

  protected KucoinAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  KucoinUserInfoResponse userInfo() throws IOException {
    return kucoin.userInfo(apiKey, exchange.getNonceFactory(), signatureCreator);
  }
  
  KucoinDepositAddressResponse walletAddress(Currency cur) throws IOException {
    return kucoin.walletAddress(apiKey, exchange.getNonceFactory(), signatureCreator, cur.getCurrencyCode());
  }
}
