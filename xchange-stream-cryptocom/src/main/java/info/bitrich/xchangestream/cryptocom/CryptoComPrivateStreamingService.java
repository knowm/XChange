package info.bitrich.xchangestream.cryptocom;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Collections;
import org.knowm.xchange.cryptocom.CryptoComDigest;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Crypto.com Exchange v1 user WebSocket feed requires signing in with {@code public/auth}
 * immediately after connecting, before any {@code user.*} channel can be subscribed. Following the
 * same connect -&gt; authenticate -&gt; (re)subscribe sequence used for reconnects, {@link
 * #resubscribeChannels()} - which the framework calls right after every successful connection - is
 * overridden to send the login message instead of resubscribing; channels are (re)subscribed once
 * the auth confirmation arrives, see {@link #handleMessage(JsonNode)}.
 */
public class CryptoComPrivateStreamingService extends CryptoComStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(CryptoComPrivateStreamingService.class);
  private static final String AUTH_METHOD = "public/auth";

  private final String apiKey;
  private final String apiSecret;

  public CryptoComPrivateStreamingService(String apiUrl, String apiKey, String apiSecret) {
    super(apiUrl);
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
  }

  @Override
  public void resubscribeChannels() {
    sendAuthMessage();
  }

  private void sendAuthMessage() {
    long id = nextRequestId();
    long nonce = System.currentTimeMillis();
    String signature =
        CryptoComDigest.signature(
            AUTH_METHOD, id, apiKey, nonce, Collections.emptyMap(), apiSecret);

    ObjectNode message = objectMapper.createObjectNode();
    message.put("id", id);
    message.put("method", AUTH_METHOD);
    message.put("api_key", apiKey);
    message.put("sig", signature);
    message.put("nonce", nonce);
    sendObjectMessage(message);
  }

  @Override
  protected void handleMessage(JsonNode message) {
    if (AUTH_METHOD.equals(message.path("method").asText(""))) {
      if (message.path("code").asInt(-1) == 0) {
        LOG.info("Crypto.com user WebSocket authenticated");
        super.resubscribeChannels();
      } else {
        ExchangeSecurityException authFailure =
            new ExchangeSecurityException(
                "Crypto.com user WebSocket authentication failed: " + message);
        LOG.error(authFailure.getMessage());
        // Surface the failure to any already-subscribed user.* channels instead of leaving
        // their observables silently waiting forever for data that will never arrive.
        channels.keySet().forEach(channel -> handleChannelError(channel, authFailure));
      }
      return;
    }
    super.handleMessage(message);
  }
}
