/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.vircurex.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

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
public class VircurexSha2Digest {

  private static final String SHA_256 = "SHA-256";
  private MessageDigest digest;
  private final String secretWord;

  /**
   * Constructor
   * 
   * @param aSecretWord
   * @throws IllegalArgumentException
   *           if key is invalid (cannot be base-64-decoded or the decoded key
   *           is invalid).
   */
  public VircurexSha2Digest(String aSecretWord, String aUserName, String aTimeStamp, String aNonce, String aMethod, String anOrderType, String anOrderAmount, String aTransactionCurrency,
      String aLimitPrice, String aTradeableCurrency) throws IllegalArgumentException {

    try {
      digest = MessageDigest.getInstance(SHA_256);
      digest
          .update((aSecretWord + ";" + aUserName + ";" + aTimeStamp + ";" + aNonce + ";" + aMethod + ";" + anOrderType + ";" + anOrderAmount + ";" + aTransactionCurrency + ";" + aLimitPrice + ";" + aTradeableCurrency)
              .getBytes());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    secretWord = DatatypeConverter.printHexBinary(digest.digest());
  }

  public VircurexSha2Digest(String aSecretWord, String aUserName, String aTimeStamp, String aNonce, String aMethod) {

    try {
      digest = MessageDigest.getInstance(SHA_256);
      digest.update((aSecretWord + ";" + aUserName + ";" + aTimeStamp + ";" + aNonce + ";" + aMethod).getBytes());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    secretWord = DatatypeConverter.printHexBinary(digest.digest());
  }

  public VircurexSha2Digest(String aSecretWord, String aUserName, String aTimeStamp, String aNonce, String aMethod, String anOrderId) {

    try {
      digest = MessageDigest.getInstance(SHA_256);
      digest.update((aSecretWord + ";" + aUserName + ";" + aTimeStamp + ";" + aNonce + ";" + aMethod + ";" + anOrderId).getBytes());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    secretWord = DatatypeConverter.printHexBinary(digest.digest());
  }

  @Override
  public String toString() {

    return secretWord;
  }

}
