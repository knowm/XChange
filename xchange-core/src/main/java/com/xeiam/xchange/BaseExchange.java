package com.xeiam.xchange;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * <p>
 * Abstract base class to provide the following to {@link Exchange}s:
 * </p>
 * <ul>
 * <li>Access to common methods and fields</li>
 * </ul>
 */
public abstract class BaseExchange implements Exchange {

  protected ExchangeSpecification exchangeSpecification;

  protected PollingMarketDataService pollingMarketDataService;
  protected PollingTradeService pollingTradeService;
  protected PollingAccountService pollingAccountService;
  protected StreamingExchangeService streamingExchangeService;

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    ExchangeSpecification defaultSpecification = getDefaultExchangeSpecification();

    // Check if default is for everything
    if (exchangeSpecification == null) {
      this.exchangeSpecification = defaultSpecification;
    }
    else {
      // Using a configured exchange
      if (exchangeSpecification.getExchangeName() == null) {
        exchangeSpecification.setExchangeName(defaultSpecification.getExchangeName());
      }
      if (exchangeSpecification.getExchangeDescription() == null) {
        exchangeSpecification.setExchangeDescription(defaultSpecification.getExchangeDescription());
      }
      if (exchangeSpecification.getSslUri() == null) {
        exchangeSpecification.setSslUri(defaultSpecification.getSslUri());
      }
      if (exchangeSpecification.getHost() == null) {
        exchangeSpecification.setHost(defaultSpecification.getHost());
      }
      if (exchangeSpecification.getPlainTextUri() == null) {
        exchangeSpecification.setPlainTextUri(defaultSpecification.getPlainTextUri());
      }
      if (exchangeSpecification.getExchangeSpecificParameters() == null) {
        exchangeSpecification.setExchangeSpecificParameters(defaultSpecification.getExchangeSpecificParameters());
      }
      else {
        // add default value unless it is overriden by current spec
        for (Map.Entry<String, Object> entry : defaultSpecification.getExchangeSpecificParameters().entrySet()) {
          if (exchangeSpecification.getExchangeSpecificParametersItem(entry.getKey()) == null) {
            exchangeSpecification.setExchangeSpecificParametersItem(entry.getKey(), entry.getValue());
          }
        }
      }

      this.exchangeSpecification = exchangeSpecification;
    }

  }

  @Override
  public ExchangeSpecification getExchangeSpecification() {

    return exchangeSpecification;
  }

  @Override
  public PollingMarketDataService getPollingMarketDataService() {

    return pollingMarketDataService;
  }

  @Override
  public PollingTradeService getPollingTradeService() {

    return pollingTradeService;
  }

  @Override
  public PollingAccountService getPollingAccountService() {

    return pollingAccountService;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {

    return streamingExchangeService;
  }

  /**
   * Initialize the services if necessary. Implementations may call the remote service.
   *
   * @throws java.io.IOException                 - Indication that a networking error occurred while fetching JSON data
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   */
  @Override
  public void init() throws IOException, ExchangeException {
    // do nothing
  }

  @Override
  public String toString() {

    String name = exchangeSpecification != null ? exchangeSpecification.getExchangeName() : getClass().getName();
    return name + "#" + hashCode();
  }
}
