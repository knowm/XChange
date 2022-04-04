package info.bitrich.xchangestream.okcoin;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.Completable;
import org.knowm.xchange.okex.v5.OkexExchange;

public class OkxStreamingExchange extends OkexExchange implements StreamingExchange {
    @Override
    public Completable connect(ProductSubscription... args) {
        return null;
    }

    @Override
    public Completable disconnect() {
        return null;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) {

    }
}
