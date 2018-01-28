package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.kucoin.dto.KucoinSimpleResponse;
import org.knowm.xchange.kucoin.dto.account.KucoinCoinBalances;
import org.knowm.xchange.kucoin.dto.account.KucoinDepositAddressResponse;

public class KucoinAccountServiceRaw extends KucoinBaseService {

  protected KucoinAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  KucoinSimpleResponse<KucoinCoinBalances> accountBalances(Integer limit, Integer page) throws IOException {
    return kucoin.accountBalances(apiKey, exchange.getNonceFactory(), signatureCreator, limit, page);
  }
  
  KucoinDepositAddressResponse walletAddress(Currency cur) throws IOException {
    return kucoin.walletAddress(apiKey, exchange.getNonceFactory(), signatureCreator, cur.getCurrencyCode());
  }
  
  KucoinSimpleResponse<Object> withdrawalApply(Currency cur, BigDecimal amount, String address)
      throws IOException {
    return kucoin.withdrawalApply(apiKey, exchange.getNonceFactory(), signatureCreator,
        cur.getCurrencyCode(), amount, address);
  }
}
