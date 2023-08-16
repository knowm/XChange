package org.knowm.xchange.krakenfutures.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

/** @author Jean-Christophe Laruelle */
public class KrakenFuturesDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private KrakenFuturesDigest(byte[] secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static KrakenFuturesDigest createInstance(String secretKeyBase64) {

    if (secretKeyBase64 != null) {
      return new KrakenFuturesDigest(Base64.getDecoder().decode(secretKeyBase64.getBytes()));
    } else {
      return null;
    }
  }

  public String signMessage(String message){
    MessageDigest sha256;
    try {
      sha256 = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(
              "Illegal algorithm (SHA-256) for post body digest. Check the implementation.");
    }

    sha256.update(message.getBytes());
    Mac mac512 = getMac();
    mac512.update(sha256.digest());
    return Base64.getEncoder().encodeToString(mac512.doFinal()).trim();
  }
  @Override
  public String digestParams(RestInvocation restInvocation) {

    try{
      String message = URLDecoder.decode(
              restInvocation.getParamsMap().get(QueryParam.class).asQueryString(),
              StandardCharsets.UTF_8.name())
              + restInvocation.getParamValue(HeaderParam.class, "Nonce").toString()
              + restInvocation.getPath();

      return signMessage(message);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(
              "Bad encoding found on request query while hashing (SHA256) the POST data.", e);
    }


  }
}