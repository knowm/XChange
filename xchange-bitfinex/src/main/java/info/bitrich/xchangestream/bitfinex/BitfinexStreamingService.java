package info.bitrich.xchangestream.bitfinex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.bitrich.xchangestream.bitfinex.dto.BitfinexAuthRequestStatus;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuth;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthBalance;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthOrder;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthPreTrade;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthTrade;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketUnSubscriptionMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static org.knowm.xchange.service.BaseParamsDigest.HMAC_SHA_384;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Created by Lukas Zaoralek on 7.11.17.
 */
public class BitfinexStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(BitfinexStreamingService.class);

    static final String CHANNEL_USER_POSITIONS = "userPositions";
    static final String CHANNEL_USER_BALANCE_UPDATES = "userBalanceUpdates";
    static final String CHANNEL_USER_BALANCES = "userBalances";
    static final String CHANNEL_USER_ORDER_UPDATES = "userOrderUpdates";
    static final String CHANNEL_USER_ORDERS = "userOrders";
    static final String CHANNEL_USER_TRADES = "userTrades";
    static final String CHANNEL_USER_PRE_TRADES = "userPreTrades";

    private static final String INFO = "info";
    private static final String ERROR = "error";
    private static final String CHANNEL_ID = "chanId";
    private static final String SUBSCRIBED = "subscribed";
    private static final String UNSUBSCRIBED = "unsubscribed";
    private static final String ERROR_CODE = "code";
    private static final String AUTH = "auth";
    private static final String STATUS = "status";
    private static final String MESSAGE = "msg";
    private static final String EVENT = "event";
    private static final String VERSION = "version";

    private final PublishSubject<BitfinexWebSocketAuthPreTrade> subjectPreTrade = PublishSubject.create();
    private final PublishSubject<BitfinexWebSocketAuthTrade> subjectTrade = PublishSubject.create();
    private final PublishSubject<BitfinexWebSocketAuthOrder> subjectOrder = PublishSubject.create();
    private final PublishSubject<BitfinexWebSocketAuthBalance> subjectBalance = PublishSubject.create();

    private static final int SUBSCRIPTION_FAILED = 10300;

    private String apiKey;
    private String apiSecret;

    private final Map<String, String> subscribedChannels = new HashMap<>();

    private final SynchronizedValueFactory<Long> nonceFactory;

    public BitfinexStreamingService(String apiUrl,
                                    SynchronizedValueFactory<Long> nonceFactory) {
        super(apiUrl, Integer.MAX_VALUE);
        this.nonceFactory = nonceFactory;
    }

    @Override
    protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
        return null;
    }

    @Override
    public void messageHandler(String message) {
        LOG.debug("Received message: {}", message);
        JsonNode jsonNode;

        // Parse incoming message to JSON
        try {
            jsonNode = objectMapper.readTree(message);
        } catch (IOException e) {
            LOG.error("Error parsing incoming message to JSON: {}", message);
            return;
        }

        handleMessage(jsonNode);
    }

    @Override
    protected void handleMessage(JsonNode message) {

        if (message.isArray()) {
            String type = message.get(1).asText();
            if (type.equals("hb")) {
                return;
            }
        }

        JsonNode event = message.get(EVENT);
        if (event != null) {
            switch (event.textValue()) {
                case INFO:
                    JsonNode version = message.get(VERSION);
                    if (version != null) {
                        LOG.debug("Bitfinex websocket API version: {}.", version.intValue());
                    }
                    if (isAuthenticated())
                        auth();
                    break;
                case AUTH:
                    if (message.get(STATUS).textValue().equals(BitfinexAuthRequestStatus.FAILED.name())) {
                        LOG.error("Authentication error: {}", message.get(MESSAGE));
                    }
                    if (message.get(STATUS).textValue().equals(BitfinexAuthRequestStatus.OK.name())) {
                        LOG.info("Authenticated successfully");
                    }
                    break;
                case SUBSCRIBED: {
                    String channel = message.get("channel").asText();
                    String pair = message.get("pair").asText();
                    String channelId = message.get(CHANNEL_ID).asText();
                    try {
                        String subscriptionUniqueId = getSubscriptionUniqueId(channel, pair);
                        subscribedChannels.put(channelId, subscriptionUniqueId);
                        LOG.debug("Register channel {}: {}", subscriptionUniqueId, channelId);
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                    }
                    break;
                }
                case UNSUBSCRIBED: {
                    String channelId = message.get(CHANNEL_ID).asText();
                    subscribedChannels.remove(channelId);
                    break;
                }
                case ERROR:
                    if (message.get("code").asInt() == SUBSCRIPTION_FAILED) {
                    LOG.error("Error with message: " + message.get("symbol") + " " + message.get("msg"));
                        return;
                    }
                    super.handleError(message, new ExchangeException("Error code: " + message.get(ERROR_CODE).asText()));
                    break;
            }
        } else {
            try {
                if ("0".equals(getChannelNameFromMessage(message)) && message.isArray() && message.size() == 3) {
                    processAuthenticatedMessage(message);
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to get channel name from message", e);
            }
            super.handleMessage(message);
        }
    }

    private void processAuthenticatedMessage(JsonNode message) {
        String type = message.get(1).asText();
        JsonNode object = message.get(2);
        switch (type) {
            case "te":
                subjectPreTrade.onNext(BitfinexStreamingAdapters.adaptPreTrade(object));
                break;
            case "tu":
                subjectTrade.onNext(BitfinexStreamingAdapters.adaptTrade(object));
                break;
            case "os":
                BitfinexStreamingAdapters.adaptOrders(object).forEach(subjectOrder::onNext);
                break;
            case "on":
            case "ou":
            case "oc":
                subjectOrder.onNext(BitfinexStreamingAdapters.adaptOrder(object));
                break;
            case "ws":
                BitfinexStreamingAdapters.adaptBalances(object).forEach(subjectBalance::onNext);
                break;
            case "wu":
                subjectBalance.onNext(BitfinexStreamingAdapters.adaptBalance(object));
                break;
            default:
                // In case bitfinex adds new channels, ignore
        }
    }

    @Override
    public String getSubscriptionUniqueId(String channelName, Object... args) {
        if (args.length > 0) {
            return channelName + "-" + args[0].toString();
        } else {
            return channelName;
        }
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        String chanId;
        if (message.has(CHANNEL_ID)) {
            chanId = message.get(CHANNEL_ID).asText();
        } else {
            chanId = message.get(0).asText();
        }
        if (chanId == null) throw new IOException("Can't find CHANNEL_ID value");
        String subscribedChannel = subscribedChannels.get(chanId);
        if (subscribedChannel != null)
            return subscribedChannel;
        return chanId; // In case bitfinex adds new channels, just fallback to the name in the message
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        BitfinexWebSocketSubscriptionMessage subscribeMessage = null;
        if (args.length == 1) {
            subscribeMessage =
                    new BitfinexWebSocketSubscriptionMessage(channelName, (String) args[0]);
        } else if (args.length == 3) {
            subscribeMessage =
                    new BitfinexWebSocketSubscriptionMessage(channelName, (String) args[0], (String) args[1],
                            (String) args[2]);
        }
        if (subscribeMessage == null) throw new IOException("SubscribeMessage: Insufficient arguments");

        return objectMapper.writeValueAsString(subscribeMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        String channelId = null;
        for (Map.Entry<String, String> entry : subscribedChannels.entrySet()) {
            if (entry.getValue().equals(channelName)) {
                channelId = entry.getKey();
                break;
            }
        }

        if (channelId == null) throw new IOException("Can't find channel unique name");

        BitfinexWebSocketUnSubscriptionMessage subscribeMessage =
                new BitfinexWebSocketUnSubscriptionMessage(channelId);
        ObjectMapper objectMapper = StreamingObjectMapperHelper.getObjectMapper();
        return objectMapper.writeValueAsString(subscribeMessage);
    }

    void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    boolean isAuthenticated() {
        return StringUtils.isNotEmpty(apiKey);
    }

    private void auth() {
        long nonce = nonceFactory.createValue();
        String payload = "AUTH" + nonce;
        String signature;
        try {
            Mac macEncoder = Mac.getInstance(HMAC_SHA_384);
            SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA_384);
            macEncoder.init(secretKeySpec);
            byte[] result = macEncoder.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            signature = DatatypeConverter.printHexBinary(result);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            LOG.error("auth. Sign failed error={}", e.getMessage());
            return;
        }
        BitfinexWebSocketAuth message = new BitfinexWebSocketAuth(
                apiKey, payload, String.valueOf(nonce), signature.toLowerCase()
        );
        sendObjectMessage(message);
    }

    Observable<BitfinexWebSocketAuthOrder> getAuthenticatedOrders() {
        return subjectOrder.share();
    }

    Observable<BitfinexWebSocketAuthPreTrade> getAuthenticatedPreTrades() {
        return subjectPreTrade.share();
    }

    Observable<BitfinexWebSocketAuthTrade> getAuthenticatedTrades() {
        return subjectTrade.share();
    }

    Observable<BitfinexWebSocketAuthBalance> getAuthenticatedBalances() {
        return subjectBalance.share();
    }
}
