package org.knowm.xchange.liqui.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.liqui.Liqui;
import org.knowm.xchange.liqui.LiquiAuthenticated;
import org.knowm.xchange.liqui.dto.marketdata.LiquiResult;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiDepthResult;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiInfoResult;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiPublicTradesResult;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiTickersResult;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

import java.util.List;

public class LiquiBaseService extends BaseExchangeService implements BaseService {

    protected final Liqui liqui;
    protected final LiquiAuthenticated liquiAuthenticated;
    protected final LiquiDigest signatureCreator;

    protected LiquiBaseService(final Exchange exchange) {
        super(exchange);

        liqui = RestProxyFactory.createProxy(Liqui.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
        liquiAuthenticated = RestProxyFactory.createProxy(LiquiAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());

        signatureCreator = LiquiDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());

    }

    public LiquiInfoResult getInfo() {
        return liqui.getInfo();
    }

    public LiquiTickersResult getTicker(final CurrencyPair pair) {
        return liqui.getTicker(new Liqui.Pairs(pair));
    }

    public LiquiTickersResult getTicker(final List<CurrencyPair> pairs) {
        return liqui.getTicker(new Liqui.Pairs(pairs));
    }

    public LiquiDepthResult getDepth(final CurrencyPair pair) {
        return liqui.getDepth(new Liqui.Pairs(pair));
    }

    public LiquiDepthResult getDepth(final List<CurrencyPair> pairs) {
        return liqui.getDepth(new Liqui.Pairs(pairs));
    }

    public LiquiDepthResult getDepth(final CurrencyPair pair, final int limit) {
        return liqui.getDepth(new Liqui.Pairs(pair), limit);
    }

    public LiquiDepthResult getDepth(final List<CurrencyPair> pairs, final int limit) {
        return liqui.getDepth(new Liqui.Pairs(pairs), limit);
    }

    public LiquiPublicTradesResult getTrades(final CurrencyPair pair) {
        return liqui.getTrades(new Liqui.Pairs(pair));
    }

    public LiquiPublicTradesResult getTrades(final List<CurrencyPair> pairs) {
        return liqui.getTrades(new Liqui.Pairs(pairs));
    }

    public LiquiPublicTradesResult getTrades(final CurrencyPair pair, final int limit) {
        return liqui.getTrades(new Liqui.Pairs(pair), limit);
    }

    public LiquiPublicTradesResult getTrades(final List<CurrencyPair> pairs, final int limit) {
        return liqui.getTrades(new Liqui.Pairs(pairs), limit);
    }

    protected <R> R checkResult(final LiquiResult<R> liquiResult) {
        if (liquiResult.getResult() == null) {
            // TODO better result checking
            throw new RuntimeException("Result not present");
        }

        return liquiResult.getResult();
    }
}
