package info.bitrich.xchangestream.dydx;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.dydx.dto.dydxWebSocketTransaction;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

/**
 * Author: Max Gao (gaamox@tutanota.com)
 * Created: 20-02-2021
 */
public class dydxStreamingMarketDataService implements StreamingMarketDataService {

    private final dydxStreamingService service;

    //private final Map<CurrencyPair, >

    dydxStreamingMarketDataService(dydxStreamingService service) {
        this.service = service;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException("Not yet Implemented!");
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException("Not yet implemented!");
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException("Not yet implemented!");
    }

    /*
    public Observable<dydxWebSocketTransaction> getRawWebSocketTransactions(CurrencyPair currencyPair, boolean filterChannelName) {
        return service.getRawWebSocketTransactions(currencyPair, filterChannelName);
    }

     */

}
