/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.bter.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import javax.crypto.Mac;

import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;

/**
 * This may be used as the value of a @HeaderParam, @QueryParam or @PathParam to
 * create a digest of the post body (composed of @FormParam's). Don't use as the
 * value of a @FormParam, it will probably cause an infinite loop.
 * <p/>
 * This may be used for REST APIs where some parameters' values must be digests of other parameters. An example is the MtGox API v1, where the Rest-Sign header parameter must be a digest of the
 * request body (which is composed of
 * 
 * @FormParams).
 */
public class BTERHmacPostBodyDigest extends BaseParamsDigest {

  /**
   * Constructor
   * 
   * @param secretKeyBase64
   * @throws IllegalArgumentException
   *           if key is invalid (cannot be base-64-decoded or the decoded
   *           key is invalid).
   */
  private BTERHmacPostBodyDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BTERHmacPostBodyDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BTERHmacPostBodyDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    try {
      String postBody = restInvocation.getRequestBody();
      Mac mac = getMac();
      mac.update(postBody.getBytes("UTF-8"));
      return String.format("%0128x", new BigInteger(1, mac.doFinal()));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, check the code.", e);
    }
    // return Base64.encodeBytes(mac.doFinal()).trim();
  }
}
