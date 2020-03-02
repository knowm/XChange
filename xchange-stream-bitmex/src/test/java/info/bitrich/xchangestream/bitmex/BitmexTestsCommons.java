package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.util.LocalExchangeConfig;
import org.knowm.xchange.ExchangeSpecification;

/** @author Foat Akhmadeev 19/06/2018 */
public class BitmexTestsCommons {
  public static ExchangeSpecification getExchangeSpecification(
      LocalExchangeConfig localConfig, ExchangeSpecification defaultExchangeSpecification) {
    defaultExchangeSpecification.setExchangeSpecificParametersItem(
        StreamingExchange.USE_SANDBOX, true);
    defaultExchangeSpecification.setExchangeSpecificParametersItem(
        StreamingExchange.ACCEPT_ALL_CERITICATES, true);
    defaultExchangeSpecification.setExchangeSpecificParametersItem(
        StreamingExchange.ENABLE_LOGGING_HANDLER, true);

    defaultExchangeSpecification.setApiKey(localConfig.getApiKey());
    defaultExchangeSpecification.setSecretKey(localConfig.getSecretKey());
    //        defaultExchangeSpecification.setShouldLoadRemoteMetaData(true);
    return defaultExchangeSpecification;
  }
}
