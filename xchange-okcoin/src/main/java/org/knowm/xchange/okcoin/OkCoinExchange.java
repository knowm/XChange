package org.knowm.xchange.okcoin;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.okcoin.service.polling.OkCoinAccountService;
import org.knowm.xchange.okcoin.service.polling.OkCoinFuturesAccountService;
import org.knowm.xchange.okcoin.service.polling.OkCoinFuturesMarketDataService;
import org.knowm.xchange.okcoin.service.polling.OkCoinFuturesTradeService;
import org.knowm.xchange.okcoin.service.polling.OkCoinMarketDataService;
import org.knowm.xchange.okcoin.service.polling.OkCoinTradeService;
import org.knowm.xchange.okcoin.service.streaming.OkCoinStreamingExchangeService;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

import si.mazi.rescu.SynchronizedValueFactory;

public class OkCoinExchange extends BaseExchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Intl").equals(false)
        && exchangeSpecification.getExchangeSpecificParametersItem("Use_Futures").equals(true)) {
      throw new RuntimeException("Futures only available on international version. Set `Use_Intl` to true.");
    }

    // set SSL URL and HOST accordingly

    if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Intl").equals(true)) {
      exchangeSpecification.setSslUri("https://www.okcoin.com/api");
      exchangeSpecification.setHost("www.okcoin.com");
      exchangeSpecification.setExchangeSpecificParametersItem("Websocket_SslUri", "wss://real.okcoin.com:10440/websocket/okcoinapi");
    }
  }

  @Override
  protected void initServices() {
    if (exchangeSpecification.getExchangeSpecificParameters() != null
        && exchangeSpecification.getExchangeSpecificParametersItem("Use_Futures").equals(true)) {
      FuturesContract contract = futuresContractOfConfig(exchangeSpecification);

      this.pollingMarketDataService = new OkCoinFuturesMarketDataService(this, contract);
      if (exchangeSpecification.getApiKey() != null) {
        this.pollingAccountService = new OkCoinFuturesAccountService(this);
        this.pollingTradeService = new OkCoinFuturesTradeService(this, contract, futuresLeverageOfConfig(exchangeSpecification));
      }
    } else {
      this.pollingMarketDataService = new OkCoinMarketDataService(this);
      if (exchangeSpecification.getApiKey() != null) {
        this.pollingAccountService = new OkCoinAccountService(this);
        this.pollingTradeService = new OkCoinTradeService(this);
      }
    }
  }

  /** Extract futures leverage used by spec */
  private static int futuresLeverageOfConfig(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification.getExchangeSpecificParameters().containsKey("Futures_Leverage")) {
      return Integer.valueOf((String) exchangeSpecification.getExchangeSpecificParameters().get("Futures_Leverage"));
    } else {
      // default choice of 10x leverage is "safe" choice and default by OkCoin.
      return 10;
    }
  }

  /** Extract contract used by spec */
  public static FuturesContract futuresContractOfConfig(ExchangeSpecification exchangeSpecification) {
    FuturesContract contract;

    if (exchangeSpecification.getExchangeSpecificParameters().containsKey("Futures_Contract")) {
      contract = (FuturesContract) exchangeSpecification.getExchangeSpecificParameters().get("Futures_Contract");
    } else if (exchangeSpecification.getExchangeSpecificParameters().containsKey("Futures_Contract_String")) {
      contract = FuturesContract.valueOfIgnoreCase(FuturesContract.class,
          (String) exchangeSpecification.getExchangeSpecificParameters().get("Futures_Contract_String"));
    } else {
      throw new RuntimeException("`Futures_Contract` or `Futures_Contract_String` not defined in exchange specific parameters.");
    }

    return contract;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.okcoin.cn/api");
    exchangeSpecification.setHost("www.okcoin.cn");
    exchangeSpecification.setExchangeName("OKCoin");
    exchangeSpecification.setExchangeDescription("OKCoin is a globally oriented crypto-currency trading platform.");

    // set to true to automatically use the Intl_ parameters for ssluri and host
    exchangeSpecification.setExchangeSpecificParametersItem("Use_Intl", false);
    exchangeSpecification.setExchangeSpecificParametersItem("Use_Futures", false);

    exchangeSpecification.setExchangeSpecificParametersItem("Websocket_SslUri", "wss://real.okcoin.cn:10440/websocket/okcoinapi");

    return exchangeSpecification;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {
    return new OkCoinStreamingExchangeService(getExchangeSpecification(), configuration);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // This exchange doesn't use a nonce for authentication
    return null;
  }

  @Override
  public String getMetaDataFileName(ExchangeSpecification exchangeSpecification) {

    if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Intl").equals(false)) {
      return exchangeSpecification.getExchangeName().toLowerCase().replace(" ", "").replace("-", "").replace(".", "") + "_china";
    } else {
      if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Futures").equals(true)) {
        return exchangeSpecification.getExchangeName().toLowerCase().replace(" ", "").replace("-", "").replace(".", "") + "_futures";
      } else {
        return exchangeSpecification.getExchangeName().toLowerCase().replace(" ", "").replace("-", "").replace(".", "") + "_intl";
      }

    }
  }
}
