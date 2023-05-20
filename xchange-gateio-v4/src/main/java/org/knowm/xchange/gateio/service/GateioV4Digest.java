package org.knowm.xchange.gateio.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import javax.crypto.Mac;
import lombok.SneakyThrows;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

/**
 * This may be used as the value of a @HeaderParam, @QueryParam or @PathParam to create a digest of
 * the post body (composed of @FormParam's). Don't use as the value of a @FormParam, it will
 * probably cause an infinite loop.
 *
 * <p>This may be used for REST APIs where some parameters' values must be digests of other
 * parameters. An example is the MtGox API v1, where the Rest-Sign header parameter must be a digest
 * of the request body (which is composed of @FormParams).
 */
public class GateioV4Digest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private GateioV4Digest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static GateioV4Digest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new GateioV4Digest(secretKeyBase64);
  }

  @SneakyThrows
  @Override
  public String digestParams(RestInvocation restInvocation) {
    try {
      String postBody = restInvocation.getRequestBody();
      String method = restInvocation.getHttpMethod();
      String path = restInvocation.getPath();
      String query = restInvocation.getQueryString();

      String body = restInvocation.getRequestBody();
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      String hexedHashedBody = DigestUtils.bytesToHex(md.digest(body.getBytes()));

      String timestamp = restInvocation.getHttpHeadersFromParams().get("Timestamp");

      String payloadToSign = String.format("%s\n/%s\n%s\n%s\n%s", method, path, query, hexedHashedBody, timestamp);

      System.out.println(payloadToSign);

      Mac mac = getMac();
      mac.update(payloadToSign.getBytes("UTF-8"));
      return DigestUtils.bytesToHex(mac.doFinal());
//      return String.format("%0128x", new BigInteger(1, mac.doFinal()));

    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, check the code.", e);
    }
    // return Base64.encodeBytes(mac.doFinal()).trim();
  }
}
