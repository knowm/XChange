package org.knowm.xchange.binance;

import java.io.InputStream;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.meta.BinanceMetaData;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceExchange extends BaseExchange implements Exchange {

    private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongCurrentTimeIncrementalNonceFactory();
    private BinanceMetaData binanceMetaData;

    @Override
    protected void initServices() {
        this.marketDataService = new BinanceMarketDataService(this);
        this.tradeService = new BinanceTradeService(this);
        this.accountService = new BinanceAccountService(this);
    }

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {

        return nonceFactory;
    }

    @Override
    protected void loadExchangeMetaData(InputStream is) {

        binanceMetaData = loadMetaData(is, BinanceMetaData.class);
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
        spec.setSslUri("https://www.binance.com");
        spec.setHost("www.binance.com");
        spec.setPort(80);
        spec.setExchangeName("Binance");
        spec.setExchangeDescription("Binance Exchange.");
        return spec;
    }

    @Override
    public void remoteInit() {
        try {
            BinanceMarketDataService marketDataService = (BinanceMarketDataService) this.marketDataService;
            /*
             * binanceExchangeInfo = marketDataService.getBinanceInfo();
             * exchangeMetaData =
             * BinanceAdapters.toMetaData(binanceExchangeInfo, binanceMetaData);
             */
        } catch (Exception e) {
            logger.warn("An exception occurred while loading the metadata", e);
        }
    }

    public BinanceMetaData getDsxMetaData() {
        return binanceMetaData;
    }

}
