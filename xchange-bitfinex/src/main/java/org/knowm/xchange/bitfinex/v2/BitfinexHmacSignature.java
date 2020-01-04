package org.knowm.xchange.bitfinex.v2;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.ws.rs.HeaderParam;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

@Slf4j
public class BitfinexHmacSignature extends BaseParamsDigest {
  private static final String UTF_8 = "UTF-8";
  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private BitfinexHmacSignature(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_384);
  }

  public static BitfinexHmacSignature createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BitfinexHmacSignature(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation i) {

    // /api/${apiPath}${nonce}${JSON.stringify(body)}

    String path = i.getPath();
    try {
      path = URLDecoder.decode(path, "utf8");
    } catch (UnsupportedEncodingException e) {
      log.warn("Could not url decode the path {}.", path, e);
    }

    Object nonce = i.getParamValue(HeaderParam.class, BitfinexAuthenticated.BFX_NONCE);
    String body = i.getRequestBody();
    if (body == null || body.isEmpty()) {
      body = "{}";
    }
    String toEncode = "/api/" + path + nonce + body;

    try {
      byte[] sig = getMac().doFinal(toEncode.getBytes(UTF_8));
      String signature = DigestUtils.bytesToHex(sig);
      return signature;

    } catch (IllegalStateException | UnsupportedEncodingException e) {
      throw new ExchangeException("Could not sign the request", e);
    }
  }
}
