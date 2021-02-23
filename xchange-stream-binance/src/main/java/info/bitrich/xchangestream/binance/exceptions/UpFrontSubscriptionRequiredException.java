package info.bitrich.xchangestream.binance.exceptions;

public class UpFrontSubscriptionRequiredException extends UnsupportedOperationException {
    public UpFrontSubscriptionRequiredException() {
        super("Binance exchange only supports up front subscriptions - subscribe at connect time. To activate live Subscription/Unsubscription " +
            "to streams, call BinanceStreamingExchange.enableLiveSubscription() before subscribe method.");
    }
}
