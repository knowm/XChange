package info.bitrich.xchangestream.service.wamp;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import info.bitrich.xchangestream.service.exception.NotConnectedException;
import io.reactivex.Completable;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.subjects.BehaviorSubject;
import ws.wamp.jawampa.PubSubData;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClient.State;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.connection.IWampConnectorProvider;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class WampStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(WampStreamingService.class);

  private final String uri;
  private final String realm;
  private WampClient client;
  private WampClient.State connectedState;

  public WampStreamingService(String uri, String realm) {
    this.uri = uri;
    this.realm = realm;
  }

  public Completable connect() {
    return Completable.create(
        completable -> {
          IWampConnectorProvider connectorProvider = new NettyWampClientConnectorProvider();

          try {
            // Create a builder and configure the client
            WampClientBuilder builder = new WampClientBuilder();
            builder
                .withConnectorProvider(connectorProvider)
                .withUri(uri)
                .withRealm(realm)
                .withInfiniteReconnects()
                .withReconnectInterval(5, TimeUnit.SECONDS);
            // Create a client through the builder. This will not immediatly start
            // a connection attempt
            client = builder.build();
          } catch (Exception e) {
            // Catch exceptions that will be thrown in case of invalid configuration
            completable.onError(e);
            return;
          }

          client
              .statusChanged()
              .subscribe(
                  state -> {
                    LOG.debug("State changed: {}", state);
                    if (state instanceof WampClient.DisconnectedState
                        && connectedState instanceof WampClient.ConnectingState) {
                      if (((WampClient.DisconnectedState) state).disconnectReason() != null) {
                        completable.onError(
                            ((WampClient.DisconnectedState) state).disconnectReason());
                      } else {
                        completable.onError(
                            new IllegalStateException("Cannot connect to the exchange."));
                      }
                    }

                    connectedState = state;

                    if (state instanceof WampClient.ConnectedState) {
                      completable.onComplete();
                    }
                  });

          client.open();
        });
  }

  public Observable<PubSubData> subscribeChannel(String channel) {
    if (!(connectedState instanceof WampClient.ConnectedState)) {
      return Observable.error(new NotConnectedException());
    }

    return RxJavaInterop.toV2Observable(client.makeSubscription(channel));
  }

  public boolean isSocketOpen() {
    // WampClient was initiated with infinite reconnects attempts, so we can just always return
    // 'true'
    return !((BehaviorSubject<State>) client.statusChanged()).hasCompleted();
  }

  public void useCompressedMessages(boolean compressedMessages) {
    throw new UnsupportedOperationException();
  }
}
