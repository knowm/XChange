package info.bitrich.xchangestream.kraken;

import com.google.common.base.MoreObjects;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.account.KrakenWebsocketToken;
import org.knowm.xchange.kraken.service.KrakenAccountServiceRaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author makarid
 */
public class KrakenFuturesStreamingExchange extends KrakenExchange implements StreamingExchange {

    private static final Logger LOG = LoggerFactory.getLogger(KrakenFuturesStreamingExchange.class);
    private static final String USE_BETA = "Use_Beta";
    private static final String API_URI = "wss://futures.kraken.com/ws/v1";
    private static final String API_BETA_URI = "wss://demo-futures.kraken.com/ws/v1";

    private KrakenFuturesStreamingService streamingService, privateStreamingService;
    private KrakenFuturesStreamingMarketDataService streamingMarketDataService;

    public KrakenFuturesStreamingExchange() {
    }

    public static String pickUri(boolean useBeta) {
        return useBeta ? API_BETA_URI : API_URI;
    }

    @Override
    protected void initServices() {
        super.initServices();
        Boolean useBeta =
                MoreObjects.firstNonNull(
                        (Boolean) exchangeSpecification.getExchangeSpecificParametersItem(USE_BETA),
                        Boolean.FALSE);

        KrakenAccountServiceRaw accountService = (KrakenAccountServiceRaw) getAccountService();

        this.streamingService =
                new KrakenFuturesStreamingService(false, pickUri(useBeta), () -> authData(accountService));
        this.streamingMarketDataService = new KrakenFuturesStreamingMarketDataService(streamingService);

        if (StringUtils.isNotEmpty(exchangeSpecification.getApiKey())) {
            this.privateStreamingService =
                    new KrakenFuturesStreamingService(true, pickUri(useBeta), () -> authData(accountService));
        }
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        if (privateStreamingService != null)
            return privateStreamingService.connect().mergeWith(streamingService.connect());
        return streamingService.connect();
    }

    @Override
    public Completable disconnect() {
        if (privateStreamingService != null)
            return privateStreamingService.disconnect().mergeWith(streamingService.disconnect());
        return streamingService.disconnect();
    }

    @Override
    public boolean isAlive() {
        return streamingService.isSocketOpen()
                && (privateStreamingService == null || privateStreamingService.isSocketOpen());
    }

    @Override
    public Observable<Object> connectionSuccess() {
        return streamingService.subscribeConnectionSuccess();
    }

    @Override
    public Observable<Throwable> reconnectFailure() {
        return streamingService.subscribeReconnectFailure();
    }

    @Override
    public Observable<State> connectionStateObservable() {
        return streamingService.subscribeConnectionState();
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = super.getDefaultExchangeSpecification();
        spec.setShouldLoadRemoteMetaData(false);
        return spec;
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public StreamingTradeService getStreamingTradeService() {
//  Not yet implemented Trade side of Kraken Futures
        return null;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) {
        streamingService.useCompressedMessages(compressedMessages);
    }

    /**
     * Gets a WebSocketToken following
     * https://support.kraken.com/hc/en-us/articles/360034664311-How-to-subscribe-to-the-Kraken-WebSocket-private-feeds
     *
     * <p>Token requests should be made before any Websocket reconnection to avoid cases where the
     * token has become invalid due to issues on the Kraken side.
     *
     * <p>From Kraken support:
     *
     * <p>In theory WebSocket authentication tokens can last indefinitely, but in reality they do
     * sometimes expire causing an invalid session error. As an example, during a recent WebSocket API
     * upgrade, many authentication tokens became invalid (for no apparent reason to the token
     * owners), causing unexpected invalid session errors upon reconnecting/resubscribing after the
     * upgrade.
     *
     * @param accountServiceRaw account service to query new token against
     * @return token retrieved
     */
    public KrakenWebsocketToken authData(final KrakenAccountServiceRaw accountServiceRaw) {
        if (accountServiceRaw == null) {
            return null;
        }
        try {
            return accountServiceRaw.getKrakenWebsocketToken();
        } catch (IOException e) {
            logger.warn(
                    "Failed attempting to acquire Websocket AuthData needed for private data on"
                            + " websocket.  Will only receive public information via API",
                    e);
        }
        return null;
    }

    @Override
    public void resubscribeChannels() {
        logger.debug("Resubscribing channels");
        streamingService.resubscribeChannels();
        if (privateStreamingService != null)
            privateStreamingService.resubscribeChannels();
    }
}
