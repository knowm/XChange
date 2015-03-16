package com.xeiam.xchange.okcoin;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.okcoin.service.polling.OkCoinAccountService;
import com.xeiam.xchange.okcoin.service.polling.OkCoinFuturesAccountService;
import com.xeiam.xchange.okcoin.service.polling.OkCoinFuturesMarketDataService;
import com.xeiam.xchange.okcoin.service.polling.OkCoinFuturesTradeService;
import com.xeiam.xchange.okcoin.service.polling.OkCoinMarketDataService;
import com.xeiam.xchange.okcoin.service.polling.OkCoinTradeService;

public class OkCoinExchange extends BaseExchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Intl").equals(false)
        && exchangeSpecification.getExchangeSpecificParametersItem("Use_Futures").equals(true)) {
      throw new RuntimeException("Futures only available on international version. Set `Use_Intl` to true.");
    }

    if (exchangeSpecification.getExchangeSpecificParameters() != null
        && exchangeSpecification.getExchangeSpecificParametersItem("Use_Futures").equals(true)) {
      this.pollingMarketDataService = new OkCoinFuturesMarketDataService(this);
      if (exchangeSpecification.getApiKey() != null) {
        this.pollingAccountService = new OkCoinFuturesAccountService(this);
        this.pollingTradeService = new OkCoinFuturesTradeService(this);
      }
    } else {
      this.pollingMarketDataService = new OkCoinMarketDataService(this);
      if (exchangeSpecification.getApiKey() != null) {
        this.pollingAccountService = new OkCoinAccountService(this);
        this.pollingTradeService = new OkCoinTradeService(this);
      }
    }

    // set SSL URL and HOST accordingly

    if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Intl").equals(true)) {
      exchangeSpecification.setSslUri("https://www.okcoin.com/api");
      exchangeSpecification.setHost("www.okcoin.com");
    }

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

    return exchangeSpecification;
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
      return exchangeSpecification.getExchangeName().toLowerCase().replace(" ", "").replace("-", "").replace(".", "") + "_intl";

    }
  }
}