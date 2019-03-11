/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.coinmate.service;

import java.math.BigInteger;
import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

/**
 * This is based on BitstampDigest
 *
 * @author Martin Stachon
 */
public class CoinmateDigest extends BaseParamsDigest {

  private final String clientId;
  private final String publicApiKey;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param clientId
   * @param publicApiKey @throws IllegalArgumentException if key is invalid (cannot be
   *     base-64-decoded or the decoded key is invalid).
   */
  private CoinmateDigest(String secretKeyBase64, String clientId, String publicApiKey) {

    super(secretKeyBase64, HMAC_SHA_256);
    this.clientId = clientId;
    this.publicApiKey = publicApiKey;
  }

  public static CoinmateDigest createInstance(
      String secretKeyBase64, String clientId, String publicApiKey) {

    return secretKeyBase64 == null
        ? null
        : new CoinmateDigest(secretKeyBase64, clientId, publicApiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    Mac mac256 = getMac();
    mac256.update(restInvocation.getParamValue(FormParam.class, "nonce").toString().getBytes());
    mac256.update(clientId.getBytes());
    mac256.update(publicApiKey.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();
  }
}
