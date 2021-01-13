package info.bitrich.xchangestream.gemini.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.core.ProductSubscription;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapted from V1 by Max Gao on 01-09-2021
 */
public class GeminiWebSocketSubscriptionMessage {
    private static final String L2 = "l2";
    private static final String CANDLES_1M = "candles_1m";
    private static final String CANDLES_5M = "candles_5m";
    private static final String CANDLES_15M = "candles_15m";
    private static final String CANDLES_30M = "candles_30m";
    private static final String CANDLES_1H = "candles_1h";
    private static final String CANDLES_6H = "candles_6h";
    private static final String CANDLES_1D = "candles_1d";

    public class Subscription {
        public Subscription(String name, String[] symbols) {
            this.name = name;
            this.symbols = symbols;
        }
        @JsonProperty("name")
        public final String name;
        @JsonProperty("symbols")
        public final String[] symbols;

    }

    @JsonProperty("type")
    public final String type;

    @JsonProperty("subscriptions")
    public final Subscription[] subscriptions;

    public GeminiWebSocketSubscriptionMessage(String type, ProductSubscription productSubscription) {
        this.type = type;
        this.subscriptions = constructSubscriptions(productSubscription);
    }

    private Subscription[] constructSubscriptions(ProductSubscription productSubscription) {
        if (productSubscription == null) return null;

        List<Subscription> subscriptions = new ArrayList<>();
        List<String> L2Symbols = new ArrayList<>();

        for (CurrencyPair currencyPair : productSubscription.getOrderBook()) {
            L2Symbols.add(currencyPair.base.toString() + currencyPair.counter.toString());
        }

        subscriptions.add(new Subscription(L2, L2Symbols.stream().toArray(String[]::new)));

        return subscriptions.stream().toArray(Subscription[]::new);
    }
}
