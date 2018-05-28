package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.okcoin.dto.WebSocketMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import org.knowm.xchange.exceptions.ExchangeException;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class OkCoinStreamingService extends JsonNettyStreamingService {

    private Timer pingPongTimer = null;

    public OkCoinStreamingService(String apiUrl) {
        super(apiUrl);
    }

    @Override
    public Completable connect() {
        Completable conn = super.connect();
        return conn.andThen((CompletableSource)(completable) -> {
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
                }, 15*1000, 15*1000);
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
        if (message.get("data") != null) {
            if (message.get("data").has("result")) {
                boolean success = message.get("data").get("result").asBoolean();
                if (!success) {
                    super.handleError(message, new ExchangeException("Error code: " + message.get("data").get("error_code").asText()));
                } else {
                    super.handleMessage(message);
                }
                return;
            }
        }
        super.handleMessage(message);
    }
}
