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

import java.math.BigDecimal;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.utils.HttpTemplate;

import static org.junit.Assert.assertEquals;

/**
 * @author Matija Mazi <br/>
 */
public class RestInvocationHandlerTest {

  @Test
  public void testInvoke() throws Exception {

    TestRestInvocationHandler testHandler = new TestRestInvocationHandler();
    ExampleService proxy = RestProxyFactory.createProxy(ExampleService.class, testHandler);

    proxy.buy("john", "secret", new BigDecimal("3.14"), new BigDecimal("10.00"));
    assertRequestData(testHandler, "https://example.com/api/2/buy/", HttpTemplate.HttpMethod.POST, Order.class, "user=john&password=secret&amount=3.14&price=10.00");

    proxy.buy("john", "secret", new BigDecimal("3.14"), null);
    assertRequestData(testHandler, "https://example.com/api/2/buy/", HttpTemplate.HttpMethod.POST, Order.class, "user=john&password=secret&amount=3.14");

    proxy.withdrawBitcoin("john", "secret", new BigDecimal("3.14"), "mybitcoinaddress");
    assertRequestData(testHandler, "https://example.com/api/2/bitcoin_withdrawal/john?amount=3.14&address=mybitcoinaddress", HttpTemplate.HttpMethod.POST, Object.class, "password=secret");

    proxy.getTicker("btc", "usd");
    assertRequestData(testHandler, "https://example.com/api/2/btc_usd/ticker", HttpTemplate.HttpMethod.GET, Ticker.class, "");
  }

  private void assertRequestData(TestRestInvocationHandler testHandler, String url, HttpTemplate.HttpMethod httpMethod, Class resultClass, String postBody) {

    assertEquals(url, testHandler.restRequestData.url);
    assertEquals(httpMethod, testHandler.restRequestData.httpMethod);
    assertEquals(resultClass, testHandler.restRequestData.returnType);
    assertEquals(postBody, testHandler.restRequestData.params.getPostBody());
  }

  private static class TestRestInvocationHandler extends RestInvocationHandler {

    private RestRequestData restRequestData;

    public TestRestInvocationHandler() {

      super(new HttpTemplate(), new ObjectMapper(), ExampleService.class, "https://example.com");
    }

    @Override
    protected Object invokeHttp(RestRequestData restRequestData) {

      this.restRequestData = restRequestData;
      return null;
    }
  }
}
