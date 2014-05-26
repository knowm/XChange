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
package com.xeiam.xchange.kraken.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;

import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.utils.Base64;

import com.xeiam.xchange.service.BaseParamsDigest;

/**
 * @author Benedikt Bünz
 */
public class KrakenDigest extends BaseParamsDigest {

  /**
   * Constructor
   * 
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private KrakenDigest(byte[] secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static KrakenDigest createInstance(String secretKeyBase64) {

    try {
      if (secretKeyBase64 != null)
        return new KrakenDigest(Base64.decode(secretKeyBase64.getBytes()));
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not decode Base 64 string", e);
    }
    return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    MessageDigest sha256;
    try {
      sha256 = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
    }
    sha256.update(restInvocation.getParamValue(FormParam.class, "nonce").toString().getBytes());
    sha256.update(restInvocation.getRequestBody().getBytes());

    Mac mac512 = getMac();
    mac512.update(("/" + restInvocation.getPath()).getBytes());
    mac512.update(sha256.digest());

    return Base64.encodeBytes(mac512.doFinal()).trim();
  }
}