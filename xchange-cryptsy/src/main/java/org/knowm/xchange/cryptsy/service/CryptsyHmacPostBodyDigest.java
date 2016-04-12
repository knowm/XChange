package org.knowm.xchange.cryptsy.service;

import java.math.BigInteger;

import javax.crypto.Mac;

import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

/**
 * This may be used as the value of a @HeaderParam, @QueryParam or @PathParam to create a digest of the post body (composed of @FormParam's). Don't
 * use as the value of a @FormParam, it will probably cause an infinite loop.
 * <p>
 * This may be used for REST APIs where some parameters' values must be digests of other parameters. An example is the MtGox API v1, where the
 * Rest-Sign header parameter must be a digest of the request body (which is composed of
 * 
 * @FormParams).
 *               </p>
 */
public class CryptsyHmacPostBodyDigest extends BaseParamsDigest {

  /**
   * Constructor
   * 
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private CryptsyHmacPostBodyDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static CryptsyHmacPostBodyDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new CryptsyHmacPostBodyDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();

    Mac mac = getMac();
    byte[] digest = mac.doFinal(postBody.getBytes());
    BigInteger hash = new BigInteger(1, digest);
    String hmac = hash.toString(16);

    if (hmac.length() % 2 != 0) {
      hmac = "0" + hmac;
    }

    return hmac;
  }
}
