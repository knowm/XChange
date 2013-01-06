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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.codehaus.jackson.map.ObjectMapper;

import com.xeiam.xchange.utils.HttpTemplate;

/**
 * @author Matija Mazi <br/>
 */
public class RestInvocationHandler implements InvocationHandler {

  private final HttpTemplate httpTemplate;
  private final ObjectMapper mapper;
  private final String intfacePath;
  private final String baseUrl;

  public RestInvocationHandler(HttpTemplate httpTemplate, ObjectMapper mapper, Class<?> restInterface, String url) {

    this.httpTemplate = httpTemplate;
    this.mapper = mapper;
    this.intfacePath = restInterface.getAnnotation(Path.class).value();
    this.baseUrl = url;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    Class<?> returnType = method.getReturnType();
    AllParams params = AllParams.createInstance(method, args);
    String path = method.getAnnotation(Path.class).value();
    path = params.getPath(path);
    if (method.isAnnotationPresent(GET.class)) {
      return getForJsonObject(path, returnType, params);
    } else if (method.isAnnotationPresent(POST.class)) {
      return postForJsonObject(path, returnType, params);
    } else {
      throw new IllegalArgumentException("Only methods annotated with @GET or @POST supported.");
    }
  }

  private String getUrl(String method, String queryString) {

    // TODO make more robust in terms of path separator ('/') handling
    String completeUrl = String.format("%s/%s/%s", this.baseUrl, intfacePath, method);
    if (queryString != null && !queryString.isEmpty()) {
      completeUrl += "?" + queryString;
    }
    return completeUrl;
  }

  private <T> T getForJsonObject(String methodPath, Class<T> returnType, AllParams params) {

    String queryString = params.getQueryString();
    String url = getUrl(methodPath, queryString);

    return httpTemplate.getForJsonObject(url, returnType, mapper, new HashMap<String, String>());
  }

  private <T> T postForJsonObject(String methodPath, Class<T> returnType, AllParams allParams) {

    return httpTemplate.postForJsonObject(getUrl(methodPath, null), returnType, allParams.getPostBody(), mapper, allParams.getHttpHeaders());
  }
}
