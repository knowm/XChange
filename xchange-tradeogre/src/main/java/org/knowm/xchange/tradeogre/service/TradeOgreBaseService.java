package org.knowm.xchange.tradeogre.service;

import java.util.Base64;

import org.knowm.xchange.client.ClientConfigCustomizer;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.tradeogre.TradeOgreAuthenticated;
import org.knowm.xchange.tradeogre.TradeOgreExchange;

import si.mazi.rescu.ClientConfigUtil;

public class TradeOgreBaseService extends BaseExchangeService<TradeOgreExchange> implements BaseService {

  protected final TradeOgreAuthenticated tradeOgre;
  protected String base64UserPwd;

  protected TradeOgreBaseService(TradeOgreExchange exchange) {

    super(exchange);

    String apiKey = exchange.getExchangeSpecification().getApiKey();
    String secretKey = exchange.getExchangeSpecification().getSecretKey();

    ClientConfigCustomizer clientConfigCustomizer =
        config -> ClientConfigUtil.addBasicAuthCredentials(config, apiKey, secretKey);
    tradeOgre =
        ExchangeRestProxyBuilder.forInterface(
                TradeOgreAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(clientConfigCustomizer)
            .build();
  }

  protected String getBase64UserPwd() {
    if (base64UserPwd == null) {
      String userPwd =
          exchange.getExchangeSpecification().getApiKey()
              + ":"
              + exchange.getExchangeSpecification().getSecretKey();
      base64UserPwd = "Basic " + new String(Base64.getEncoder().encode(userPwd.getBytes()));
    }
    return base64UserPwd;
  }
}
