package info.bitrich.xchangestream.gateio;

import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import org.knowm.xchange.gateio.service.GateioV4Digest;
import org.knowm.xchange.utils.DigestUtils;

public class GateioStreamingAuthHelper {

  private final GateioV4Digest gateioV4Digest;

  public GateioStreamingAuthHelper(String apiSecret) {
    gateioV4Digest = GateioV4Digest.createInstance(apiSecret);
  }


  /**
   * Generates signature based on payload
   */
  public String sign(String channel, String event, String timestamp) {
    Mac mac = gateioV4Digest.getMac();

    String payloadToSign = String.format("channel=%s&event=%s&time=%s", channel, event, timestamp);
    mac.update(payloadToSign.getBytes(StandardCharsets.UTF_8));

    return DigestUtils.bytesToHex(mac.doFinal());
  }

}
