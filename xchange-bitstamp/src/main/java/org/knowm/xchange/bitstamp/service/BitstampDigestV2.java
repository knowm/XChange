package org.knowm.xchange.bitstamp.service;

import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import java.math.BigInteger;

/** @author Benedikt Bünz */
public class BitstampDigestV2 extends BaseParamsDigest {

  private final String apiKey;
  private final String baseUrlHost = "www.bitstamp.net";

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or
   *     the decoded key is invalid).
   */
  private BitstampDigestV2(String secretKeyBase64, String apiKey) {

    super(secretKeyBase64, HMAC_SHA_256);
    this.apiKey = "BITSTAMP "+ apiKey;
  }

  public static BitstampDigestV2 createInstance(
      String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new BitstampDigestV2(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    Mac mac256 = getMac();
    mac256.update(apiKey.getBytes());

    System.out.println("restInvocation.getHttpMethod() " + restInvocation.getHttpMethod());
    mac256.update(restInvocation.getHttpMethod().getBytes()); //HTTP VERB

    System.out.println("baseUrlHost " + baseUrlHost);
    mac256.update(baseUrlHost.getBytes()); //url Host

    String okPath = "/" + restInvocation.getPath();
    System.out.println("restInvocation.getPath() " + okPath);
    mac256.update(okPath.getBytes()); //url Path

    System.out.println("restInvocation.getQueryString() " + restInvocation.getQueryString());
    mac256.update(restInvocation.getQueryString().getBytes()); //url Query

    String contentType = restInvocation.getReqContentType();

    if (contentType != null) {
      System.out.println("restInvocation.getReqContentType() " + contentType);
      mac256.update(contentType.getBytes()); //content type
    }

    System.out.println("X-Auth-Nonce " + restInvocation.getParamValue(HeaderParam.class, "X-Auth-Nonce").toString());
    mac256.update(restInvocation.getParamValue(HeaderParam.class, "X-Auth-Nonce").toString().getBytes());

    System.out.println("X-Auth-Timestamp " + restInvocation.getParamValue(HeaderParam.class, "X-Auth-Timestamp").toString());
    mac256.update(restInvocation.getParamValue(HeaderParam.class, "X-Auth-Timestamp").toString().getBytes());

    System.out.println("X-Auth-Version " + restInvocation.getParamValue(HeaderParam.class, "X-Auth-Version").toString());
    mac256.update(restInvocation.getParamValue(HeaderParam.class, "X-Auth-Version").toString().getBytes());

    System.out.println("restInvocation.getRequestBody() " + restInvocation.getRequestBody());
    mac256.update(restInvocation.getRequestBody().getBytes());


    return String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();
  }
}
