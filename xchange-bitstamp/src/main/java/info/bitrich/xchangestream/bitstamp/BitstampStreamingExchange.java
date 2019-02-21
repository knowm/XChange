package info.bitrich.xchangestream.bitstamp;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.NettyStreamingService;
import info.bitrich.xchangestream.service.pusher.PusherStreamingService;
import io.reactivex.Completable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

import static info.bitrich.xchangestream.service.ConnectableService.BEFORE_CONNECTION_HANDLER;

public class BitstampStreamingExchange extends BitstampExchange implements StreamingExchange {
    private static final String API_KEY = "de504dc5763aeef9ff52";
    private final PusherStreamingService streamingService;

    private BitstampStreamingMarketDataService streamingMarketDataService;

    public BitstampStreamingExchange() {
        streamingService = new PusherStreamingService(API_KEY);
    }

    @Override
    protected void initServices() {
        super.initServices();
        streamingService.setBeforeConnectionHandler((Runnable) getExchangeSpecification().getExchangeSpecificParametersItem(BEFORE_CONNECTION_HANDLER));
        streamingMarketDataService = new BitstampStreamingMarketDataService(streamingService);
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        return streamingService.connect();
    }

    @Override
    public Completable disconnect() {
        return streamingService.disconnect();
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public boolean isAlive() {
        return this.streamingService.isSocketOpen();
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) { streamingService.useCompressedMessages(compressedMessages); }
}
