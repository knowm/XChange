package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.okcoin.dto.okx.OkxLoginMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class OkxStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(OkxStreamingService.class);
    private static final String LOGIN_SIGN_METHOD = "GET";
    private static final String LOGIN_SIGN_REQUEST_PATH = "/users/self/verify";

    private final Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);

    private Disposable pingPongSubscription;

    private final ExchangeSpecification xSpec;

    public OkxStreamingService(String apiUrl, ExchangeSpecification exchangeSpecification) {
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

        OkxLoginMessage message = new OkxLoginMessage();
        String passphrase = (String)xSpec.getExchangeSpecificParametersItem("passphrase");
        OkxLoginMessage.LoginArg loginArg = new OkxLoginMessage.LoginArg(xSpec.getApiKey(), passphrase, timestamp, sign);
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
        if (args.length != 1) throw new IOException("UnsubscribeMessage: Insufficient arguments");

        return objectMapper.writeValueAsString(args[0]);
    }
}
