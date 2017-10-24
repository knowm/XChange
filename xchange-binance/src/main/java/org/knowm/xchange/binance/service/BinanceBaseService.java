package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BinanceBaseService extends BaseExchangeService implements BaseService {

    protected final String apiKey;
    protected final BinanceAuthenticated binance;
    protected final ParamsDigest signatureCreator;
    
    /**
     * Constructor
     *
     * @param exchange
     */
    protected BinanceBaseService(Exchange exchange) {
        super(exchange);
        this.binance = RestProxyFactory.createProxy(BinanceAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
        this.apiKey = exchange.getExchangeSpecification().getApiKey();
        this.signatureCreator = BinanceHmacDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    }
    
    public static void main(String[] args) throws IOException {
        Exchange exchange = ExchangeFactory.INSTANCE.createExchangeWithoutSpecification("org.knowm.xchange.binance.BinanceExchange");
        ExchangeSpecification spec = exchange.getDefaultExchangeSpecification();
        spec.setApiKey("KlTqExbIz1DRu9uVuMxwlB5QGSvOdhgqNPBaR1EVbap8jH3i24JF6KuRsd35kABB");
        spec.setSecretKey("rqM7NFLRsTgU5LPbJ9tqEFCymKLoFo5PnZJDaWhwOSfef9syJC2xMBlnJaoVSjD2");
        exchange.applySpecification(spec);
        BinanceMarketDataService marketDataService = (BinanceMarketDataService) exchange.getMarketDataService();
        BinanceAccountService accountService = (BinanceAccountService) exchange.getAccountService();
        BinanceTradeServiceRaw tradeService = (BinanceTradeServiceRaw) exchange.getTradeService();
        
        accountService.depositHistory("BTC", System.currentTimeMillis() - TimeUnit.DAYS.toMillis(5), System.currentTimeMillis(), null, System.currentTimeMillis());
        accountService.withdraw("BTC", "1JcxZvwsmmE2Dx3K4vSsCPWEJKo2ofsKXW", new BigDecimal("0.01"), null, null, System.currentTimeMillis());
     
        System.out.println("OK");
    }
}
