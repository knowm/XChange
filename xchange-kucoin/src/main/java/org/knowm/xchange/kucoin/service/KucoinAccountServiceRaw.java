package org.knowm.xchange.kucoin.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.kucoin.dto.account.KucoinUserInfoResponse;

public class KucoinAccountServiceRaw extends KucoinBaseService {

  protected KucoinAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  KucoinUserInfoResponse userInfo() throws IOException {
    return kucoin.userInfo(apiKey, exchange.getNonceFactory(), signatureCreator);
  }
}
