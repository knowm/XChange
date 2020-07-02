package org.knowm.xchange.bitcoinde.v4.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

/** @author kaiserfr */
public class BitcoindeDigest extends BaseParamsDigest {
  private final String apiKey;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param apiKey
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private BitcoindeDigest(String secretKeyBase64, String apiKey) {
    super(secretKeyBase64, HMAC_SHA_256);
    this.apiKey = apiKey;
  }

  public static BitcoindeDigest createInstance(String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new BitcoindeDigest(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    // Step 1: concatenate URL with query string
    String completeURL = restInvocation.getInvocationUrl();

    // Step 2: create md5-Hash of the POST-Parameters for the HMAC-data
    String httpMethod = restInvocation.getHttpMethod();
    String requestBody = restInvocation.getRequestBody();

    String md5;
    if ("POST".equals(httpMethod)) {
      md5 = getMD5(requestBody);
    } else {
      md5 = "d41d8cd98f00b204e9800998ecf8427e"; // no post params for GET methods
    }

    // Step 3: concat HMAC-input

    // http_method+'#'+uri+'#'+api_key+'#'+nonce+'#'+post_parameter_md5_hashed_url_encoded_query_string
    String nonce = restInvocation.getHttpHeadersFromParams().get("X-API-NONCE");
    String hmac_data = String.format("%s#%s#%s#%s#%s", httpMethod, completeURL, apiKey, nonce, md5);

    // Step 3: Create actual sha256-HMAC
    Mac mac256 = getMac();
    mac256.update(hmac_data.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal()));
  }

  private String getMD5(String original) {
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    md.update(original.getBytes());
    byte[] digest = md.digest();
    StringBuffer sb = new StringBuffer();
    for (byte b : digest) {
      sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
  }
}
