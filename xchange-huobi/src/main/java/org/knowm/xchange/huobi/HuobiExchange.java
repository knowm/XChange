package org.knowm.xchange.huobi;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.huobi.service.BitVcAccountService;
import org.knowm.xchange.huobi.service.BitVcFuturesAccountService;
import org.knowm.xchange.huobi.service.BitVcFuturesMarketDataService;
import org.knowm.xchange.huobi.service.BitVcFuturesTradeService;
import org.knowm.xchange.huobi.service.BitVcTradeServiceRaw;
import org.knowm.xchange.huobi.service.GenericTradeService;
import org.knowm.xchange.huobi.service.HuobiAccountService;
import org.knowm.xchange.huobi.service.HuobiMarketDataService;
import org.knowm.xchange.huobi.service.HuobiTradeServiceRaw;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * By default when instantiating this exchange market data and trading goes via Huobi. If the flag HuobiExchange.HUOBI_MARKET_DATA is true then market
 * data will go through Huobi still and trade execution and account information will go through BitVC.
 */
public class HuobiExchange extends BaseExchange implements Exchange {

  public static final String TRADE_PASSWORD_PARAMETER = "trade_password";

  /**
   * Potentially different market data endpoints should be settable
   */
  public static final String HUOBI_MARKET_DATA = "huobi_uri_marketdata";

  /**
   * Using BitVc Spot for execution
   */
  public static final String USE_BITVC = "use_bitvc";
  /**
   * Use BitVc Futures for market data
   */
  public static final String USE_BITVC_FUTURES_MARKET_DATA = "use_bitvc_futures";
  /**
   * Use BitVc Futures for execution
   */
  public static final String USE_BITVC_FUTURES_EXECUTION = "use_bitvc_futures_execution";

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    concludeHostParams(exchangeSpecification);
  }

  @Override
  protected void initServices() {

    concludeHostParams(exchangeSpecification);

    if (exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC).equals(true)
        && exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC_FUTURES_MARKET_DATA).equals(true)) {

      marketDataService = new BitVcFuturesMarketDataService(this, futuresContractOfConfig(exchangeSpecification));
    } else {
      marketDataService = new HuobiMarketDataService(this);
    }

    if (exchangeSpecification.getApiKey() != null) {
      if (exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC).equals(true)) {

        // BitVc futures execution or spot execution
        if (exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC_FUTURES_EXECUTION).equals(true)) {
          accountService = new BitVcFuturesAccountService(this);
          tradeService = new BitVcFuturesTradeService(this, futuresContractOfConfig(exchangeSpecification));
        } else {
          accountService = new BitVcAccountService(this);
          tradeService = new GenericTradeService(this, new BitVcTradeServiceRaw(this));
        }
      } else {
        accountService = new HuobiAccountService(this);
        tradeService = new GenericTradeService(this, new HuobiTradeServiceRaw(this));

      }
    }
  }

  /**
   * Adjust host parameters depending on exchange specific parameters
   */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {

    if (exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC).equals(true)) {
      exchangeSpecification.setSslUri("https://api.bitvc.com");
      exchangeSpecification.setExchangeSpecificParametersItem("Websocket_SslUri", "NOT IMPLEMENTED");
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
    spec.setPlainTextUri("http://api.huobi.com/staticmarket");
    spec.setSslUri("https://api.huobi.com/apiv3");

    /* set to true if trade and account service should be from BitVc too */
    spec.setExchangeSpecificParametersItem(USE_BITVC, false);
    spec.setExchangeSpecificParametersItem(USE_BITVC_FUTURES_MARKET_DATA, false);
    spec.setExchangeSpecificParametersItem(USE_BITVC_FUTURES_EXECUTION, false);
    spec.setExchangeSpecificParametersItem(HUOBI_MARKET_DATA, "http://api.huobi.com/staticmarket");
    spec.setExchangeSpecificParametersItem("Websocket_SslUri", "http://hq.huobi.com");

    return spec;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    // BitVC doesn't require a nonce for it's authenticated API
    return null;
  }

}
