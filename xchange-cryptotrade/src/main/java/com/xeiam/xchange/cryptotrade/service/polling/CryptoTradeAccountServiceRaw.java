package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.CryptoTradeAuthenticated;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeException;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo;

public class CryptoTradeAccountServiceRaw extends CryptoTradeBasePollingService<CryptoTradeAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CryptoTradeAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(CryptoTradeAuthenticated.class, exchangeSpecification);
  }

  public CryptoTradeAccountInfo getCryptoTradeAccountInfo() throws CryptoTradeException, IOException {

    CryptoTradeAccountInfo info = cryptoTradeProxy.getInfo(exchangeSpecification.getApiKey(), signatureCreator, nextNonce());
    return handleResponse(info);
  }

}
