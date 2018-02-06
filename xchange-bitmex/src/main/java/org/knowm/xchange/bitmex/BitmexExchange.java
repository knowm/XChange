package org.knowm.xchange.bitmex;

import com.google.common.collect.BiMap;
import org.knowm.xchange.bitmex.auth.ApiKeyAuth;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.dto.BitmexInstrument;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.service.BitmexAccountService;
import org.knowm.xchange.bitmex.service.BitmexMarketDataService;
import org.knowm.xchange.bitmex.service.BitmexMarketDataServiceRaw;
import org.knowm.xchange.bitmex.service.BitmexTradeService;
import org.knowm.xchange.bitmex.util.ApiClient;
import org.knowm.xchange.bitmex.util.Configuration;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.List;

public class BitmexExchange extends BaseExchange implements Exchange {

    private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();
    private ThreadLocal<ApiClient> apiClientThreadLocal;

    @Override
    protected void initServices() {

        this.marketDataService = new BitmexMarketDataService(this);
        this.accountService = new BitmexAccountService(this);
        this.tradeService = new BitmexTradeService(this);
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
        exchangeSpecification.setSslUri("https://www.bitmex.com/");
        exchangeSpecification.setHost("bitmex.com");
        exchangeSpecification.setPort(80);
        exchangeSpecification.setExchangeName("Bitmex");
        exchangeSpecification.setExchangeDescription("Bitmex is a bitcoin exchang");
        return exchangeSpecification;
    }

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        return nonceFactory;
    }

    @Override
    public void remoteInit() throws IOException {

        List<BitmexInstrument> tickers  = ((BitmexMarketDataService)marketDataService).getActiveTickers();
        BiMap<BitmexPrompt, String> contracts = ((BitmexMarketDataServiceRaw) marketDataService).getActivePrompts(tickers);
        exchangeMetaData = BitmexAdapters.adaptToExchangeMetaData(exchangeMetaData, tickers, contracts);
    }


    public ApiClient getSwaggerApiClient() {
        if (apiClientThreadLocal == null)
            apiClientThreadLocal = new ThreadLocal<ApiClient>().withInitial(
                    () -> {


                        ApiClient defaultClient = Configuration.getDefaultApiClient();
                        ApiKeyAuth apiKey_ = (ApiKeyAuth) defaultClient.getAuthentication("apiKey");
                        apiKey_.setApiKey(getExchangeSpecification().getApiKey());
                        ApiKeyAuth apiNonce = (ApiKeyAuth) defaultClient.getAuthentication("apiNonce");
                        apiNonce.setApiKey("" + getNonceFactory().createValue());
                        ApiKeyAuth apiSignature = (ApiKeyAuth) defaultClient.getAuthentication("apiSignature");
                        apiSignature.setApiKey(getExchangeSpecification().getSecretKey());
                        return defaultClient;

                    });
        return apiClientThreadLocal.get();

    }
}
