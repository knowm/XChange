package com.xeiam.xchange.cexio.service.polling;

import java.io.IOException;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cexio.CexIOAuthenticated;
import com.xeiam.xchange.cexio.CexIOUtils;
import com.xeiam.xchange.cexio.dto.account.CexIOBalanceInfo;
import com.xeiam.xchange.cexio.service.CexIODigest;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

/**
 * @author timmolter
 */
public class CexIOAccountServiceRaw extends BasePollingExchangeService {

  private final CexIOAuthenticated cexIOAuthenticated;
  private ParamsDigest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CexIOAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.cexIOAuthenticated = RestProxyFactory.createProxy(CexIOAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = CexIODigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getUserName(), exchangeSpecification.getApiKey());
  }

  public CexIOBalanceInfo getCexIOAccountInfo() throws IOException {

    CexIOBalanceInfo info = cexIOAuthenticated.getBalance(exchangeSpecification.getApiKey(), signatureCreator, CexIOUtils.nextNonce());
    if (info.getError() != null) {
      throw new ExchangeException("Error getting balance. " + info.getError());
    }

    return info;
  }

}
