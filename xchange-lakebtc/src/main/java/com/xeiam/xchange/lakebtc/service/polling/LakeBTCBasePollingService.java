package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.lakebtc.LakeBTC;
import com.xeiam.xchange.lakebtc.dto.LakeBTCResponse;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.Assert;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author kpysniak
 */
public class LakeBTCBasePollingService<T extends LakeBTC> extends BaseExchangeService implements BasePollingService {


    protected T btcLakeBTC;
    protected ParamsDigest signatureCreator;
    protected SynchronizedValueFactory<Long> tonce;

    /**
     * Constructor
     *
     * @param exchangeSpecification
     */
    public LakeBTCBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> tonceFactory) {

        super(exchangeSpecification);
        Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");

        this.btcLakeBTC = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
        this.signatureCreator = LakeBTCDigest.createInstance(exchangeSpecification.getUserName(), exchangeSpecification.getSecretKey());
        this.tonce = tonceFactory;
    }


    @SuppressWarnings("rawtypes")
    public static <T extends LakeBTCResponse> T checkResult(T returnObject) {
        if (returnObject.getResult() == null) {
            throw new ExchangeException("Null data returned");
        }
        return returnObject;
    }

    public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(
            CurrencyPair.BTC_USD, CurrencyPair.BTC_CNY
    );

    /**
     * Constructor Initialize common properties from the exchange specification
     *
     * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
     */
    protected LakeBTCBasePollingService(ExchangeSpecification exchangeSpecification) {

        super(exchangeSpecification);
    }

    @Override
    public Collection<CurrencyPair> getExchangeSymbols() throws IOException {
        return CURRENCY_PAIRS;
    }
}
