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
package com.xeiam.xchange.coinbase.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.HeaderParam;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

/**
 * @author jamespedwards42
 */
public class CoinbaseDigest implements ParamsDigest {

  private final Mac mac256;

  private CoinbaseDigest(final String secretKey) throws IllegalArgumentException {

    try {
      mac256 = Mac.getInstance("HmacSHA256");
      mac256.init(new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256"));
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException("Invalid key for hmac initialization.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, check the code, should be using UTF-8.", e);
    }
  }

  public static CoinbaseDigest createInstance(final String secretKey) throws IllegalArgumentException {

    return secretKey == null ? null : new CoinbaseDigest(secretKey);
  }

  @Override
  public String digestParams(final RestInvocation restInvocation) {

    final String message = restInvocation.getParamValue(HeaderParam.class, "ACCESS_NONCE").toString() + restInvocation.getInvocationUrl() + restInvocation.getRequestBody();

    mac256.update(message.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal()));
  }
}