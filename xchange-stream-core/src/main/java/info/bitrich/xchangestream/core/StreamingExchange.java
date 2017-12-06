package info.bitrich.xchangestream.core;

import io.reactivex.Completable;
import org.knowm.xchange.Exchange;

public interface StreamingExchange extends Exchange {
    /**
     * Connects to the WebSocket API of the exchange.
     *
     * @param args Product subscription is used only in certain exchanges where you need to specify subscriptions during the connect phase.
     * @return {@link Completable} that completes upon successful connection.
     */
    Completable connect(ProductSubscription... args);

    /**
     * Disconnect from the WebSocket API.
     *
     * @return {@link Completable} that completes upon successful disconnect.
     */
    Completable disconnect();

    /**
     * Returns service that can be used to access market data.
     */
    StreamingMarketDataService getStreamingMarketDataService();
}
