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
import si.mazi.rescu.ParamsDigest;

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
      specWithAccountGroup.setSslUri(
          exchange.getExchangeSpecification().getSslUri()
              + exchange
                  .getExchangeSpecification()
                  .getExchangeSpecificParametersItem("account-group")
              + "/");
      ascendexAuthenticated =
          ExchangeRestProxyBuilder.forInterface(IAscendexAuthenticated.class, specWithAccountGroup)
              .build();
    } else {
      LOG.warn(
          "Authenticated endpoints will not work because no 'account-group' specificParameter has been found.");
    }
    signatureCreator =
        AscendexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public <R> R checkResult(AscendexResponse<R> response) throws AscendexException {
    if (response.getCode() == 0) {
      return response.getData();
    } else {
      throw new AscendexException(response.getCode(), response.getMessage());
    }
  }
}
