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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Various cryptography utility methods
 */
public class CryptoUtils {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(CryptoUtils.class);

  /**
   * Creates a numerical nonce from the number of milliseconds since January 1, 1970, 00:00:00 GMT
   * 
   * @return
   */
  public static String getNumericalNonce() {
    String numericalNonce = Long.toString(new Date().getTime());
    // log.debug("numericalNonce= " + numericalNonce);
    return numericalNonce;
  }

  public static String decodeBase64String(String string2Encode) {

    byte[] resultAsByteArray = Base64.decodeBase64(string2Encode.getBytes());
    return new String(resultAsByteArray);
  }

  /**
   * Compute signature
   * 
   * @param algorithm
   * @param baseString
   * @param secretKeyString
   * @return
   * @throws GeneralSecurityException
   * @throws UnsupportedEncodingException
   */
  public static String computeSignature(String algorithm, String baseString, String secretKeyString) throws GeneralSecurityException, UnsupportedEncodingException {

    SecretKey secretKey = null;
    secretKey = new SecretKeySpec(Base64.decodeBase64(secretKeyString.getBytes()), algorithm);
    Mac mac = Mac.getInstance(algorithm);
    mac.init(secretKey);
    mac.update(baseString.getBytes());
    return new String(Base64.encodeBase64(mac.doFinal())).trim();
  }

  public static byte[] getBase64DecodedString(String base64String) {
    return Base64.decodeBase64(base64String);
  }

  // public static byte[] getByteArrayNonce() {
  // return getNumericalNonce().getBytes();
  // }
  //
  // public static String getBase64Nonce() {
  // return Base64.encodeBase64String(getByteArrayNonce());
  // }

}
