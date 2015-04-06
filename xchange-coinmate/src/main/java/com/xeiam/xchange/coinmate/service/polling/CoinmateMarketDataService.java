
package com.xeiam.xchange.coinmate.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinmate.CoinmateAdapters;
import com.xeiam.xchange.coinmate.CoinmateUtils;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import java.io.IOException;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateMarketDataService extends CoinmateMarketDataServiceRaw implements PollingMarketDataService {

    public CoinmateMarketDataService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        
        return CoinmateAdapters.adaptTicker(getCoinmateTicker(CoinmateUtils.getPair(currencyPair)), currencyPair);
    }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        
        return CoinmateAdapters.adaptOrderBook(getCoinmateOrderBook(CoinmateUtils.getPair(currencyPair), true), currencyPair);
        
    }

    @Override
    public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
