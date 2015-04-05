package com.xeiam.xchange.coinmate.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinmate.Coinmate;
import com.xeiam.xchange.coinmate.dto.marketdata.CoinmateTicker;
import java.io.IOException;
import si.mazi.rescu.RestProxyFactory;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateMarketDataServiceRaw extends CoinmateBasePollingService {

    private final Coinmate coinmate;

    public CoinmateMarketDataServiceRaw(Exchange exchange) {
        super(exchange);
        this.coinmate = RestProxyFactory.createProxy(Coinmate.class, exchange.getExchangeSpecification().getSslUri());
    }

    public CoinmateTicker getCoinmateTicker(String currencyPair) throws IOException {

        return coinmate.getTicker(currencyPair);
    }

}
