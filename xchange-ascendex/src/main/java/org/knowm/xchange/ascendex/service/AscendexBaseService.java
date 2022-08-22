package org.knowm.xchange.ascendex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ascendex.AscendexException;
import org.knowm.xchange.ascendex.IAscendex;
import org.knowm.xchange.ascendex.IAscendexAuthenticated;
import org.knowm.xchange.ascendex.dto.AscendexResponse;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ClientConfigUtil;
import si.mazi.rescu.ParamsDigest;

import java.net.Proxy;

/**
 * In order for Ascendex exchange authenticated endpoints to work you must add a
 * specificParameterItem named 'account-group' on the
 * getExchangeSpecification.getExchangeSpecificParameters Map
 */
public class AscendexBaseService extends BaseExchangeService implements BaseService {
  protected IAscendex ascendex;
  protected IAscendexAuthenticated ascendexAuthenticated;
  protected ParamsDigest signatureCreator;

  private static final Logger LOG = LoggerFactory.getLogger(AscendexBaseService.class);

  public AscendexBaseService(Exchange exchange) {
    super(exchange);
    ascendex =
        ExchangeRestProxyBuilder.forInterface(IAscendex.class, exchange.getExchangeSpecification())
            .build();
    if (exchange
        .getExchangeSpecification()
        .getExchangeSpecificParameters()
        .containsKey("account-group")) {
      ExchangeSpecification specWithAccountGroup = exchange.getDefaultExchangeSpecification();
      specWithAccountGroup.setSslUri(exchange.getExchangeSpecification().getSslUri());
      String proxyUsername =  getExchangeSpecificParametersItem("proxyUsername");
      String proxyPassword =  getExchangeSpecificParametersItem("proxyPassword");
      Proxy.Type proxyType = getExchangeSpecificParametersItem("proxyType");
    /*  not all  api  that auth need <account-group>
    like :/api/pro/v1/info  api/pro/data/v2/order/hist
    specWithAccountGroup.setSslUri(
          exchange.getExchangeSpecification().getSslUri()
              + exchange
                  .getExchangeSpecification()
                  .getExchangeSpecificParametersItem("account-group")
              + "/");*/
      ClientConfig clientConfig = ExchangeRestProxyBuilder.createClientConfig(exchange.getExchangeSpecification());
      clientConfig.setProxyType(proxyType);
      ClientConfig addPassword = ClientConfigUtil.addBasicAuthCredentials(clientConfig, proxyUsername, proxyPassword);
      ascendexAuthenticated =
          ExchangeRestProxyBuilder.forInterface(IAscendexAuthenticated.class, specWithAccountGroup)
                  .clientConfig(addPassword)
              .build();
    } else {
      LOG.warn(
          "Authenticated endpoints will not work because no 'account-group' specificParameter has been found.");
    }
    signatureCreator =
        AscendexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public <T> T getExchangeSpecificParametersItem(String key){
    try {
      return (T)exchange.getExchangeSpecification().getExchangeSpecificParametersItem(key);
    } catch (Exception e) {
    return null;
    }
  }
  public <R> R checkResult(AscendexResponse<R> response) throws AscendexException {
    if (response.getCode() == 0) {
      return response.getData();
    } else {
      throw new AscendexException(response.getCode(), response.getMessage());
    }
  }
}
