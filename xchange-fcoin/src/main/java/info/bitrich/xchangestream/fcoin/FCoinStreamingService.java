package info.bitrich.xchangestream.fcoin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.fcoin.dto.FCoinMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.ObservableEmitter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FCoinStreamingService extends JsonNettyStreamingService {

        private List<ObservableEmitter<Long>> delayEmitters = new LinkedList<>();

        public FCoinStreamingService(String apiUrl) {
            super(apiUrl);
        }

        @Override
        protected String getChannelNameFromMessage(JsonNode message) throws IOException {
            if (!message.has("type")) {
                throw new IOException("No channel");
            }
            return message.get("type").asText();
        }

        @Override
        public String getSubscribeMessage(String channelName, Object... args) throws IOException {
            FCoinMessage FCoinMessage = new FCoinMessage(null, "sub", channelName);

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(FCoinMessage);
        }

        @Override
        public String getUnsubscribeMessage(String channelName) throws IOException {
            FCoinMessage FCoinMessage = new FCoinMessage(null, "unsub", channelName);

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(FCoinMessage);
        }

        @Override
        protected void handleMessage(JsonNode message) {
            if (message != null) {
                if (message.has("ts")) {
                    for (ObservableEmitter<Long> emitter : delayEmitters) {
                        emitter.onNext(System.currentTimeMillis() - message.get("timestamp").asLong());
                    }
                }
            }
            super.handleMessage(message);
        }

        public void addDelayEmitter(ObservableEmitter<Long> delayEmitter) {
            delayEmitters.add(delayEmitter);
        }

    }

