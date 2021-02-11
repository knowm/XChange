package info.bitrich.xchangestream.binance.exceptions;

public class UpFrontSubscriptionRequiredException extends UnsupportedOperationException {
    public UpFrontSubscriptionRequiredException() {
        super("Binance exchange only supports up front subscriptions - subscribe at connect time");
    }
}
