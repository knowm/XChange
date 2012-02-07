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

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoUtilsTest {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(CryptoUtils.class);

  @Test
  public void base64Encode() throws UnsupportedEncodingException {

    String string2Encode = "VGhpcyBpcyBhbiBlbmNvZGVkIHN0cmluZw==";
    log.debug("string2Encode: " + string2Encode);
    String expectedResult = "This is an encoded string";
    log.debug("expectedResult: " + expectedResult);
    // byte[] result = Base64.decodeBase64(string2Encode.getBytes("UTF-8"));
    byte[] resultAsByteArray = Base64.decodeBase64(string2Encode.getBytes());
    String result = new String(resultAsByteArray);

    log.debug("resultAsByteArray: " + resultAsByteArray);
    log.debug("result: " + result);

    Assert.assertEquals(result, expectedResult);

  }

  @Test
  public void testSignature() throws UnsupportedEncodingException, GeneralSecurityException {

    // method signature
    // String algorithm, String baseString, byte[] key
    String algorithm = "HmacSHA512";
    String baseString = "nonce=1328626350245256";
    String secretKey = "YYY";
    String restSign = CryptoUtils.computeSignature(algorithm, baseString, secretKey);
    log.debug("Rest-Sign    : " + restSign);
    String expectedResult = "tZStTi75ISnfQNbLCgplfobePAEm/RX++Qp+LaHpuCSrjZ/L5+S6dABaqvKBUGYCkp4gHdm9DtdysmSjiJ760g==";
    log.debug("Expected-Sign: " + expectedResult);

    Assert.assertEquals(restSign, expectedResult);
  }
}
