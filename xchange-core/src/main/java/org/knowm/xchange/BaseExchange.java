package org.knowm.xchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseExchange implements Exchange {

  protected final Logger logger = LoggerFactory.getLogger(getClass());
  protected ExchangeSpecification exchangeSpecification;
  protected ExchangeMetaData exchangeMetaData;
  protected MarketDataService marketDataService;
  protected TradeService tradeService;
  protected AccountService accountService;

  protected abstract void initServices();

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
        exchangeSpecification.setExchangeSpecificParameters(
            defaultSpecification.getExchangeSpecificParameters());
      } else {
        // add default value unless it is overridden by current spec
        for (Map.Entry<String, Object> entry :
            defaultSpecification.getExchangeSpecificParameters().entrySet()) {
          if (exchangeSpecification.getExchangeSpecificParametersItem(entry.getKey()) == null) {
            exchangeSpecification.setExchangeSpecificParametersItem(
                entry.getKey(), entry.getValue());
          }
        }
      }

      this.exchangeSpecification = exchangeSpecification;
    }

    if (this.exchangeSpecification.getMetaDataJsonFileOverride()
        != null) { // load the metadata from the file system

      InputStream is = null;
      try {
        is = new FileInputStream(this.exchangeSpecification.getMetaDataJsonFileOverride());
        loadExchangeMetaData(is);
      } catch (FileNotFoundException e) {
        logger.warn(
            "An exception occured while loading the metadata file from the classpath. This is just a warning and can be ignored, but it may lead to unexpected results, so it's better to address it.",
            e);
      } finally {
        IOUtils.closeQuietly(is);
      }

    } else if (this.exchangeSpecification.getExchangeName()
        != null) { // load the metadata from the classpath

      InputStream is = null;
      try {
        is =
            BaseExchangeService.class
                .getClassLoader()
                .getResourceAsStream(getMetaDataFileName(exchangeSpecification) + ".json");
        loadExchangeMetaData(is);
      } finally {
        IOUtils.closeQuietly(is);
      }

    } else {
      logger.warn(
          "No \"exchange name\" found in the ExchangeSpecification. The name is used to load the meta data file from the classpath and may lead to unexpected results.");
    }

    initServices();

    if (this.exchangeSpecification.isShouldLoadRemoteMetaData()) {
      try {
        logger.info("Calling Remote Init...");
        remoteInit();
      } catch (IOException e) {
        throw new ExchangeException(e);
      }
    }
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {

    logger.info(
        "No remote initialization implemented for {}. The exchange meta data for this exchange is loaded from a json file containing hard-coded exchange meta-data. This may or may not be OK for you, and you should understand exactly how this works. Each exchange can either 1) rely on the hard-coded json file that comes packaged with XChange's jar, 2) provide your own override json file, 3) properly implement the `remoteInit()` method for the exchange (please submit a pull request so the whole community can benefit) or 4) a combination of hard-coded JSON and remote API calls. For more info see: https://github.com/timmolter/XChange/wiki/Design-Notes#exchange-metadata",
        exchangeSpecification.getExchangeName());
  }

  protected void loadExchangeMetaData(InputStream is) {

    exchangeMetaData = loadMetaData(is, ExchangeMetaData.class);
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

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return new ArrayList<>(getExchangeMetaData().getCurrencyPairs().keySet());
  }

  public String getMetaDataFileName(ExchangeSpecification exchangeSpecification) {

    return exchangeSpecification
        .getExchangeName()
        .toLowerCase()
        .replace(" ", "")
        .replace("-", "")
        .replace(".", "");
  }

  @Override
  public ExchangeSpecification getExchangeSpecification() {

    return exchangeSpecification;
  }

  @Override
  public ExchangeMetaData getExchangeMetaData() {

    return exchangeMetaData;
  }

  public MarketDataService getMarketDataService() {

    return marketDataService;
  }

  public TradeService getTradeService() {

    return tradeService;
  }

  public AccountService getAccountService() {

    return accountService;
  }

  @Override
  public String toString() {

    String name =
        exchangeSpecification != null
            ? exchangeSpecification.getExchangeName()
            : getClass().getName();
    return name + "#" + hashCode();
  }
}
