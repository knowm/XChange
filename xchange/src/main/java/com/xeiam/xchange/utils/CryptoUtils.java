/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.utils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Various cryptography utility methods
 */
public class CryptoUtils {

  public static String getNumericalNonce() {
    return Long.toString(new Date().getTime());
  }

  public static byte[] getByteArrayNonce() {
    return getNumericalNonce().getBytes();
  }

  public static String getBase64Nonce() {
    return Base64.encodeBase64String(getByteArrayNonce());
  }

  /**
   * Compute signature
   * 
   * @param algorithm
   * @param baseString
   * @param key
   * @return
   * @throws GeneralSecurityException
   * @throws UnsupportedEncodingException
   */
  public static String computeSignature(String algorithm, String baseString, byte[] key) throws GeneralSecurityException, UnsupportedEncodingException {

    SecretKey secretKey = null;
    secretKey = new SecretKeySpec(key, algorithm);
    Mac mac = Mac.getInstance(algorithm);
    mac.init(secretKey);
    mac.update(baseString.getBytes());
    return new String(Base64.encodeBase64(mac.doFinal())).trim();
  }

}
