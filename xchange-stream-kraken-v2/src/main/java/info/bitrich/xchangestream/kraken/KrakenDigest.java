package info.bitrich.xchangestream.kraken;

import jakarta.ws.rs.FormParam;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Mac;
import lombok.SneakyThrows;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public final class KrakenDigest extends BaseParamsDigest {

  /**
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private KrakenDigest(byte... secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static KrakenDigest createInstance(String secretKeyBase64) {
    if (secretKeyBase64 != null) {
      return new KrakenDigest(
          Base64.getDecoder().decode(secretKeyBase64.getBytes(StandardCharsets.UTF_8)));
    }

    return null;
  }

  @SneakyThrows
  @Override
  public String digestParams(RestInvocation restInvocation) {

    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

    sha256.update(
        restInvocation
            .getParamValue(FormParam.class, "nonce")
            .toString()
            .getBytes(StandardCharsets.UTF_8));
    sha256.update(restInvocation.getRequestBody().getBytes(StandardCharsets.UTF_8));

    Mac mac512 = getMac();
    mac512.update(("/" + restInvocation.getPath()).getBytes(StandardCharsets.UTF_8));
    mac512.update(sha256.digest());

    return Base64.getEncoder().encodeToString(mac512.doFinal()).trim();
  }
}
