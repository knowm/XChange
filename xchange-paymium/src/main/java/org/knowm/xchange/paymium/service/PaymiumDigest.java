package org.knowm.xchange.paymium.service;

import java.math.BigInteger;
import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import org.knowm.xchange.paymium.CryptoTools;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

public class PaymiumDigest extends BaseParamsDigest {

  /*
    final String sign = CryptoTools.hmacDigest(time.toString() + url, secret, "HmacSHA256");
          requestProperties.put("Api-Key", api);
          requestProperties.put("Api-Signature", sign);
          requestProperties.put("Api-Nonce", time.toString());
  */

  /*  public static BitstampDigest createInstance(
          String secretKeyBase64, String clientId, String apiKey) {

    return secretKeyBase64 == null ? null : new BitstampDigest(secretKeyBase64, clientId, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    Mac mac256 = getMac();
    mac256.update(restInvocation.getParamValue(FormParam.class, "nonce").toString().getBytes());
    mac256.update(clientId.getBytes());
    mac256.update(apiKey.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();
  }*/

  private String signature;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private PaymiumDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
    signature = secretKeyBase64;
  }

  public static PaymiumDigest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new PaymiumDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String invocationUrl =
        restInvocation.getParamValue(HeaderParam.class, "Api-Nonce").toString()
            + restInvocation.getInvocationUrl();
    Mac mac = getMac();
    mac.update(invocationUrl.getBytes());

    final String sign = CryptoTools.hmacDigest(invocationUrl, signature, "HmacSHA256");
    System.out.println(sign);
    System.out.println(DigestUtils.bytesToHex(getMac().doFinal().toString().getBytes()));
    System.out.println(String.format("%064x", new BigInteger(1, mac.doFinal())));

    return sign;
    // return DigestUtils.bytesToHex(getMac().doFinal().toString().getBytes());
    // return String.format("%064x", new BigInteger(1, mac.doFinal()));
  }
}
