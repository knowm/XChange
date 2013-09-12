package org.xchange.kraken.service.polling;

import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class KrakenPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

    protected KrakenPollingMarketDataService(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<CurrencyPair> getExchangeSymbols() {
       throw new NotYetImplementedForExchangeException();
    }

    @Override
    public Ticker getTicker(String tradableIdentifier, String currency) throws ExchangeException, NotAvailableFromExchangeException,
            NotYetImplementedForExchangeException {
        throw new NotYetImplementedForExchangeException();

    }

    @Override
    public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) throws ExchangeException, NotAvailableFromExchangeException,
            NotYetImplementedForExchangeException {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public OrderBook getFullOrderBook(String tradableIdentifier, String currency) throws ExchangeException, NotAvailableFromExchangeException,
            NotYetImplementedForExchangeException {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public Trades getTrades(String tradableIdentifier, String currency, Object... args) throws ExchangeException, NotAvailableFromExchangeException,
            NotYetImplementedForExchangeException {
        throw new NotYetImplementedForExchangeException();
    }

}
