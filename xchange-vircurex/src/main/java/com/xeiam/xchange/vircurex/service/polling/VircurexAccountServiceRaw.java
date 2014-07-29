package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.vircurex.VircurexAuthenticated;
import com.xeiam.xchange.vircurex.VircurexUtils;
import com.xeiam.xchange.vircurex.dto.account.VircurexAccountInfoReturn;
import com.xeiam.xchange.vircurex.service.VircurexSha2Digest;

public class VircurexAccountServiceRaw extends VircurexBasePollingService {

  private VircurexAuthenticated vircurex;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public VircurexAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.vircurex = RestProxyFactory.createProxy(VircurexAuthenticated.class, exchangeSpecification.getSslUri());
  }

  public VircurexAccountInfoReturn getVircurexAccountInfo() throws IOException {

    String timestamp = VircurexUtils.getUtcTimestamp();
    String nonce = (System.currentTimeMillis() / 250L) + "";
    VircurexSha2Digest digest = new VircurexSha2Digest(exchangeSpecification.getApiKey(), exchangeSpecification.getUserName(), timestamp, nonce, "get_balances");
    VircurexAccountInfoReturn info = vircurex.getInfo(exchangeSpecification.getUserName(), nonce, digest.toString(), timestamp);
    return info;
  }
}
