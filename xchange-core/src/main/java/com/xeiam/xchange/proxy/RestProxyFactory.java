/**
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
package com.xeiam.xchange.proxy;

import java.lang.reflect.Proxy;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Matija Mazi
 * @see #createProxy(Class, String, com.xeiam.xchange.proxy.HttpTemplate, org.codehaus.jackson.map.ObjectMapper)
 */
public class RestProxyFactory {

  /**
   * private Constructor
   */
  private RestProxyFactory() {

  }

  /**
   * Create a proxy implementation of restInterface. The interface must be annotated with jax-rs annotations. Basic support exists for {@link javax.ws.rs.Path}, {@link javax.ws.rs.GET},
   * {@link javax.ws.rs.POST}, {@link javax.ws.rs.QueryParam}, {@link javax.ws.rs.FormParam}, {@link javax.ws.rs.HeaderParam}, {@link javax.ws.rs.PathParam}.
   * 
   * @param restInterface The interface to implement
   * @param baseUrl The service base baseUrl
   * @return a proxy implementation of restInterface
   */
  public static <I> I createProxy(Class<I> restInterface, String baseUrl) {

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    RestInvocationHandler restInvocationHandler = new RestInvocationHandler(new HttpTemplate(), mapper, restInterface, baseUrl);
    return createProxy(restInterface, restInvocationHandler);
  }

  static <I> I createProxy(Class<I> restInterface, RestInvocationHandler restInvocationHandler) {

    Object proxy = Proxy.newProxyInstance(restInterface.getClassLoader(), new Class[] { restInterface }, restInvocationHandler);
    // noinspection unchecked
    return (I) proxy;
  }
}
