package info.bitrich.xchangestream.binance;

import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import io.reactivex.Observable;

public class BinanceStreamingService {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceStreamingService.class);

    private Map<CurrencyPair, BinanceProductStreamingService> productStreamingServices;
    private Map<CurrencyPair, Observable<JsonNode>> productSubscriptions;
    private final String baseUri;
    
    public BinanceStreamingService(String _baseUri) {
        baseUri = _baseUri;
        productStreamingServices = new HashMap<>();
        productSubscriptions = new HashMap<>();
    }

    public Observable<JsonNode> subscribeChannel(
            CurrencyPair currencyPair,
            Object... args) {
        if (!productStreamingServices.containsKey(currencyPair)) {
            String symbolUri = baseUri + currencyPair.base.toString().toLowerCase() + currencyPair.counter.toString().toLowerCase()+"@depth";
            BinanceProductStreamingService productStreamingService = new BinanceProductStreamingService(symbolUri,
                    currencyPair);
            productStreamingService.connect().blockingAwait();
            Observable<JsonNode> productSubscription = productStreamingService
                    .subscribeChannel(currencyPair.toString(), args);
            productStreamingServices.put(currencyPair, productStreamingService);
            productSubscriptions.put(currencyPair, productSubscription);
        }

        return productSubscriptions.get(currencyPair);
    }
}
