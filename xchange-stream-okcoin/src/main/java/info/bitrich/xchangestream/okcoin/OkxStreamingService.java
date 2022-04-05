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
    protected void handleMessage(JsonNode message) {
        super.handleMessage(message);
    }

    @Override
    public void messageHandler(String message) {
        LOG.debug("Received message: {}", message);
        JsonNode jsonNode;

        // Parse incoming message to JSON
        try {
            jsonNode = objectMapper.readTree(message);
        } catch (IOException e) {
            if (!"pong".equals(message)) {
                LOG.error("Error parsing incoming message to JSON: {}", message);
            }
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
        return message.has("arg") ?
                (message.get("arg").has("channel") ? message.get("arg").get("channel").asText() : "")
                : "";
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        return "{\n" +
                "    \"op\": \"subscribe\",\n" +
                "    \"args\": [{\n" +
                "        \"channel\": \"candle1D\",\n" +
                "        \"instId\": \"BTC-USDT\"\n" +
                "    }]\n" +
                "}\n";
    }

    @Override
    public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
        return null;
    }
}
