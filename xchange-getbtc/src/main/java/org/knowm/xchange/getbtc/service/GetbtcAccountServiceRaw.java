package org.knowm.xchange.getbtc.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.getbtc.GetbtcAuthenticated;
import org.knowm.xchange.getbtc.dto.account.GetbtcAccountInformation;
import org.knowm.xchange.getbtc.utils.RestSignUtil;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * kevinobamatheus@gmail.com
 *
 * @author kevingates
 */
public class GetbtcAccountServiceRaw extends GetbtcBaseService {
  private final GetbtcAuthenticated exxAuthenticated;
  private String apiKey;
  private String secretKey;
  private SynchronizedValueFactory<Long> nonceFactory;

  public GetbtcAccountServiceRaw(Exchange exchange) {
    super(exchange);
    this.exxAuthenticated =
        RestProxyFactory.createProxy(
            GetbtcAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    this.apiKey = super.apiKey;
    this.secretKey = super.secretKey;
    this.nonceFactory = exchange.getNonceFactory();
  }

  /**
   * Obtain your own personal asset information.
   *
   * @return Object
   * @throws IOException
   * @throws InvalidKeyException
   */
  public GetbtcAccountInformation getGetbtcAccountInfo() throws IOException {
    Map params = new HashMap();
    params.put("api_key", this.apiKey);
    params.put("nonce", System.currentTimeMillis());

    try {
      params.put("signature", RestSignUtil.getHmacSHA256(params, this.secretKey));
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return exxAuthenticated.getAccountInfo(params);
  }
}
