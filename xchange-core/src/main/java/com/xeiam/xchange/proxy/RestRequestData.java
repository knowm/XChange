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

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.xeiam.xchange.utils.HttpTemplate;

/**
* @author Matija Mazi <br/>
*/
class RestRequestData implements Serializable {

  final Class<?> returnType;
  final AllParams params;
  final HttpTemplate.HttpMethod httpMethod;
  final String url;

  RestRequestData(Class<?> returnType, AllParams params, HttpTemplate.HttpMethod httpMethod, String url) {

    this.returnType = returnType;
    this.params = params;
    this.httpMethod = httpMethod;
    this.url = url;
  }

  static RestRequestData create(Method method, Object[] args, String baseUrl, String intfacePath) {

    AllParams params = AllParams.createInstance(method, args);
    String path = params.getPath(method.getAnnotation(Path.class).value());
    HttpTemplate.HttpMethod httpMethod = method.isAnnotationPresent(GET.class) ? HttpTemplate.HttpMethod.GET :
        method.isAnnotationPresent(POST.class) ? HttpTemplate.HttpMethod.POST :
            null;
    String url1 = getUrl(baseUrl, path, intfacePath, params.getQueryString());
    return new RestRequestData(method.getReturnType(), params, httpMethod, url1);
  }

  private static String getUrl(String baseUrl, String method, String intfacePath, String queryString) {

    // TODO make more robust in terms of path separator ('/') handling
    String completeUrl = String.format("%s/%s/%s", baseUrl, intfacePath, method);
    if (queryString != null && !queryString.isEmpty()) {
      completeUrl += "?" + queryString;
    }
    return completeUrl;
  }

}
