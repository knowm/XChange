package org.knowm.xchange.liqui;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiDepthResult;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiInfoResult;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiTickersResult;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiTradesResult;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

import java.util.List;

public class LiquiBaseService extends BaseExchangeService implements BaseService {

    private final Liqui liqui;

    protected LiquiBaseService(final Exchange exchange) {
        super(exchange);

        liqui = RestProxyFactory.createProxy(Liqui.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
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

    public LiquiTradesResult getTrades(final CurrencyPair pair) {
        return liqui.getTrades(new Liqui.Pairs(pair));
    }

    public LiquiTradesResult getTrades(final List<CurrencyPair> pairs) {
        return liqui.getTrades(new Liqui.Pairs(pairs));
    }

    public LiquiTradesResult getTrades(final CurrencyPair pair, final int limit) {
        return liqui.getTrades(new Liqui.Pairs(pair), limit);
    }

    public LiquiTradesResult getTrades(final List<CurrencyPair> pairs, final int limit) {
        return liqui.getTrades(new Liqui.Pairs(pairs), limit);
    }
}
