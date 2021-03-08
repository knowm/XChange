package info.bitrich.xchangestream.ftx;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import org.knowm.xchange.ftx.FtxExchange;

public class FtxStreamingExchange extends FtxExchange implements StreamingExchange {

    private final String API_URI = "wss://ftx.com/ws/";


    @Override
    protected void initServices() {
        super.initServices();
    }

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
    public StreamingMarketDataService getStreamingMarketDataService() {
        return null;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) {

    }
}
