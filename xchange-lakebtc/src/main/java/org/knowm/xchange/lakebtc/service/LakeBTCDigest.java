package org.knowm.xchange.lakebtc.service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.knowm.xchange.lakebtc.LakeBTCUtil;
import org.knowm.xchange.lakebtc.dto.LakeBTCRequest;
import org.knowm.xchange.service.BaseParamsDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.BasicAuthCredentials;
import si.mazi.rescu.RestInvocation;

/** User: cristian.lucaci Date: 10/3/2014 Time: 5:03 PM */
public class LakeBTCDigest extends BaseParamsDigest {

  private final Logger log = LoggerFactory.getLogger(LakeBTCDigest.class);

  private final String clientId;
  private final String apiKey;

  /**
   * Constructor
   *
   * @param secretKeyBase64 secretKeyBase64 key
   * @param clientId client ID, mail
   * @param secretKeyBase64 @throws IllegalArgumentException if key is invalid (cannot be
   *     base-64-decoded or the decoded key is invalid).
   */
  private LakeBTCDigest(String clientId, String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_1);
    this.clientId = clientId;
    this.apiKey = secretKeyBase64;
  }

  public static LakeBTCDigest createInstance(String clientId, String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new LakeBTCDigest(clientId, secretKeyBase64);
  }

  public static String makeSign(String data, String key) throws Exception {

    SecretKeySpec sign = new SecretKeySpec(key.getBytes(), HMAC_SHA_1);
    Mac mac = Mac.getInstance(HMAC_SHA_1);
    mac.init(sign);
    byte[] rawHmac = mac.doFinal(data.getBytes());

    return arrayHex(rawHmac);
  }

  private static String arrayHex(byte[] a) {
    StringBuilder sb = new StringBuilder();
    for (byte b : a) {
      sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String tonce = restInvocation.getHttpHeadersFromParams().get("Json-Rpc-Tonce");

    LakeBTCRequest request = null;
    for (Object param : restInvocation.getUnannanotatedParams()) {
      if (param instanceof LakeBTCRequest) {
        request = (LakeBTCRequest) param;
      }
    }

    if (request == null) {
      throw new IllegalArgumentException("No LakeBTCDigest found.");
    }

    final long id = request.getId();
    final String method = request.getRequestMethod();
    final String params = ""; // stripParams(request.getParams());

    String signature =
        String.format(
            "tonce=%s&accesskey=%s&requestmethod=%s&id=%d&method=%s&params=%s",
            tonce, clientId, method, id, request.getMethod(), params);
    log.debug("signature message: {}", signature);

    Mac mac = getMac();
    byte[] hash = mac.doFinal(signature.getBytes());

    BasicAuthCredentials auth = new BasicAuthCredentials(apiKey, LakeBTCUtil.bytesToHex(hash));

    return auth.digestParams(restInvocation);
  }
}
