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
package com.xeiam.xchange.rest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.Ticker;

/**
 * @author Matija Mazi
 */
public class RestInvocationHandlerTest {

  @Test
  public void testInvoke() throws Exception {

    TestRestInvocationHandler testHandler = new TestRestInvocationHandler(ExampleService.class);
    ExampleService proxy = RestProxyFactory.createProxy(ExampleService.class, testHandler);

    proxy.buy("john", "secret", new BigDecimal("3.14"), new BigDecimal("10.00"));
    assertRequestData(testHandler, "https://example.com/api/2/buy/", HttpMethod.POST, Order.class, "user=john&password=secret&amount=3.14&price=10.00", null);

    proxy.buy("john", "secret", new BigDecimal("3.14"), null);
    assertRequestData(testHandler, "https://example.com/api/2/buy/", HttpMethod.POST, Order.class, "user=john&password=secret&amount=3.14", null);

    proxy.withdrawBitcoin("john", "secret", new BigDecimal("3.14"), "mybitcoinaddress");
    assertRequestData(testHandler, "https://example.com/api/2/bitcoin_withdrawal/john?amount=3.14&address=mybitcoinaddress", HttpMethod.POST, Object.class, "password=secret", null);

    proxy.getTicker("btc", "usd");
    assertRequestData(testHandler, "https://example.com/api/2/btc_usd/ticker", HttpMethod.GET, Ticker.class, "", null);

    proxy.getInfo(1000L, 2000L);
    assertRequestData(testHandler, "https://example.com/api/2", HttpMethod.POST, Object.class, "method=getInfo", null);
  }

  @Test
  public void testHttpBasicAuth() throws Exception {

    TestRestInvocationHandler testHandler = new TestRestInvocationHandler(ExampleService.class);
    ExampleService proxy = RestProxyFactory.createProxy(ExampleService.class, testHandler);

    proxy.testBasicAuth(new BasicAuthCredentials("Aladdin", "open sesame"), 23);
    HashMap<String, String> authHeaders = new HashMap<String, String>();
    authHeaders.put("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
    assertRequestData(testHandler, "https://example.com/api/2/auth?param=23", HttpMethod.GET, Object.class, "", authHeaders);
  }

  private void assertRequestData(TestRestInvocationHandler testHandler, String url, HttpMethod httpMethod, Class resultClass, String postBody, Map<String, String> headers) {

    assertEquals(url, testHandler.restRequestData.url);
    assertEquals(httpMethod, testHandler.restRequestData.httpMethod);
    assertEquals(resultClass, testHandler.restRequestData.returnType);
    assertEquals(postBody, testHandler.restRequestData.params.getRequestBody());
    if (headers != null) {
      assertEquals(headers, testHandler.restRequestData.params.getHttpHeaders());
    }
  }

  @Test
  public void testJsonBody() throws Exception {

    TestRestInvocationHandler testHandler = new TestRestInvocationHandler(ExampleService.class);
    ExampleService proxy = RestProxyFactory.createProxy(ExampleService.class, testHandler);

    proxy.testJsonBody(Ticker.TickerBuilder.newInstance().withTradableIdentifier("BTC").withVolume(new BigDecimal("1023.23")).build());
    assertEquals("{\"tradableIdentifier\":\"BTC\",\"last\":null,\"bid\":null,\"ask\":null,\"high\":null,\"low\":null,\"volume\":1023.23,\"timestamp\":", testHandler.restRequestData.params
        .getRequestBody().substring(0, 114));

  }

  @Test
  public void testRootPathService() throws Exception {

    TestRestInvocationHandler testHandler = new TestRestInvocationHandler(RootPathService.class);
    RootPathService proxy = RestProxyFactory.createProxy(RootPathService.class, testHandler);

    proxy.cancel("424");
    assertRequestData(testHandler, "https://example.com/cancel?id=424", HttpMethod.DELETE, Order.class, "", null);
  }

  private static class TestRestInvocationHandler extends RestInvocationHandler {

    private RestRequestData restRequestData;

    public TestRestInvocationHandler(Class<?> restInterface) {

      super(restInterface, "https://example.com");
    }

    @Override
    protected Object invokeHttp(RestRequestData restRequestData) {

      this.restRequestData = restRequestData;
      return null;
    }
  }
}
