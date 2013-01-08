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
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 * This holds name-value mapping for various types of params used in REST (QueryParam, PathParam, FormParam, HeaderParam).
 *
 * @author Matija Mazi <br/>
 */
public class AllParams implements Serializable {

  @SuppressWarnings("unchecked")
  private static final List<Class<? extends Annotation>> PARAM_ANNOTATION_CLASSES = Arrays.asList(QueryParam.class, PathParam.class, FormParam.class, HeaderParam.class);

  private Map<Class<? extends Annotation>, Params> paramsMap;

  AllParams(Map<Class<? extends Annotation>, Params> paramsMap) {

    this.paramsMap = paramsMap;
    for (Params params : paramsMap.values()) {
      params.setAllParams(this);
    }
  }

  static AllParams createInstance(Method method, Object[] args) {

    return new AllParams(createParamsMap(method, args));
  }

  private static Map<Class<? extends Annotation>, Params> createParamsMap(Method method, Object[] args) {

    Map<Class<? extends Annotation>, Params> params = createEmptyMap();
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
    return params;
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

  private static Map<Class<? extends Annotation>, Params> createEmptyMap() {

    Map<Class<? extends Annotation>, Params> map = new HashMap<Class<? extends Annotation>, Params>();
    for (Class<? extends Annotation> annotationClass : PARAM_ANNOTATION_CLASSES) {
      map.put(annotationClass, Params.of());
    }
    return map;
  }

  String getPath(String methodPath) {

    return paramsMap.get(PathParam.class).applyToPath(methodPath);
  }

  String getPostBody() {

    return paramsMap.get(FormParam.class).asFormEncodedPostBody();
  }

  Map<String, String> getHttpHeaders() {

    return paramsMap.get(HeaderParam.class).asHttpHeaders();
  }

  String getQueryString() {

    return paramsMap.get(QueryParam.class).asQueryString();
  }
}
