package org.knowm.xchange.gateio.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
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
public class GateioHmacPostBodyDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private GateioHmacPostBodyDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static GateioHmacPostBodyDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new GateioHmacPostBodyDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    try {
      String postBody = restInvocation.getRequestBody();

      // little hack here. the post body to create the signature mus not contain the url-encoded
      // parameters, they must be in plain form
      // passing ie the white space inside the withdraw method (required for XLM and XRP ... to pass
      // the tag) results in a plus sing '+', which is the correct encoding, but in this case the
      // signature is not created correctly.
      // the expected signature must be created using plain parameters. here we simply replace the +
      // by a white space, should be fine for now
      // see https://support.gate.io/hc/en-us/articles/360000808354-How-to-Withdraw-XRP
      postBody = postBody.replace('+', ' ');
      Mac mac = getMac();
      mac.update(postBody.getBytes("UTF-8"));
      return String.format("%0128x", new BigInteger(1, mac.doFinal()));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, check the code.", e);
    }
    // return Base64.encodeBytes(mac.doFinal()).trim();
  }
}
