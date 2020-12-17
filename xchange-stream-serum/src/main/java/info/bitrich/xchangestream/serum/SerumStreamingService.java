package info.bitrich.xchangestream.serum;

import com.fasterxml.jackson.databind.JsonNode;
import com.knowm.xchange.serum.SerumAdapters;
import com.knowm.xchange.serum.SerumConfigs.SubscriptionType;
import com.knowm.xchange.serum.SerumConfigs.Commitment;
import info.bitrich.xchangestream.serum.dto.SerumWsSubscriptionMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;

public class SerumStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(SerumStreamingService.class);

    public SerumStreamingService(String apiUrl) {
        super(apiUrl);
    }

    public SerumStreamingService(String apiUrl,
                                 int maxFramePayloadLength,
                                 Duration connectionTimeout,
                                 Duration retryDuration,
                                 int idleTimeoutSeconds) {
        super(apiUrl, maxFramePayloadLength, connectionTimeout, retryDuration, idleTimeoutSeconds);
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        return null;
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Not enough args");
        }
        if (!(args[0] instanceof CurrencyPair)) {
            throw new IllegalArgumentException("arg[0] must be the currency pairs");
        }
        if (!(args[1] instanceof SubscriptionType)) {
            throw new IllegalArgumentException("arg[1] must be the subscription type");
        }
        final String account = SerumAdapters.toSolanaAddress((CurrencyPair) args[0]);
        final SubscriptionType subscriptionType = (SubscriptionType) args[1];
        final Commitment commitment = args[2] != null && args[2] instanceof Commitment ?
                (Commitment) args[2] : Commitment.max;

        return new SerumWsSubscriptionMessage(commitment, subscriptionType, account).buildMsg();
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        return null;
    }
}
