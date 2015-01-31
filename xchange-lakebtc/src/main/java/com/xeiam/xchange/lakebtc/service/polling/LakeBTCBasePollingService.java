package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.lakebtc.LakeBTC;
import com.xeiam.xchange.lakebtc.LakeBTCAuthenticated;
import com.xeiam.xchange.lakebtc.dto.LakeBTCResponse;
import com.xeiam.xchange.lakebtc.service.LakeBTCDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author kpysniak
 */
public class LakeBTCBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final LakeBTCAuthenticated lakeBTCAuthenticated;
  protected final ParamsDigest signatureCreator;

  protected final LakeBTC lakeBTC;

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCBasePollingService(Exchange exchange) {

    super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");

    this.lakeBTCAuthenticated = RestProxyFactory.createProxy(LakeBTCAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = LakeBTCDigest.createInstance(exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification()
        .getSecretKey());

    this.lakeBTC = RestProxyFactory.createProxy(LakeBTC.class, exchange.getExchangeSpecification().getSslUri());

  }

  @SuppressWarnings("rawtypes")
  public static <T extends LakeBTCResponse> T checkResult(T returnObject) {

    if (returnObject.getResult() == null) {
      throw new ExchangeException("Null data returned");
    }
    return returnObject;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
