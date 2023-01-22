package info.bitrich.xchangestream.okex;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.Completable;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.okex.OkexExchange;


public class OkexStreamingExchange extends OkexExchange implements StreamingExchange {
    // Production URIs
    public static final String WS_PUBLIC_CHANNEL_URI = "wss://ws.okx.com:8443/ws/v5/public";
    public static final String WS_PRIVATE_CHANNEL_URI = "wss://ws.okx.com:8443/ws/v5/private";

    public static final String AWS_WS_PUBLIC_CHANNEL_URI = "wss://wsaws.okx.com:8443/ws/v5/public";
    public static final String AWS_WS_PRIVATE_CHANNEL_URI = "wss://wsaws.okx.com:8443/ws/v5/private";

    // Demo(Sandbox) URIs
    public static final String SANDBOX_WS_PUBLIC_CHANNEL_URI = "wss://wspap.okx.com:8443/ws/v5/public?brokerId=9999";
    public static final String SANDBOX_WS_PRIVATE_CHANNEL_URI = "wss://wspap.okx.com:8443/ws/v5/private?brokerId=9999";

    private OkexStreamingService streamingService;
    private OkexStreamingService privateStreamingService = null;

    private OkexStreamingMarketDataService streamingMarketDataService;

    private OkexStreamingTradeService streamingTradeService;
    public OkexStreamingExchange() {}


    @Override
    public Completable connect(ProductSubscription... args) {
        boolean userAws =
                Boolean.TRUE.equals(
                        exchangeSpecification.getExchangeSpecificParametersItem(PARAM_USE_AWS)
                );
        this.streamingService = new OkexStreamingService(getPublicUrl(useSandbox(), userAws));
        this.streamingMarketDataService = new OkexStreamingMarketDataService(streamingService);
        if(this.exchangeSpecification.getApiKey() != null){
            this.privateStreamingService = new OkexStreamingService(getPrivateApiUrl(useSandbox(), userAws), this.exchangeSpecification);
            this.streamingTradeService = new OkexStreamingTradeService(privateStreamingService, exchangeMetaData);
        }
        Completable completable;

        if(this.exchangeSpecification.getApiKey() != null){
            completable = streamingService.connect().concatWith(privateStreamingService.connect());
        } else {
            completable = streamingService.connect();
        }
        return completable;
    }

    private String getPublicUrl(Boolean useSandBox, Boolean isAws){
        if (useSandBox) {
            return SANDBOX_WS_PUBLIC_CHANNEL_URI;
        } else {
            return isAws ? AWS_WS_PUBLIC_CHANNEL_URI : WS_PUBLIC_CHANNEL_URI;
        }
    }

    private String getPrivateApiUrl(Boolean useSandBox, Boolean isAws){
        if (useSandBox) {
            return SANDBOX_WS_PRIVATE_CHANNEL_URI;
        } else {
            return isAws ? AWS_WS_PRIVATE_CHANNEL_URI : WS_PRIVATE_CHANNEL_URI;
        }
    }

    @Override
    public Completable disconnect() {
        if(privateStreamingService != null){
            return streamingService.disconnect().concatWith(privateStreamingService.disconnect());
        } else {
            return streamingService.disconnect();
        }
    }

    @Override
    public boolean isAlive() {
        if(privateStreamingService != null){
            return streamingService != null && streamingService.isSocketOpen() && privateStreamingService.isSocketOpen();
        } else {
            return streamingService != null && streamingService.isSocketOpen();
        }
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public StreamingTradeService getStreamingTradeService() {
        return streamingTradeService;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) {
        throw new NotYetImplementedForExchangeException("useCompressedMessage");
    }
}
