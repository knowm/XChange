package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.okcoin.dto.WebSocketMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import org.knowm.xchange.exceptions.ExchangeException;

import java.io.IOException;

public class OkCoinStreamingService extends JsonNettyStreamingService {

    public OkCoinStreamingService(String apiUrl) {
        super(apiUrl);
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
        if (message.get("success") != null) {
            boolean success = message.get("success").asBoolean();
            if (!success) {
                super.handleError(message, new ExchangeException("Error code: " + message.get("errorcode").asText()));
            }
            return;
        }
        super.handleMessage(message);
    }
}
