package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.kraken.KrakenAuthenticated;
import com.xeiam.xchange.kraken.KrakenUtils;
import com.xeiam.xchange.kraken.dto.account.KrakenBalanceResult;
import com.xeiam.xchange.kraken.service.KrakenDigest;

public class KrakenAccountServiceRaw extends BaseKrakenService {

  private KrakenAuthenticated krakenAuthenticated;
  private ParamsDigest signatureCreator;

  public KrakenAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    krakenAuthenticated = RestProxyFactory.createProxy(KrakenAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = KrakenDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  public Map<String, BigDecimal> getKrakenBalance() throws IOException {

    KrakenBalanceResult balanceResult = krakenAuthenticated.balance(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());
    return checkResult(balanceResult);
  }
}
