package org.knowm.xchange.bitfinex;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitfinex.config.Config;
import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.bitfinex.service.BitfinexAccountService;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.bitfinex.service.BitfinexMarketDataService;
import org.knowm.xchange.bitfinex.service.BitfinexMarketDataServiceRaw;
import org.knowm.xchange.bitfinex.service.BitfinexTradeService;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexCurrencyPairInfo;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitfinexExchange extends BaseExchange {

  private static ResilienceRegistries RESILIENCE_REGISTRIES;

  private SynchronizedValueFactory<Long> nonceFactory = Config.getInstance().getNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new BitfinexMarketDataService(this, getResilienceRegistries());
    this.accountService = new BitfinexAccountService(this, getResilienceRegistries());
    this.tradeService = new BitfinexTradeService(this, getResilienceRegistries());
  }

  @Override
  public ResilienceRegistries getResilienceRegistries() {
    if (RESILIENCE_REGISTRIES == null) {
      RESILIENCE_REGISTRIES = BitfinexResilience.createRegistries();
    }
    return RESILIENCE_REGISTRIES;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.bitfinex.com/");
    exchangeSpecification.setHost("api.bitfinex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BitFinex");
    exchangeSpecification.setExchangeDescription("BitFinex is a bitcoin exchange.");
    exchangeSpecification.getResilience().setRateLimiterEnabled(true);
    exchangeSpecification.getResilience().setRetryEnabled(true);

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {

    try {

      BitfinexMarketDataServiceRaw dataService = (BitfinexMarketDataServiceRaw) marketDataService;

      // ust -> usdt
      BitfinexAdapters.putCurrencyMapping("UST", "USDT");

      // put derivatives currency mappings
      dataService
          .currencyDerivativesMappings()
          .forEach(
              bitfinexCurrencyMapping -> {
                BitfinexAdapters.putCurrencyMapping(
                    bitfinexCurrencyMapping.getSource(), bitfinexCurrencyMapping.getTarget());
              });

      List<BitfinexCurrencyPairInfo> currencyPairInfos = dataService.allCurrencyPairInfos();

      Map<Instrument, InstrumentMetaData> instruments = new HashMap<>();
      Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

      currencyPairInfos.forEach(
          bitfinexCurrencyPairInfo -> {
            instruments.put(
                bitfinexCurrencyPairInfo.getCurrencyPair(),
                InstrumentMetaData.builder()
                    .minimumAmount(bitfinexCurrencyPairInfo.getInfo().getMinAssetAmount())
                    .maximumAmount(bitfinexCurrencyPairInfo.getInfo().getMaxAssetAmount())
                    .priceScale(8)
                    .volumeScale(8)
                    .marketOrderEnabled(true)
                    .build());

            currencies.put(
                bitfinexCurrencyPairInfo.getCurrencyPair().getBase(),
                new CurrencyMetaData(8, null));
            currencies.put(
                bitfinexCurrencyPairInfo.getCurrencyPair().getCounter(),
                new CurrencyMetaData(8, null));
          });

      exchangeMetaData = new ExchangeMetaData(instruments, currencies, null, null, null);

    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }
}
