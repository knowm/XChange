package org.knowm.xchange.exx.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exx.EXXAuthenticated;
import org.knowm.xchange.exx.dto.account.EXXAccountInformation;
import org.knowm.xchange.exx.utils.CommonUtil;
import si.mazi.rescu.SynchronizedValueFactory;

public class EXXAccountServiceRaw extends EXXBaseService {
  private final EXXAuthenticated exxAuthenticated;
  private String apiKey;
  private String secretKey;
  private SynchronizedValueFactory<Long> nonceFactory;

  public EXXAccountServiceRaw(Exchange exchange) {
    super(exchange);
    this.exxAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                EXXAuthenticated.class, exchange.getExchangeSpecification())
            .build();

    this.apiKey = super.apiKey;
    this.secretKey = super.secretKey;
    this.nonceFactory = exchange.getNonceFactory();
  }

  /**
   * Obtain your own personal asset information.
   *
   * @return Object
   * @throws IOException
   */
  public EXXAccountInformation getExxAccountInfo() throws IOException {
    Long nonce = System.currentTimeMillis();
    String params = "accesskey=" + this.apiKey + "&nonce=" + nonce;
    String signature = CommonUtil.HmacSHA512(params, this.secretKey);

    return exxAuthenticated.getAccountInfo(this.apiKey, nonce, signature);
  }
}
