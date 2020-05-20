package org.knowm.xchange.bittrex.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

public class BittrexDigestV3 extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private BittrexDigestV3(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BittrexDigestV3 createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BittrexDigestV3(secretKeyBase64);
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

    String uri = restInvocation.getInvocationUrl();
    Long timestamp = (Long) restInvocation.getParamValue(HeaderParam.class, "Api-Timestamp");
    String method = restInvocation.getHttpMethod();

    String preSign = timestamp + uri + method + contentHash;

    // var preSign = [timestamp, uri, method, contentHash, subaccountId].join('');
    // var signature = CryptoJS.HmacSHA512(preSign, apiSecret).toString(CryptoJS.enc.Hex);

    Mac mac = getMac();
    mac.update(preSign.getBytes());

    return DigestUtils.bytesToHex(mac.doFinal());
  }
}
