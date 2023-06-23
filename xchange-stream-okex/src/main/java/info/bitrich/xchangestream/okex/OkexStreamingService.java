package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.okex.dto.OkexLoginMessage;
import info.bitrich.xchangestream.okex.dto.OkexSubscribeMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.okex.dto.OkexInstType;
import org.knowm.xchange.service.BaseParamsDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkexStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(OkexStreamingService.class);
    private static final String LOGIN_SIGN_METHOD = "GET";
    private static final String LOGIN_SIGN_REQUEST_PATH = "/users/self/verify";

    private static final String SUBSCRIBE = "subscribe";
    private static final String UNSUBSCRIBE = "unsubscribe";

    public static final String TRADES = "trades";
    public static final String ORDERBOOK = "books";
    public static final String ORDERBOOK5 = "books5";
    public static final String FUNDING_RATE = "funding-rate";
    public static final String TICKERS = "tickers";
    public static final String USERTRADES = "orders";

    private final Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);

    private Disposable pingPongSubscription;

    private final ExchangeSpecification xSpec;

    public OkexStreamingService(String apiUrl, ExchangeSpecification exchangeSpecification) {
        super(apiUrl);
        this.xSpec = exchangeSpecification;
    }

    @Override
    public Completable connect() {
        Completable conn = super.connect();
        return conn.andThen(
            (CompletableSource)
                (completable) -> {
                try {
                    if(xSpec.getApiKey() != null){
                        login();
                    }

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

    public void login() throws JsonProcessingException {
        Mac mac;
        try {
            mac = Mac.getInstance(BaseParamsDigest.HMAC_SHA_256);
            final SecretKey secretKey =
                    new SecretKeySpec(xSpec.getSecretKey().getBytes(StandardCharsets.UTF_8), BaseParamsDigest.HMAC_SHA_256);
            mac.init(secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new ExchangeException("Invalid API secret", e);
        }
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String toSign = timestamp + LOGIN_SIGN_METHOD + LOGIN_SIGN_REQUEST_PATH;
        String sign = Base64.getEncoder().encodeToString(mac.doFinal(toSign.getBytes(StandardCharsets.UTF_8)));

        OkexLoginMessage message = new OkexLoginMessage();
        String passphrase = xSpec.getExchangeSpecificParametersItem("passphrase").toString();
        OkexLoginMessage.LoginArg loginArg = new OkexLoginMessage.LoginArg(xSpec.getApiKey(), passphrase, timestamp, sign);
        message.getArgs().add(loginArg);

        this.sendMessage(objectMapper.writeValueAsString(message));
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
    protected String getChannelNameFromMessage(JsonNode message) {
        String channelName = "";
        if(message.has("arg")){
            if(message.get("arg").has("channel") && message.get("arg").has("instId")){
                channelName = message.get("arg").get("channel").asText()+message.get("arg").get("instId").asText();
            }
        }
        return channelName;
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        return objectMapper.writeValueAsString(new OkexSubscribeMessage(SUBSCRIBE, Collections.singletonList(getTopic(channelName))));
    }

    @Override
    public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
        return objectMapper.writeValueAsString(new OkexSubscribeMessage(UNSUBSCRIBE, Collections.singletonList(getTopic(channelName))));
    }

    private OkexSubscribeMessage.SubscriptionTopic getTopic(String channelName){
        if(channelName.contains(ORDERBOOK5)){
            return new OkexSubscribeMessage.SubscriptionTopic(ORDERBOOK5,null,null,channelName.replace(ORDERBOOK5,""));
        } else if(channelName.contains(ORDERBOOK)){
            return new OkexSubscribeMessage.SubscriptionTopic(ORDERBOOK,null,null,channelName.replace(ORDERBOOK,""));
        } else if(channelName.contains(TRADES)){
            return new OkexSubscribeMessage.SubscriptionTopic(TRADES,null,null,channelName.replace(TRADES,""));
        } else if(channelName.contains(TICKERS)){
            return new OkexSubscribeMessage.SubscriptionTopic(TICKERS,null,null,channelName.replace(TICKERS,""));
        } else if (channelName.contains(USERTRADES)){
            return new OkexSubscribeMessage.SubscriptionTopic(USERTRADES, OkexInstType.ANY,null,channelName.replace(USERTRADES,""));
        } else if(channelName.contains(FUNDING_RATE)){
            return new OkexSubscribeMessage.SubscriptionTopic(FUNDING_RATE, null,null,channelName.replace(FUNDING_RATE,""));
        } else {
            throw new NotYetImplementedForExchangeException("ChannelName: "+channelName+" has not implemented yet on "+this.getClass().getSimpleName());
        }
    }

    public void pingPongDisconnectIfConnected() {
        if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
            pingPongSubscription.dispose();
        }
    }
}
