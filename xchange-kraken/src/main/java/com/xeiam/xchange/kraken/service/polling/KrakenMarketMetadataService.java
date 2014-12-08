package com.xeiam.xchange.kraken.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.BaseMarketMetadata;
import com.xeiam.xchange.dto.marketdata.MarketMetadata;
import com.xeiam.xchange.kraken.KrakenAuthenticated;
import com.xeiam.xchange.kraken.dto.account.KrakenTradeVolume;
import com.xeiam.xchange.kraken.dto.account.results.KrakenTradeVolumeResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssetPair;
import com.xeiam.xchange.service.polling.MarketMetadataService;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;

import static com.xeiam.xchange.utils.ConfigurationManager.CFG_MGR;

public class KrakenMarketMetadataService extends KrakenBasePollingService<KrakenAuthenticated> implements MarketMetadataService {

  public KrakenMarketMetadataService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(KrakenAuthenticated.class, exchangeSpecification, nonceFactory);
  }

  @Override
  public MarketMetadata getMarketMetadata(CurrencyPair pair) throws ExchangeException, IOException {

    String krakenPair = createKrakenCurrencyPair(pair);

    KrakenAssetPair assetPair = checkResult(kraken.getAssetPairs(krakenPair)).get(krakenPair);

    // we could either make anther call to KrakenAuthenticated.tradeVolume() or just return the highest fee
    BigDecimal fee = getTradeVolume(pair).getFees().get(krakenPair).getFee().movePointLeft(2);

    BigDecimal amountMinimum = new BigDecimal(CFG_MGR.getProperties().getProperty(KEY_ORDER_SIZE_MIN_DEFAULT)).setScale(assetPair.getVolumeLotScale());
    return new BaseMarketMetadata(amountMinimum, assetPair.getPairScale(), fee);
  }

  protected KrakenTradeVolume getTradeVolume(CurrencyPair... currencyPairs) throws IOException {

    KrakenTradeVolumeResult result = kraken.tradeVolume(delimitAssetPairs(currencyPairs), exchangeSpecification.getApiKey(), signatureCreator, nextNonce());

    return checkResult(result);
  }

}
