package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.lgo.dto.LgoSubscription;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import org.knowm.xchange.lgo.service.LgoSignatureService;

import java.io.IOException;
import java.time.Duration;

import static info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper.*;

public class LgoStreamingService extends JsonNettyStreamingService {

    private final LgoSignatureService signatureService;
    private final String apiUrl;

    LgoStreamingService(LgoSignatureService signatureService, String apiUrl) {
        super(apiUrl, Integer.MAX_VALUE, Duration.ofSeconds(10), Duration.ofSeconds(15), 1);
        this.apiUrl = apiUrl;
        this.signatureService = signatureService;
    }

    @Override
    protected void handleIdle(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new PingWebSocketFrame());
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) {
        String channel = message.get("channel").asText();
        if (channel.equals("trades") || channel.equals("level2") || channel.equals("user")) {
            return channel + "-" + message.get("product_id").textValue();
        }
        return channel;
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        return getObjectMapper().writeValueAsString(LgoSubscription.subscribe(channelName));
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        return getObjectMapper().writeValueAsString(LgoSubscription.unsubscribe(channelName));
    }

    @Override
    protected DefaultHttpHeaders getCustomHeaders() {
        DefaultHttpHeaders headers = super.getCustomHeaders();
        String timestamp = String.valueOf(System.currentTimeMillis());
        headers.add("X-LGO-DATE", timestamp);
        String auth = signatureService.digestSignedUrlHeader(this.apiUrl, timestamp);
        headers.add("Authorization", auth);
        return headers;
    }
}
