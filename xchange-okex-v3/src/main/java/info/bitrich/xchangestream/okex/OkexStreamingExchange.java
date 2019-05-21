package info.bitrich.xchangestream.okex;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.netty.channel.ChannelHandlerContext;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.okcoin.OkCoinExchange;

public class OkexStreamingExchange extends OkCoinExchange implements StreamingExchange {
    private static final String API_URI = "wss://real.okex.com:10442/ws/v3";

    protected final OkexStreamingService streamingService;
    private OkexStreamingMarketDataService streamingMarketDataService;

    public OkexStreamingExchange() {
        streamingService = new OkexStreamingService(API_URI);
    }

    protected OkexStreamingExchange(OkexStreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @Override
    protected void initServices() {
        super.initServices();
        streamingMarketDataService = new OkexStreamingMarketDataService(streamingService);
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
    public boolean isAlive() {
        return streamingService.isSocketOpen();
    }

    @Override
    public Observable<Throwable> reconnectFailure() {
        return streamingService.subscribeReconnectFailure();
    }

    @Override
    public Observable<ChannelHandlerContext> disconnectObservable() {
        return streamingService.subscribeDisconnect();
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) { streamingService.useCompressedMessages(compressedMessages); }

    @Override
    public Observable<Long> messageDelay() {
        return Observable.create(streamingService::addDelayEmitter);
    }

    @Override
    public void resubscribeChannels() {
        streamingService.resubscribeChannels();
    }
}
