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

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.*;

/**
 * @author Matija Mazi
 */
public class RestRequestData implements Serializable {

  private static final List<Class<? extends Annotation>> HTTP_METHOD_ANNS = Arrays.asList(GET.class, POST.class, PUT.class, OPTIONS.class, HEAD.class, DELETE.class);

  protected final Class<?> returnType;
  protected final RestMethodMetadata params;
  protected final HttpMethod httpMethod;
  protected final String url;

  /**
   * private Constructor
   * 
   * @param returnType
   * @param params
   * @param httpMethod
   * @param url
   */
  public RestRequestData(Class<?> returnType, RestMethodMetadata params, HttpMethod httpMethod, String url) {

    this.returnType = returnType;
    this.params = params;
    this.httpMethod = httpMethod;
    this.url = url;
  }

  static RestRequestData create(Method method, Object[] args, String baseUrl, String intfacePath) {

    RestMethodMetadata params = RestMethodMetadata.createInstance(method, args);
    Path pathAnn = method.getAnnotation(Path.class);
    String path = pathAnn == null ? null : params.getPath(pathAnn.value());
    HttpMethod httpMethod = getHttpMethod(method);
    String url1 = getUrl(baseUrl, path, intfacePath, params.getQueryString());
    return new RestRequestData(method.getReturnType(), params, httpMethod, url1);
  }

  static HttpMethod getHttpMethod(Method method) {

    HttpMethod httpMethod = null;
    for (Class<? extends Annotation> m : HTTP_METHOD_ANNS) {
      if (method.isAnnotationPresent(m)) {
        if (httpMethod != null) {
          throw new IllegalArgumentException("Method is annotated with more than one HTTP-method annotation: " + method);
        }
        httpMethod = HttpMethod.valueOf(m.getSimpleName());
      }
    }
    if (httpMethod == null) {
      throw new IllegalArgumentException("Method must be annotated with a HTTP-method annotation: " + method);
    }
    return httpMethod;
  }

  private static String getUrl(String baseUrl, String method, String intfacePath, String queryString) {

    // TODO make more robust in terms of path separator ('/') handling
    // (Use UriBuilder?)
    String completeUrl = baseUrl;
    completeUrl = appendIfNotEmpty(completeUrl, intfacePath, "/");
    completeUrl = appendIfNotEmpty(completeUrl, method, "/");
    completeUrl = appendIfNotEmpty(completeUrl, queryString, "?");
    return completeUrl;
  }

  private static String appendIfNotEmpty(String url, String next, String separator) {

    if (next != null && next.length() > 0 && !next.equals("/")) {
      url += separator + next;
    }
    return url;
  }

}
