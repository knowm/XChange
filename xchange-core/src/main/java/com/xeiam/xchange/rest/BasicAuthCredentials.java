/*
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.rest;

import java.io.UnsupportedEncodingException;

import com.xeiam.xchange.utils.Base64;

/**
 * @author Matija Mazi <br/>
 *         This is used as support for HTTP Basic access authentication. Should be used a parameter of a REST interface method, annotated with @HeaderParam("Authorization"), eg: Result
 *         getResult(@HeaderParam("Authorization") BasicAuthCredentials credentials);
 */
public class BasicAuthCredentials implements ParamsDigest {

  private final String username, password;

  public BasicAuthCredentials(String username, String password) {

    this.username = username;
    this.password = password;
  }

  @Override
  public String digestParams(RestMethodMetadata restMethodMetadata) {

    // ignore restMethodMetadata, just need username & password
    try {
      byte[] inputBytes = (username + ":" + password).getBytes("ISO-8859-1");
      return "Basic " + Base64.encodeBytes(inputBytes);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Unsupported encoding, fix the code.", e);
    }
  }
}
