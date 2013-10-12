/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btcchina.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import si.mazi.rescu.BasicAuthCredentials;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.btcchina.BTCChinaUtils;

/**
 * @author David Yam
 */
public class BTCChinaDigest implements ParamsDigest {

  private static final String HMAC_SHA1 = "HmacSHA1";
  private final Mac mac;
  private final String exchangeAccessKey;

  /**
   * Constructor
   * 
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private BTCChinaDigest(String exchangeAccessKey, String exchangeSecretKey) throws IllegalArgumentException {

    try {
      SecretKey secretKey = new SecretKeySpec(exchangeSecretKey.getBytes(), HMAC_SHA1);
      mac = Mac.getInstance(HMAC_SHA1);
      mac.init(secretKey);
      this.exchangeAccessKey = exchangeAccessKey;
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException("Invalid key for hmac initialization.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
    }
  }

  public static BTCChinaDigest createInstance(String exchangeAccessKey, String exchangeSecretKey) throws IllegalArgumentException {

    return exchangeSecretKey == null ? null : new BTCChinaDigest(exchangeAccessKey, exchangeSecretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String tonce = restInvocation.getHttpHeaders().get("Json-Rpc-Tonce");
    String requestJson = restInvocation.getRequestBody();

    String id = "", method = "", params = "";
    try {
      Pattern regex = Pattern.compile("\\{\"id\":([0-9]*),\"method\":\"([^\"]*)\",\"params\":\\[([^\\]]*)\\]\\}", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
      Matcher regexMatcher = regex.matcher(requestJson);
      if (regexMatcher.find()) {
        id = regexMatcher.group(1);
        method = regexMatcher.group(2);
        params = regexMatcher.group(3);
      }
    } catch (PatternSyntaxException ex) {
      // Syntax error in the regular expression
    }

    String signature = String.format("tonce=%s&accesskey=%s&requestmethod=%s&id=%s&method=%s&params=%s", tonce, exchangeAccessKey, "post", id, method, params);
    byte[] hash = mac.doFinal(signature.getBytes());

    BasicAuthCredentials auth = new BasicAuthCredentials(exchangeAccessKey, BTCChinaUtils.bytesToHex(hash));

    return auth.digestParams(restInvocation);
  }

}