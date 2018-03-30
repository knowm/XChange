package info.bitrich.xchangestream.service.pubnub;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.UnsupportedOperationException;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lukas Zaoralek on 14.11.17.
 */
public class PubnubStreamingService {
    private static final Logger LOG = LoggerFactory.getLogger(PubnubStreamingService.class);

    private final PubNub pubnub;
    private PNStatusCategory pnStatusCategory;
    private final Map<String, ObservableEmitter<JsonNode>> subscriptions = new ConcurrentHashMap<>();
    private final ObjectMapper mapper;

    public PubnubStreamingService(String publicKey) {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(publicKey);
        pubnub = new PubNub(pnConfiguration);
        pnStatusCategory = PNStatusCategory.PNDisconnectedCategory;
    }

    public Completable connect() {
        return Completable.create(e -> {
            pubnub.addListener(new SubscribeCallback() {
                @Override
                public void status(PubNub pubNub, PNStatus pnStatus) {
                    pnStatusCategory = pnStatus.getCategory();
                    LOG.debug("PubNub status: {} {}", pnStatusCategory.toString(), pnStatus.getStatusCode());
                    if (pnStatusCategory == PNStatusCategory.PNConnectedCategory) {
//              e.onComplete();
                    } else if (pnStatus.isError()) {
//              e.onError(pnStatus.getErrorData().getThrowable());
                    }
                }

                @Override
                public void message(PubNub pubNub, PNMessageResult pnMessageResult) {
                    String channelName = pnMessageResult.getChannel();
                    ObservableEmitter<JsonNode> subscription = subscriptions.get(channelName);
                    LOG.debug("PubNub Message: {}", pnMessageResult.toString());
                    if (subscription != null) {
                        JsonNode jsonMessage = null;
                        try {
                            jsonMessage = mapper.readTree(pnMessageResult.getMessage().toString());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        subscription.onNext(jsonMessage);
                    } else {
                        LOG.debug("No subscriber for channel {}.", channelName);
                    }
                }

                @Override
                public void presence(PubNub pubNub, PNPresenceEventResult pnPresenceEventResult) {
                    LOG.debug("PubNub Message: {}", pnPresenceEventResult.toString());
                }
            });
            e.onComplete();
        });
    }

    public Observable<JsonNode> subscribeChannel(String channelName) {
        LOG.info("Subscribing to channel {}.", channelName);
        return Observable.<JsonNode>create(e -> {
            if (!subscriptions.containsKey(channelName)) {
                subscriptions.put(channelName, e);
                pubnub.subscribe().channels(Collections.singletonList(channelName)).execute();
                LOG.debug("Subscribe channel: {}", channelName);
            }
        }).doOnDispose(() -> {
            LOG.debug("Unsubscribe channel: {}", channelName);
            pubnub.unsubscribe().channels(Collections.singletonList(channelName)).execute();
        }).share();
    }

    public Completable disconnect() {
        return Completable.create(completable -> {
            pubnub.disconnect();
            completable.onComplete();
        });
    }

    public boolean isAlive() {
        return (pnStatusCategory == PNStatusCategory.PNConnectedCategory);
    }

    public void useCompressedMessages(boolean compressedMessages) { throw new UnsupportedOperationException(); }
}
