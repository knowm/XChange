package org.knowm.xchange.huobi;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.huobi.service.polling.BitVcAccountService;
import org.knowm.xchange.huobi.service.polling.BitVcFuturesMarketDataService;
import org.knowm.xchange.huobi.service.polling.BitVcTradeServiceRaw;
import org.knowm.xchange.huobi.service.polling.GenericTradeService;
import org.knowm.xchange.huobi.service.polling.HuobiAccountService;
import org.knowm.xchange.huobi.service.polling.HuobiMarketDataService;
import org.knowm.xchange.huobi.service.polling.HuobiTradeServiceRaw;
import org.knowm.xchange.huobi.service.streaming.HuobiStreamingExchangeService;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * By default when instantiating this exchange market data and trading goes via Huobi. If the flag HuobiExchange.HUOBI_MARKET_DATA is true then market
 * data will go through Huobi still and trade execution and account information will go through BitVC.
 */
public class HuobiExchange extends BaseExchange implements Exchange {

  public static final String TRADE_PASSWORD_PARAMETER = "trade_password";

  /** Potentially different market data endpoints should be settable */
  public static final String HUOBI_MARKET_DATA = "huobi_uri_marketdata";
  public static final String USE_BITVC = "use_bitvc";
  public static final String USE_BITVC_FUTURES = "use_bitvc_futures";

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    if (exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC).equals(true)) {
      exchangeSpecification.setSslUri("https://api.bitvc.com");
      exchangeSpecification.setExchangeSpecificParametersItem("Websocket_SslUri", "NOT IMPLEMENTED");
    }
  }

  @Override
  protected void initServices() {
    if (exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC).equals(true)
        && exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC_FUTURES).equals(true)) {
      FuturesContract contract = futuresContractOfConfig(exchangeSpecification);

      pollingMarketDataService = new BitVcFuturesMarketDataService(this, contract);
    } else {
      pollingMarketDataService = new HuobiMarketDataService(this);
    }

    if (exchangeSpecification.getApiKey() != null) {
      if ((Boolean) exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC)) {
        pollingAccountService = new BitVcAccountService(this);
        pollingTradeService = new GenericTradeService(this, new BitVcTradeServiceRaw(this));

      } else {
        pollingAccountService = new HuobiAccountService(this);
        pollingTradeService = new GenericTradeService(this, new HuobiTradeServiceRaw(this));

      }
    }
  }

  private static FuturesContract futuresContractOfConfig(ExchangeSpecification exchangeSpecification) {
    FuturesContract contract;

    if (exchangeSpecification.getExchangeSpecificParameters().containsKey("Futures_Contract")) {
      contract = (FuturesContract) exchangeSpecification.getExchangeSpecificParameters().get("Futures_Contract");
    } else if (exchangeSpecification.getExchangeSpecificParameters().containsKey("Futures_Contract_String")) {
      contract = FuturesContract.valueOf((String) exchangeSpecification.getExchangeSpecificParameters().get("Futures_Contract_String"));
    } else {
      throw new RuntimeException("`Futures_Contract` or `Futures_Contract_String` not defined in exchange specific parameters.");
    }

    return contract;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(getClass());
    spec.setExchangeName("Huobi");
    spec.setExchangeDescription("Huobi-Family Exchange (Huobi, BitVC, BitVC Futures)");

    /* by default we request market data from huobi and execute on bitvc */
    spec.setPlainTextUri("http://market.huobi.com/staticmarket");
    spec.setSslUri("https://api.huobi.com/apiv3");

    /* set to true if trade and account service should be from BitVc too */
    spec.setExchangeSpecificParametersItem(USE_BITVC, false);
    spec.setExchangeSpecificParametersItem(USE_BITVC_FUTURES, false);
    spec.setExchangeSpecificParametersItem(HUOBI_MARKET_DATA, "http://market.huobi.com/staticmarket");
    spec.setExchangeSpecificParametersItem("Websocket_SslUri", "http://hq.huobi.com");

    return spec;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // BitVC doesn't require a nonce for it's authenticated API
    return null;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {
    if (!(Boolean) exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC)) {
      return new HuobiStreamingExchangeService(getExchangeSpecification(), configuration);
    } else {
      return super.getStreamingExchangeService(configuration);
    }
  }
}
