package com.xeiam.xchange.quoine.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.quoine.QuoineAuthenticated;
import com.xeiam.xchange.quoine.QuoineExchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class QuoineBasePollingService extends BaseExchangeService implements BasePollingService {

  protected QuoineAuthenticated quoine;

  protected final String device;
  protected final String userID;
  protected final String userToken;

  /**
   * Constructor
   *
   * @param exchange
   */
  public QuoineBasePollingService(Exchange exchange) {

    super(exchange);

    quoine = RestProxyFactory.createProxy(QuoineAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    device = (String) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(QuoineExchange.KEY_DEVICE_NAME);
    userID = (String) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(QuoineExchange.KEY_USER_ID);
    userToken = (String) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(QuoineExchange.KEY_USER_TOKEN);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

  protected RuntimeException handleHttpError(HttpStatusIOException exception) throws IOException {
    throw new ExchangeException(exception.getHttpBody(), exception);
  }

}
