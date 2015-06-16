package com.xeiam.xchange.independentreserve;


import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.independentreserve.service.polling.IndependentReserveAccountService;
import com.xeiam.xchange.independentreserve.service.polling.IndependentReserveMarketDataService;
import com.xeiam.xchange.independentreserve.service.polling.IndependentReserveTradeService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Author: Kamil Zbikowski
 * Date: 4/9/15
 */
public class IndependentReserveExchange extends BaseExchange implements Exchange {

    private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

    @Override
    public void applySpecification(ExchangeSpecification exchangeSpecification) {

        super.applySpecification(exchangeSpecification);

        this.pollingMarketDataService = new IndependentReserveMarketDataService(this);
        this.pollingTradeService = new IndependentReserveTradeService(this);
        this.pollingAccountService = new IndependentReserveAccountService(this);
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {

        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
        exchangeSpecification.setSslUri("https://api.independentreserve.com");
        exchangeSpecification.setHost("https://api.independentreserve.com");
        exchangeSpecification.setPort(80);
        exchangeSpecification.setExchangeName("IndependentReserve");
        exchangeSpecification.setExchangeDescription("Independent Reserve is a registered Australian company, underpinned by Australia's highly regulated financial sector.");
        return exchangeSpecification;
    }



    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {

        return nonceFactory;
    }
}
