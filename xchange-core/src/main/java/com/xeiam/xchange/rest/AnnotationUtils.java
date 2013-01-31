/*
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Matija Mazi <br/>
 * @created 1/19/13 7:46 AM
 */
public class AnnotationUtils {

  static <T extends Annotation> String getValueOrNull(Class<T> annotationClass, Annotation ann) {

    if (!annotationClass.isInstance(ann)) {
      return null;
    }
    try {
      return (String) ann.getClass().getMethod("value").invoke(ann);
    } catch (Exception e) {
      throw new RuntimeException("Annotation " + annotationClass + " has no element 'value'.");
    }
  }

  static <A extends Annotation> A getFromMethodOrClass(Method method, Class<A> annotationClass) {

    A methodAnn = method.getAnnotation(annotationClass);
    if (methodAnn != null) {
      return methodAnn;
    }
    for (Class<?> cls = method.getDeclaringClass(); cls != null; cls = cls.getSuperclass()) {
      if (cls.isAnnotationPresent(annotationClass)) {
        return cls.getAnnotation(annotationClass);
      }
    }
    for (Class<?> intf : method.getDeclaringClass().getInterfaces()) {
      if (intf.isAnnotationPresent(annotationClass)) {
        return intf.getAnnotation(annotationClass);
      }
    }
    return null;
  }
}
