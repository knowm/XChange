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
package com.xeiam.xchange.mtgox.v1.service;

import java.lang.annotation.Annotation;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.proxy.AllParams;
import com.xeiam.xchange.proxy.Params;

public class MtGoxHmacPostBodyDigestTest {

  private static final Logger log = LoggerFactory.getLogger(MtGoxHmacPostBodyDigestTest.class);

  @Test
  public void testSignature() throws GeneralSecurityException {

    String secretKey = "9WkB3zUil6h5pXrqUX7XT57c+g2rxxemeGYv3aBSW4hlkwSIgmul+mC3yxwU8fPtQsR8jTpyI2xo7WznjhTf4g==";

    Map<Class<? extends Annotation>, Params> paramsMap = new HashMap<Class<? extends Annotation>, Params>();
    paramsMap.put(FormParam.class, Params.of("nonce", 1328626350245256L));

    String restSign = MtGoxHmacPostBodyDigest.createInstance(secretKey).digestParams(new AllParams(paramsMap));
    log.debug("Rest-Sign    : " + restSign);
    String expectedResult = "eNjLVoVh6LVQfzgv7qFMCL48b5d2Qd1gvratXGA76W6+g46Jl9TNkiTCHks5sLXjfAQ1rGnvWxRHu6pYjC5FSQ==";
    log.debug("Expected-Sign: " + expectedResult);

    Assert.assertEquals(restSign, expectedResult);
  }
}
