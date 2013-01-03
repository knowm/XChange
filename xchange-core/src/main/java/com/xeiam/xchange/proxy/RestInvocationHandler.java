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

import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author Matija Mazi <br/>
*/
public class RestInvocationHandler implements InvocationHandler {
  @SuppressWarnings("unchecked")
  private static final List<Class<? extends Annotation>> PARAM_ANNOTATION_CLASSES = Arrays.asList(QueryParam.class, PathParam.class, FormParam.class);

  private final HttpTemplate httpTemplate;
  private final ExchangeSpecification exchangeSpecification;
  private final ObjectMapper mapper;
  private final String intfacePath;

  public RestInvocationHandler(HttpTemplate httpTemplate, ExchangeSpecification exchangeSpecification, ObjectMapper mapper, Class<?> restInterface) {

    this.httpTemplate = httpTemplate;
    this.exchangeSpecification = exchangeSpecification;
    this.mapper = mapper;
    intfacePath = restInterface.getAnnotation(Path.class).value();
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    Path path = method.getAnnotation(Path.class);
    Class<?> returnType = method.getReturnType();
    Map<Class<? extends Annotation>, Params> params = createParamsMap();
    Annotation[][] paramAnnotations = method.getParameterAnnotations();
    for (int i = 0; i < paramAnnotations.length; i++) {
      Annotation[] paramAnns = paramAnnotations[i];
      for (Annotation paramAnn : paramAnns) {
        String paramName = getParamName(paramAnn);
        if (paramName != null) {
          params.get(paramAnn.annotationType()).add(paramName, args[i]);
        }
      }
    }
    if (method.isAnnotationPresent(GET.class)) {
      return getForJsonObject(path.value(), returnType, params.get(QueryParam.class));
    } else if (method.isAnnotationPresent(POST.class)) {
      return postForJsonObject(path.value(), returnType, params.get(FormParam.class));
    } else {
      throw new IllegalArgumentException("Only methods annotated with @GET or @POST supported.");
    }
  }

  private static String getParamName(Annotation queryParam) {

    for (Class<? extends Annotation> annotationClass : PARAM_ANNOTATION_CLASSES) {
      String paramName = getValueOrNull(annotationClass, queryParam);
      if (paramName != null) {
        return paramName;
      }
    }
    // This is not one of the annotations in PARAM_ANNOTATION_CLASSES.
    return null;
  }

  private static <T extends Annotation> String getValueOrNull(Class<T> annotationClass, Annotation ann) {
    if (!annotationClass.isInstance(ann)) {
      return null;
    }
    try {
      return (String) ann.getClass().getMethod("value").invoke(ann);
    } catch (Exception e) {
      throw new RuntimeException("Annotation " + annotationClass + " has no element 'value'.");
    }
  }

  private static Map<Class<? extends Annotation>, Params> createParamsMap() {

    Map<Class<? extends Annotation>, Params> map = new HashMap<Class<? extends Annotation>, Params>();
    for (Class<? extends Annotation> annotationClass : PARAM_ANNOTATION_CLASSES) {
      map.put(annotationClass, new Params());
    }
    return map;
  }

  private String getUrl(String method) {

    // todo: make more robust in terms of path separator ('/') handling
    return String.format("%s/%s/%s", exchangeSpecification.getUri(), intfacePath , method);
  }

  private <T> T getForJsonObject(String method, Class<T> returnType, Params params) {

    String url = getUrl(method);
    if (params != null) {
      url += "?" + params.asQueryString();
    }

    return httpTemplate.getForJsonObject(url, returnType, mapper, new HashMap<String, String>());
  }

  private  <T> T postForJsonObject(String method, Class<T> returnType, Params postBody) {

    return httpTemplate.postForJsonObject(getUrl(method), returnType, postBody.asFormEncodedPostBody(), mapper, new HashMap<String, String>());
  }
}
