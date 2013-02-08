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

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This holds name-value mapping for various types of params used in REST (QueryParam, PathParam, FormParam, HeaderParam).
 * 
 * @author Matija Mazi
 */
public class RestMethodMetadata implements Serializable {

  @SuppressWarnings("unchecked")
  private static final List<Class<? extends Annotation>> PARAM_ANNOTATION_CLASSES = Arrays.asList(QueryParam.class, PathParam.class, FormParam.class, HeaderParam.class);

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final String contentType;
  private final Map<Class<? extends Annotation>, Params> paramsMap;
  private final List<Object> unannanotatedParams = new ArrayList<Object>();

  public RestMethodMetadata(Method method, Object[] args) {

    Consumes consumes = AnnotationUtils.getFromMethodOrClass(method, Consumes.class);
    this.contentType = consumes != null ? consumes.value()[0] : MediaType.APPLICATION_FORM_URLENCODED;

    paramsMap = new HashMap<Class<? extends Annotation>, Params>();
    for (Class<? extends Annotation> annotationClass : PARAM_ANNOTATION_CLASSES) {
      Params params = Params.of();
      params.setRestMethodMetadata(this);
      paramsMap.put(annotationClass, params);
    }

    Annotation[][] paramAnnotations = method.getParameterAnnotations();
    for (int i = 0; i < paramAnnotations.length; i++) {
      Annotation[] paramAnns = paramAnnotations[i];
      if (paramAnns.length == 0) {
        unannanotatedParams.add(args[i]);
      }
      for (Annotation paramAnn : paramAnns) {
        String paramName = getParamName(paramAnn);
        if (paramName != null) {
          this.paramsMap.get(paramAnn.annotationType()).add(paramName, args[i]);
        }
      }
    }

    // Support using method method name as a parameter.
    for (Class<? extends Annotation> paramAnnotationClass : PARAM_ANNOTATION_CLASSES) {
      if (method.isAnnotationPresent(paramAnnotationClass)) {
        Annotation paramAnn = method.getAnnotation(paramAnnotationClass);
        String paramName = getParamName(paramAnn);
        this.paramsMap.get(paramAnnotationClass).add(paramName, method.getName());
      }
    }
  }

  // todo: this is needed only for testing
  public RestMethodMetadata(Map<Class<? extends Annotation>, Params> paramsMap, String contentType) {

    this.contentType = contentType;
    this.paramsMap = new LinkedHashMap<Class<? extends Annotation>, Params>(paramsMap);
    for (Params params : this.paramsMap.values()) {
      params.setRestMethodMetadata(this);
    }
  }

  static RestMethodMetadata createInstance(Method method, Object[] args) {

    return new RestMethodMetadata(method, args);
  }

  private static String getParamName(Annotation queryParam) {

    for (Class<? extends Annotation> annotationClass : PARAM_ANNOTATION_CLASSES) {
      String paramName = AnnotationUtils.getValueOrNull(annotationClass, queryParam);
      if (paramName != null) {
        return paramName;
      }
    }
    // This is not one of the annotations in PARAM_ANNOTATION_CLASSES.
    return null;
  }

  public String getPath(String methodPath) {

    return paramsMap.get(PathParam.class).applyToPath(methodPath);
  }

  public String getRequestBody() {

    if (MediaType.APPLICATION_FORM_URLENCODED.equals(contentType)) {
      return paramsMap.get(FormParam.class).asFormEncodedRequestBody();
    } else if (MediaType.APPLICATION_JSON.equals(contentType)) {
      if (!paramsMap.get(FormParam.class).isEmpty()) {
        throw new IllegalArgumentException("@FormParams are not allowed with " + MediaType.APPLICATION_JSON);
      } else if (unannanotatedParams.size() > 1) {
        throw new IllegalArgumentException("Can only have a single unnanotated parameter with " + MediaType.APPLICATION_JSON);
      }
      if (unannanotatedParams.size() == 0) {
        return null;
      }
      try {
        return objectMapper.writeValueAsString(unannanotatedParams.get(0));
      } catch (IOException e) {
        throw new RuntimeException("Error writing json, probably a bug.", e);
      }
    }
    throw new IllegalArgumentException("Unsupported media type: " + contentType);
  }

  public Map<String, String> getHttpHeaders() {

    return paramsMap.get(HeaderParam.class).asHttpHeaders();
  }

  public String getQueryString() {

    return paramsMap.get(QueryParam.class).asQueryString();
  }

  public String getContentType() {

    return contentType;
  }
}
