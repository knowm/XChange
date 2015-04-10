package com.xeiam.xchange.independentreserve.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import java.io.IOException;
import java.util.List;

/**
 * Author: Kamil Zbikowski
 * Date: 4/9/15
 */
public class IndependentReserveBasePollingService extends BaseExchangeService implements BasePollingService {

    /**
     * Constructor
     *
     * @param exchange
     */
    protected IndependentReserveBasePollingService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public List<CurrencyPair> getExchangeSymbols() throws IOException {
        return null;
    }
}
