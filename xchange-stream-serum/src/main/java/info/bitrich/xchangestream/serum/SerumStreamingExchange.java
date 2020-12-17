package info.bitrich.xchangestream.serum;

import com.knowm.xchange.serum.SerumConfigs.Env;
import com.knowm.xchange.serum.SerumExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.ExchangeSpecification;

public class SerumStreamingExchange extends SerumExchange implements StreamingExchange {

    public static final String MAINNET_URL = "wss://api.mainnet-beta.solana.com";
    public static final String TESTNET_URL = "wss://testnet.solana.com";
    public static final String DEVNET_URL = "wss://devnet.solana.com";

    private SerumStreamingService streamingService;

    @Override
    public Completable connect(ProductSubscription... args) {

        final ExchangeSpecification exchangeSpec = getExchangeSpecification();
        final Env env = Env.valueOf(String.valueOf(exchangeSpec.getExchangeSpecificParametersItem("Env")));
        String url;
        switch (env) {
            case DEVNET:
                url = DEVNET_URL;
                break;
            case TESTNET:
                url = TESTNET_URL;
                break;
            case MAINNET:
                url = MAINNET_URL;
                break;
            default:
                throw new UnsupportedOperationException("Env unsupported");
        }
        this.streamingService = new SerumStreamingService(url);
        return this.streamingService.connect();
    }

    @Override
    public Completable disconnect() {
        final SerumStreamingService service = streamingService;
        streamingService = null;
        return service.disconnect();
    }

    @Override
    public Observable<Throwable> reconnectFailure() {
        return streamingService.subscribeReconnectFailure();
    }

    @Override
    public Observable<Object> connectionSuccess() {
        return streamingService.subscribeConnectionSuccess();
    }

    @Override
    public Observable<ConnectionStateModel.State> connectionStateObservable() {
        return streamingService.subscribeConnectionState();
    }


    @Override
    public boolean isAlive() {
        return streamingService != null && streamingService.isSocketOpen();
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return null;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) {
        streamingService.useCompressedMessages(compressedMessages);
    }
}
