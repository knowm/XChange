package info.bitrich.xchangestream.gateio;

import org.knowm.xchange.gateio.service.GateioV4Digest;
import org.knowm.xchange.utils.DigestUtils;

import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;

public class GateioStreamingAuthHelper {

  private final GateioV4Digest gateioV4Digest;

  public GateioStreamingAuthHelper(String apiSecret) {
    gateioV4Digest = GateioV4Digest.createInstance(apiSecret);
  }

  /**
   * Generates signature based on payload
   * Signature string concatenation method: channel=<channel>&event=<event>&time=<time>, where <channel>, <event>, <time>
   * are corresponding request information
   * Authentication information are sent in request body in field auth.
   */
  public String sign(String channel, String event, String timestamp) {
    Mac mac = gateioV4Digest.getMac();

    String payloadToSign = String.format("channel=%s&event=%s&time=%s", channel, event, timestamp);
    mac.update(payloadToSign.getBytes(StandardCharsets.UTF_8));

    return DigestUtils.bytesToHex(mac.doFinal());
  }

  /**
   * Generates signature based on payload for userTrade service
   * Signature string concatenation method: "<event>\n<channel>\n<req_param>\n<timestamp>",
   * where <event>, <channel>,<req_param>, <timestamp> are corresponding request information
   * req_param in login channel always empty string
   * Authentication information are sent in request body in field payload.
   *
   */
  public String signUserTrade(String channel, String event, String timestamp, String req_param) {
    Mac mac = gateioV4Digest.getMac();

    String payloadToSign = String.format("%s\n%s\n%s\n%s", event, channel, req_param, timestamp);
    mac.update(payloadToSign.getBytes(StandardCharsets.UTF_8));

    return DigestUtils.bytesToHex(mac.doFinal());
  }
}
