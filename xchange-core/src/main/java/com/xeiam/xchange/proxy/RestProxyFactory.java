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

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.HttpTemplate;
import org.codehaus.jackson.map.ObjectMapper;

import java.lang.reflect.Proxy;

/**
 * @author Matija Mazi <br/>
 */
public class RestProxyFactory {

  /**
   * Create a proxy implementation of restInterface.
   *
   * The interface must be annotated with jax-rs annotations. @Path, @GET, @POST, @QueryParam, @FormParam currently partially supported.
   *
   * @param restInterface The interface to implment.
   * @param <I> The interface to implement.
   * @return a proxy implementation of restInterface.
   */
  public static <I> I createProxy(Class<I> restInterface, HttpTemplate httpTemplate, ExchangeSpecification exchangeSpecification, ObjectMapper mapper) {

    RestInvocationHandler restInvocationHandler = new RestInvocationHandler(httpTemplate, exchangeSpecification, mapper, restInterface);
    Object proxy = Proxy.newProxyInstance(restInterface.getClassLoader(), new Class[]{restInterface}, restInvocationHandler);
    //noinspection unchecked
    return (I) proxy;
  }
}
