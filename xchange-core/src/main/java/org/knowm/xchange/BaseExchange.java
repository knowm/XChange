package org.knowm.xchange;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.account.PollingAccountService;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

public abstract class BaseExchange implements Exchange {

  protected abstract void initServices();

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected ExchangeSpecification exchangeSpecification;
  protected ExchangeMetaData metaData;

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
      if (exchangeSpecification.getSslUriStreaming() == null) {
        exchangeSpecification.setSslUriStreaming(defaultSpecification.getSslUriStreaming());
      }
      if (exchangeSpecification.getHost() == null) {
        exchangeSpecification.setHost(defaultSpecification.getHost());
      }
      if (exchangeSpecification.getPlainTextUri() == null) {
        exchangeSpecification.setPlainTextUri(defaultSpecification.getPlainTextUri());
      }
      if (exchangeSpecification.getPlainTextUriStreaming() == null) {
        exchangeSpecification.setPlainTextUriStreaming(defaultSpecification.getPlainTextUriStreaming());
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

    if (this.exchangeSpecification.getMetaDataJsonFileOverride() != null) { // load the metadata from the file system

      InputStream is = null;
      try {
        is = new FileInputStream(this.exchangeSpecification.getMetaDataJsonFileOverride());
        loadMetaData(is);
      } catch (FileNotFoundException e) {
        logger.warn(
            "An exception occured while loading the metadata file from the classpath. This is just a warning and can be ignored, but it may lead to unexpected results, so it's better to address it.",
            e);
      } finally {
        IOUtils.closeQuietly(is);
      }

    } else if (this.exchangeSpecification.getExchangeName() != null) { // load the metadata from the classpath

      InputStream is = null;
      try {
        is = BaseExchangeService.class.getClassLoader().getResourceAsStream(getMetaDataFileName(exchangeSpecification) + ".json");
        loadMetaData(is);
      } finally {
        IOUtils.closeQuietly(is);
      }

    } else {
      logger.warn(
          "No \"exchange name\" found in the ExchangeSpecification. The name is used to load the meta data file from the classpath and may lead to unexpected results.");
    }

    initServices();

  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    logger.debug("No remote initialization for {}", exchangeSpecification.getExchangeName());
  }

  protected void loadMetaData(InputStream is) {
    loadExchangeMetaData(is);
  }

  protected <T> T loadMetaData(InputStream is, Class<T> type) {
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    try {
      T result = mapper.readValue(is, type);
      logger.debug(result.toString());
      return result;
    } catch (Exception e) {
      logger.warn(
          "An exception occured while loading the metadata file from the file system. This is just a warning and can be ignored, but it may lead to unexpected results, so it's better to address it.",
          e);
      return null;
    }
  }

  protected void loadExchangeMetaData(InputStream is) {
    metaData = loadMetaData(is, ExchangeMetaData.class);
  }

  public String getMetaDataFileName(ExchangeSpecification exchangeSpecification) {

    return exchangeSpecification.getExchangeName().toLowerCase().replace(" ", "").replace("-", "").replace(".", "");
  }

  @Override
  public ExchangeSpecification getExchangeSpecification() {

    return exchangeSpecification;
  }

  @Override
  public ExchangeMetaData getMetaData() {
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
