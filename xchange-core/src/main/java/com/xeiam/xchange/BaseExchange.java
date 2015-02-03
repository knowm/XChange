package com.xeiam.xchange;

import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.dto.MetaData;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

public abstract class BaseExchange implements Exchange {

  private final Logger logger = LoggerFactory.getLogger(BaseExchange.class);

  protected ExchangeSpecification exchangeSpecification;
  protected MetaData metaData;

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
    } else {
      // Using a configured exchange
      // fill in null params with the default ones
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
      } else {
        // add default value unless it is overridden by current spec
        for (Map.Entry<String, Object> entry : defaultSpecification.getExchangeSpecificParameters().entrySet()) {
          if (exchangeSpecification.getExchangeSpecificParametersItem(entry.getKey()) == null) {
            exchangeSpecification.setExchangeSpecificParametersItem(entry.getKey(), entry.getValue());
          }
        }
      }

      this.exchangeSpecification = exchangeSpecification;
    }

    // load the metadata from the classpath
    if (this.exchangeSpecification.getExchangeName() != null) {

      InputStream is = BaseExchangeService.class.getClassLoader().getResourceAsStream(getMetaDataFileName(exchangeSpecification) + ".json");

      // Use Jackson to parse it
      ObjectMapper mapper = new ObjectMapper();

      try {
        metaData = mapper.readValue(is, MetaData.class);
        logger.debug(metaData.toString());
      } catch (Exception e) {
        logger.warn("An exception occured while loading the metadata file from the classpath. This may lead to unexpected results.", e);
      }
    } else {
      logger
          .warn("No \"exchange name\" found in the ExchangeSpecification. The name is used to load the meta data file from the classpath and may lead to unexpected results.");
    }

  }

  public String getMetaDataFileName(ExchangeSpecification exchangeSpecification) {

    return exchangeSpecification.getExchangeName().toLowerCase().replace(" ", "").replace("-", "").replace(".", "");
  }

  @Override
  public ExchangeSpecification getExchangeSpecification() {

    return exchangeSpecification;
  }

  @Override
  public MetaData getMetaData() {
    return metaData;
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

  @Override
  public String toString() {

    String name = exchangeSpecification != null ? exchangeSpecification.getExchangeName() : getClass().getName();
    return name + "#" + hashCode();
  }
}
