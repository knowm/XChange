package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkxStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(OkxStreamingService.class);

    private final Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);

    private Disposable pingPongSubscription;

    public OkxStreamingService(String apiUrl) {
        super(apiUrl);
    }

    @Override
    public Completable connect() {
        Completable conn = super.connect();
        return conn.andThen(
            (CompletableSource)
                (completable) -> {
                try {
                    if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
                        pingPongSubscription.dispose();
                    }

                    pingPongSubscription = pingPongSrc.subscribe(o ->this.sendMessage("ping"));
                    completable.onComplete();
                } catch (Exception e) {
                    completable.onError(e);
                }
        });
    }

    @Override
    public void messageHandler(String message) {
        LOG.debug("Received message: {}", message);
        JsonNode jsonNode;

        // Parse incoming message to JSON
        try {
            jsonNode = objectMapper.readTree(message);
        } catch (IOException e) {
            if ("pong".equals(message)) {
                // ping pong message
                return;
            }
            LOG.error("Error parsing incoming message to JSON: {}", message);
            return;
        }

        if (processArrayMessageSeparately() && jsonNode.isArray()) {
            // In case of array - handle every message separately.
            for (JsonNode node : jsonNode) {
                handleMessage(node);
            }
        } else {
            handleMessage(jsonNode);
        }
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        if (message.has("event")) return message.get("event").asText();
        return message.has("arg") ?
                (message.get("arg").has("channel") ? message.get("arg").get("channel").asText() : "")
                : "";
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        if (args.length != 1) throw new IOException("SubscribeMessage: Insufficient arguments");

        return objectMapper.writeValueAsString(args[0]);
    }

    @Override
    public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
        String s = objectMapper.writeValueAsString(args[0]);
        return String.format("{\"op\": \"unsubscribe\", \"args\": %s}", s);
    }
}
