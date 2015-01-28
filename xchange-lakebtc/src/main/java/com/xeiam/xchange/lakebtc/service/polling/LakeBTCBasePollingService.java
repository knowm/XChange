package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.lakebtc.LakeBTC;
import com.xeiam.xchange.lakebtc.dto.LakeBTCResponse;
import com.xeiam.xchange.lakebtc.service.LakeBTCDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author kpysniak
 */
public class LakeBTCBasePollingService<T extends LakeBTC> extends BaseExchangeService implements BasePollingService {

  protected T btcLakeBTC;
  protected ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param type
   * @param exchange
   */
  public LakeBTCBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");

    this.btcLakeBTC = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = LakeBTCDigest.createInstance(exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification()
        .getSecretKey());
  }

  protected LakeBTCBasePollingService(Exchange exchange) {

    super(exchange);
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
