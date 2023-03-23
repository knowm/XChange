package org.knowm.xchange.bittrex.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import jakarta.ws.rs.HeaderParam;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

public class BittrexDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private BittrexDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BittrexDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BittrexDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA-512");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException(e);
    }

    String content = restInvocation.getRequestBody();
    String contentHash = DigestUtils.bytesToHex(md.digest(content.getBytes()));

    // In the signature calculation, Bittrex expects the original unencoded URL.
    // That is not accessible from a RestInvocation, so we have to decode it here.
    String uri = urlDecode(restInvocation.getInvocationUrl());
    Long timestamp = (Long) restInvocation.getParamValue(HeaderParam.class, "Api-Timestamp");
    String method = restInvocation.getHttpMethod();

    String preSign = timestamp + uri + method + contentHash;

    Mac mac = getMac();
    mac.update(preSign.getBytes());

    return DigestUtils.bytesToHex(mac.doFinal());
  }

  /**
   * Decodes the URL-encoded string and returns the plain text. The converse of
   * si.mazi.rescu.Params#urlEncode(java.lang.String, boolean).
   *
   * @param data Encoded text.
   * @return Plain text.
   */
  static String urlDecode(String data) {
    try {
      return URLDecoder.decode(data, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, fix the code.", e); // should not happen
    }
  }
}
