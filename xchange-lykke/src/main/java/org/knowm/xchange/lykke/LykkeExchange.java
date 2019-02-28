package org.knowm.xchange.lykke;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.FeeTier;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.lykke.dto.marketdata.LykkeAsset;
import org.knowm.xchange.lykke.dto.marketdata.LykkeAssetPair;
import org.knowm.xchange.lykke.service.LykkeAccountService;
import org.knowm.xchange.lykke.service.LykkeMarketDataService;
import org.knowm.xchange.lykke.service.LykkeTradeService;
import org.knowm.xchange.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class LykkeExchange extends BaseExchange implements Exchange {

    private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongCurrentTimeIncrementalNonceFactory();

    @Override
    protected void initServices() {
        this.marketDataService = new LykkeMarketDataService(this);
        this.tradeService = new LykkeTradeService(this);
        this.accountService = new LykkeAccountService(this);
    }

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        return nonceFactory;
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification exchangeSpecification =
                new ExchangeSpecification(this.getClass().getCanonicalName());
        exchangeSpecification.setSslUri("https://hft-api.lykke.com/");
        exchangeSpecification.setHost("lykke.com");
        exchangeSpecification.setPort(80);
        exchangeSpecification.setExchangeName("Lykke");
        exchangeSpecification.setExchangeDescription("Lykke is a bitcoin and altcoin wallet and exchange.");

        return exchangeSpecification;
    }

    @Override
    public void remoteInit() throws IOException, ExchangeException {
        try{
            // populate currency pair keys only, exchange does not provide any other metadata for download
            Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
            Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();
            List<CurrencyPair> currencyPairList = getExchangeSymbols();

            LykkeMarketDataService marketDataService = (LykkeMarketDataService) this.marketDataService;
            List<LykkeAssetPair> assetPairList = marketDataService.getAssetPairs();

            for (LykkeAssetPair lykkeAssetPair : assetPairList){
                CurrencyPair currencyPair = new CurrencyPair(lykkeAssetPair.getName().split("/")[0],lykkeAssetPair.getQuotingAssetId());
                CurrencyPairMetaData currencyPairMetaData = new CurrencyPairMetaData(BigDecimal.ZERO,null,null,lykkeAssetPair.getAccuracy(),null);
                currencyPairs.put(currencyPair,currencyPairMetaData);
                currencyPairList.add(currencyPair);
            }
            for(LykkeAsset lykkeAsset : marketDataService.getLykkeAssets()){
                if(lykkeAsset.getSymbol() != null) {
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
