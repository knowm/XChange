package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.okcoin.dto.WebSocketMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.ObservableEmitter;
import org.knowm.xchange.exceptions.ExchangeException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OkCoinStreamingService extends JsonNettyStreamingService {

    private Timer pingPongTimer = null;


    private List<ObservableEmitter<Long>> delayEmitters = new LinkedList<>();

    public OkCoinStreamingService(String apiUrl) {
        super(apiUrl);
    }

    @Override
    public Completable connect() {
        Completable conn = super.connect();
        return conn.andThen((CompletableSource) (completable) -> {
            try {
                if (pingPongTimer != null) {
                    pingPongTimer.cancel();
                }
                pingPongTimer = new Timer("OkexPingPong", false);
                pingPongTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        OkCoinStreamingService.this.sendMessage("{\"event\":\"ping\"}");
                    }
                }, 15 * 1000, 15 * 1000);
                completable.onComplete();
            } catch (Exception e) {
                completable.onError(e);
            }
        });
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        return message.get("channel").asText();
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        WebSocketMessage webSocketMessage = new WebSocketMessage("addChannel", channelName);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(webSocketMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        WebSocketMessage webSocketMessage = new WebSocketMessage("removeChannel", channelName);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(webSocketMessage);
    }

    @Override
    protected void handleMessage(JsonNode message) {
        if (message.get("event") != null && "pong".equals(message.get("event").asText()) ) {
            // ignore pong message
            return;
        }
        JsonNode data = message.get("data");
        if (data != null) {
            if (data.has("result")) {
                boolean success = data.get("result").asBoolean();
                if (!success) {
                    super.handleError(message, new ExchangeException("Error code: " + data.get("error_code").asText()));
                }
                return;
            }
            if (data.has("timestamp")) {
                for (ObservableEmitter<Long> emitter : delayEmitters) {
                    emitter.onNext(System.currentTimeMillis() - data.get("timestamp").asLong());
                }
            }
        }
        super.handleMessage(message);
    }

    public void addDelayEmitter(ObservableEmitter<Long> delayEmitter) {
        delayEmitters.add(delayEmitter);
    }
}
