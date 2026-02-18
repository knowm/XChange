package org.knowm.xchange.kraken.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.FormParam;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

/**
 * @author Benedikt Bünz
 */
public class KrakenDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private KrakenDigest(byte[] secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static KrakenDigest createInstance(String secretKeyBase64) {

    if (secretKeyBase64 != null) {
      return new KrakenDigest(Base64.getDecoder().decode(secretKeyBase64.getBytes()));
    } else return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    MessageDigest sha256;
    try {
      sha256 = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(
          "Illegal algorithm for post body digest. Check the implementation.");
    }

    // Get nonce - try FormParam first, then extract from JSON body if not available
    String nonce;
    Object nonceParam = restInvocation.getParamValue(FormParam.class, "nonce");
    if (nonceParam != null) {
      nonce = nonceParam.toString();
    } else {
      // For JSON requests, extract nonce from request body
      try {
        String requestBody = restInvocation.getRequestBody();
        if (requestBody != null && requestBody.contains("nonce")) {
          ObjectMapper mapper = new ObjectMapper();
          JsonNode jsonNode = mapper.readTree(requestBody);
          JsonNode nonceNode = jsonNode.get("nonce");
          if (nonceNode != null) {
            nonce = nonceNode.asText();
          } else {
            throw new RuntimeException("Nonce not found in request body");
          }
        } else {
          throw new RuntimeException("Nonce not found in request");
        }
      } catch (Exception e) {
        throw new RuntimeException("Failed to extract nonce from request: " + e.getMessage(), e);
      }
    }

    sha256.update(nonce.getBytes());
    sha256.update(restInvocation.getRequestBody().getBytes());

    Mac mac512 = getMac();
    mac512.update(("/" + restInvocation.getPath()).getBytes());
    mac512.update(sha256.digest());

    return Base64.getEncoder().encodeToString(mac512.doFinal()).trim();
  }
}
