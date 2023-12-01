package org.knowm.xchange.lykke;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.FeeTier;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.lykke.dto.marketdata.LykkeAsset;
import org.knowm.xchange.lykke.dto.marketdata.LykkeAssetPair;
import org.knowm.xchange.lykke.service.LykkeAccountService;
import org.knowm.xchange.lykke.service.LykkeMarketDataService;
import org.knowm.xchange.lykke.service.LykkeTradeService;

public class LykkeExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new LykkeMarketDataService(this);
    this.tradeService = new LykkeTradeService(this);
    this.accountService = new LykkeAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://hft-api.lykke.com/");
    exchangeSpecification.setHost("lykke.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Lykke");
    exchangeSpecification.setExchangeDescription(
        "Lykke is a bitcoin and altcoin wallet and exchange.");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    try {
      // populate currency pair keys only, exchange does not provide any other metadata for download
      Map<Instrument, InstrumentMetaData> currencyPairs = exchangeMetaData.getInstruments();
      Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();
      List<Instrument> currencyPairList = getExchangeInstruments();
      List<FeeTier> feeTiers = new ArrayList<>();
      feeTiers.add(new FeeTier(BigDecimal.ZERO, new Fee(BigDecimal.ZERO, BigDecimal.ZERO)));

      LykkeMarketDataService marketDataService = (LykkeMarketDataService) this.marketDataService;
      List<LykkeAssetPair> assetPairList = marketDataService.getAssetPairs();

      for (LykkeAssetPair lykkeAssetPair : assetPairList) {
        CurrencyPair currencyPair =
            new CurrencyPair(
                lykkeAssetPair.getName().split("/")[0], lykkeAssetPair.getQuotingAssetId());
        InstrumentMetaData currencyPairMetaData =
            new InstrumentMetaData.Builder()
                .feeTiers(feeTiers.toArray(new FeeTier[feeTiers.size()]))
                .priceScale(lykkeAssetPair.getAccuracy())
                .build();
        currencyPairs.put(currencyPair, currencyPairMetaData);
        currencyPairList.add(currencyPair);
      }
      for (LykkeAsset lykkeAsset : marketDataService.getLykkeAssets()) {
        if (lykkeAsset.getSymbol() != null) {
          Currency currency = new Currency(lykkeAsset.getSymbol());
          CurrencyMetaData currencyMetaData = new CurrencyMetaData(lykkeAsset.getAccuracy(), null);
          currencies.put(currency, currencyMetaData);
        }
      }
    } catch (Exception e) {
      logger.warn("An exception occurred while loading the metadata", e);
    }
  }
}
