package org.knowm.xchange.coinbasepro;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCurrency;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProduct;
import org.knowm.xchange.coinbasepro.service.CoinbaseProAccountService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataServiceRaw;
import org.knowm.xchange.coinbasepro.service.CoinbaseProTradeService;
import org.knowm.xchange.utils.nonce.CurrentTime1000NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinbaseProExchange extends BaseExchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTime1000NonceFactory();

  /** Adjust host parameters depending on exchange specific parameters */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {

    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Sandbox").equals(true)) {

        if (Boolean.TRUE.equals(
            exchangeSpecification.getExchangeSpecificParametersItem("Use_Prime"))) {
          exchangeSpecification.setSslUri("https://api-public.sandbox.prime.coinbase.com");
          exchangeSpecification.setHost("api-public.sandbox.prime.coinbase.com");
        } else {
          exchangeSpecification.setSslUri("https://api-public.sandbox.pro.coinbase.com");
          exchangeSpecification.setHost("api-public.sandbox.pro.coinbase.com");
        }

      } else {
        if (Boolean.TRUE.equals(
            exchangeSpecification.getExchangeSpecificParametersItem("Use_Prime"))) {
          exchangeSpecification.setSslUri("https://api.prime.coinbase.com");
          exchangeSpecification.setHost("api.prime.coinbase.com");
        } else {
          exchangeSpecification.setSslUri("https://api.pro.coinbase.com");
          exchangeSpecification.setHost("api.pro.coinbase.com");
        }
      }
    }
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    concludeHostParams(exchangeSpecification);
  }

  @Override
  protected void initServices() {

    concludeHostParams(exchangeSpecification);

    this.marketDataService = new CoinbaseProMarketDataService(this);
    this.accountService = new CoinbaseProAccountService(this);
    this.tradeService = new CoinbaseProTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.pro.coinbase.com");
    exchangeSpecification.setHost("api.pro.coinbase.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("CoinbasePro");
    exchangeSpecification.setExchangeDescription(
        "CoinbasePro Exchange is a Bitcoin exchange, re-branded from GDAX in 2018");

    exchangeSpecification.setExchangeSpecificParametersItem("Use_Sandbox", false);

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {
    CoinbaseProProduct[] products =
        ((CoinbaseProMarketDataServiceRaw) marketDataService).getCoinbaseProProducts();
    CoinbaseProCurrency[] currencies =
        ((CoinbaseProMarketDataServiceRaw) marketDataService).getCoinbaseProCurrencies();
    exchangeMetaData =
        CoinbaseProAdapters.adaptToExchangeMetaData(exchangeMetaData, products, currencies);
  }
}
