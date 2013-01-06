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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.xeiam.xchange.ExchangeException;

/**
 * Various cryptography utility methods
 */
public class CryptoUtils {

  /**
   * @return A numerical nonce using the number of milliseconds since the start of the epoch
   */
  public static String getNumericalNonce() {

    return Long.toString(new Date().getTime());
  }

  /**
   * Decode a base 64 string
   * 
   * @param base64Data The data to decode
   * @return The decoded data as a String
   * @deprecated Use {@link }Base64} directly
   */
  @Deprecated
  public static String decodeBase64String(String base64Data) {

    try {
      return new String(Base64.decode(base64Data.getBytes()));
    } catch (IOException e) {
      throw new ExchangeException("Could not decode Base 64 string", e);
    }
  }

  /**
   * Compute signature
   * 
   * @param algorithm The algorithm to use (e.g. "HmacSHA512")
   * @param baseString The data to sign
   * @param secretKeyString The secret key to use for signing
   * @return A base 64 encoded signature
   * @throws GeneralSecurityException If something goes wrong
   */
  public static String computeSignature(String algorithm, String baseString, String secretKeyString) throws GeneralSecurityException {

    try {
      SecretKey secretKey = new SecretKeySpec(Base64.decode(secretKeyString.getBytes()), algorithm);
      Mac mac = Mac.getInstance(algorithm);
      mac.init(secretKey);
      mac.update(baseString.getBytes());
      return Base64.encodeBytes(mac.doFinal()).trim();
    } catch (IOException e) {
      throw new ExchangeException("Could not decode Base 64 string", e);
    }
  }

}
