package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.List;
import java.util.stream.Collectors;

public class BinanceStreamingExchange extends BinanceExchange implements StreamingExchange {
    private static final String API_BASE_URI = "wss://stream.binance.com:9443/";

    private BinanceStreamingService streamingService;
    private BinanceStreamingMarketDataService streamingMarketDataService;

    public BinanceStreamingExchange() {}

    /**
     * Binance streaming API expects connections to multiple channels to be defined at connection time. To define the channels for this
     * connection pass a `ProductSubscription` in at connection time.
     *
     * @param args A single `ProductSubscription` to define the subscriptions required to be available during this connection.
     * @return
     */
    @Override
    public Completable connect(ProductSubscription... args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Subscriptions must be made at connection time");
        }
        if (streamingService != null) {
            throw new UnsupportedOperationException("Exchange only handles a single connection - disconnect the current connection.");
        }

        ProductSubscription subscriptions = args[0];
        streamingService = createStreamingService(subscriptions);
        streamingMarketDataService = new BinanceStreamingMarketDataService(streamingService);
        return streamingService.connect()
                .doOnComplete(() -> streamingMarketDataService.openSubscriptions(subscriptions));
    }

    @Override
    public Completable disconnect() {
        BinanceStreamingService service = streamingService;
        streamingService = null;
        streamingMarketDataService = null;
        return service.disconnect();
    }

    @Override
    public boolean isAlive() {
        throw new IllegalStateException("Not implemented.");
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    private BinanceStreamingService createStreamingService(ProductSubscription subscription) {
        String path = API_BASE_URI + "stream?streams=" + buildSubscriptionStreams(subscription);
        return new BinanceStreamingService(path, subscription);
    }

    private static String buildSubscriptionStreams(ProductSubscription subscription) {
        return buildSubscriptionStrings(subscription.getTicker(), "ticker") +
                buildSubscriptionStrings(subscription.getOrderBook(), "depth") +
                buildSubscriptionStrings(subscription.getTrades(), "trade");
    }

    private static String buildSubscriptionStrings(List<CurrencyPair> currencyPairs, String subscriptionType) {
        StringBuilder builder = new StringBuilder();
        subscriptionStrings(currencyPairs).forEach(subscriptionString -> {
            builder.append("/");
            builder.append(subscriptionString);
            builder.append("@");
            builder.append(subscriptionType);
        });
        return builder.toString();
    }

    private static List<String> subscriptionStrings(List<CurrencyPair> currencyPairs) {
        return currencyPairs.stream()
                .map(pair -> String.join("", pair.toString().split("/")).toLowerCase())
                .collect(Collectors.toList());
    }

}

