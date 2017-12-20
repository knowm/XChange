package info.bitrich.xchangestream.binance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.bitrich.xchangestream.binance.dto.DepthBinanceWebSocketTransaction;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;

public class BinanceStreamingMarketDataService implements StreamingMarketDataService {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceStreamingMarketDataService.class);

    private final BinanceStreamingService service;
    private final Map<CurrencyPair, OrderBook> orderbooks = new HashMap<>();

    private final ObjectMapper mapper = new ObjectMapper();

    public BinanceStreamingMarketDataService(BinanceStreamingService service) {
      this.service = service;
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
      return service.subscribeChannel(currencyPair, args)
        .map((JsonNode s) -> {
            DepthBinanceWebSocketTransaction transaction = mapper.readValue(s.toString(), DepthBinanceWebSocketTransaction.class);
            OrderBook currentOrderBook = orderbooks.computeIfAbsent(currencyPair, orderBook -> new OrderBook(null, new ArrayList<LimitOrder>(), new ArrayList<LimitOrder>()));
            
            BinanceOrderbook ob = transaction.getOrderBook();
            ob.bids.entrySet().stream().forEach(e -> {
                currentOrderBook.update(new OrderBookUpdate(
                    OrderType.BID,
                    null,
                    currencyPair,
                    e.getKey(),
                    transaction.getEventTime(),
                    e.getValue()));});
            ob.asks.entrySet().stream().forEach(e -> {
                currentOrderBook.update(new OrderBookUpdate(
                    OrderType.ASK,
                    null,
                    currencyPair,
                    e.getKey(),
                    transaction.getEventTime(),
                    e.getValue()));});
            return currentOrderBook;
        });
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
      throw new NotAvailableFromExchangeException();
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
      throw new NotYetImplementedForExchangeException();
    }
}
