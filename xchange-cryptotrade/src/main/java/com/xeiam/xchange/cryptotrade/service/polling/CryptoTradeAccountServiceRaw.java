package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptotrade.CryptoTradeAuthenticated;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeException;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo;

public class CryptoTradeAccountServiceRaw extends CryptoTradeBasePollingService<CryptoTradeAuthenticated> {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoTradeAccountServiceRaw(Exchange exchange) {

    super(CryptoTradeAuthenticated.class, exchange);
  }

  public CryptoTradeAccountInfo getCryptoTradeAccountInfo() throws CryptoTradeException, IOException {

    CryptoTradeAccountInfo info = cryptoTradeProxy.getInfo(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return handleResponse(info);
  }

}
