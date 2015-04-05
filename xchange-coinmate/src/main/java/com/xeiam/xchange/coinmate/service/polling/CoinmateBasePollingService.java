package com.xeiam.xchange.coinmate.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateBasePollingService extends BasePollingExchangeService implements BasePollingService {

    public CoinmateBasePollingService(Exchange exchange) {
        super(exchange);
    }
    
    @Override
    public List<CurrencyPair> getExchangeSymbols() throws IOException {
        return exchange.getMetaData().getCurrencyPairs();
    }

}
