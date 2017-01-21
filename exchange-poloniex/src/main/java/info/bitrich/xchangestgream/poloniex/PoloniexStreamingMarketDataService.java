package info.bitrich.xchangestgream.poloniex;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.wamp.WampStreamingService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.poloniex.PoloniexAdapters;
import org.knowm.xchange.poloniex.PoloniexUtils;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexTicker;

import java.math.BigDecimal;

public class PoloniexStreamingMarketDataService implements StreamingMarketDataService {
    private final WampStreamingService streamingService;

    public PoloniexStreamingMarketDataService(WampStreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        return streamingService.subscribeChannel("ticker")
                .map(pubSubData -> {
                    PoloniexMarketData marketData = new PoloniexMarketData();
                    marketData.setLast(new BigDecimal(pubSubData.arguments().get(1).asText()));
                    marketData.setLowestAsk(new BigDecimal(pubSubData.arguments().get(2).asText()));
                    marketData.setHighestBid(new BigDecimal(pubSubData.arguments().get(3).asText()));
                    marketData.setPercentChange(new BigDecimal(pubSubData.arguments().get(4).asText()));
                    marketData.setBaseVolume(new BigDecimal(pubSubData.arguments().get(5).asText()));
                    marketData.setQuoteVolume(new BigDecimal(pubSubData.arguments().get(6).asText()));
                    marketData.setHigh24hr(new BigDecimal(pubSubData.arguments().get(8).asText()));
                    marketData.setLow24hr(new BigDecimal(pubSubData.arguments().get(9).asText()));

                    PoloniexTicker ticker = new PoloniexTicker(marketData, PoloniexUtils.toCurrencyPair(pubSubData.arguments().get(0).asText()));
                    return PoloniexAdapters.adaptPoloniexTicker(ticker, ticker.getCurrencyPair());
                })
                .filter(ticker -> ticker.getCurrencyPair().equals(currencyPair));
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }
}
