package org.knowm.xchange.utils;

import java.io.IOException;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * A Jackson {@link TypeIdResolver} which uses {@link ServiceLoader} to
 * find suitable concrete classes on the classpath.
 *
 * @author Graham Crockford
 */
public abstract class AbstractServiceLoaderTypeResolver<T> extends TypeIdResolverBase {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceLoaderTypeResolver.class);

  private JavaType baseType;

  @Override
  public void init(JavaType baseType) {
    this.baseType = baseType;
  }

  @Override
  public String idFromValue(Object obj) {
    return idFromType(obj.getClass());
  }

  @Override
  public String idFromValueAndType(Object value, Class<?> clazz) {
    return idFromType(clazz);
  }

  @Override
  public String idFromBaseType() {
    return idFromType(baseType.getRawClass());
  }

  protected abstract Map<String, Class<? extends T>> registered();

  @Override
  public JavaType typeFromId(DatabindContext context, String id) throws IOException {
    Class<? extends T> clazz = registered().get(id);
    if (clazz == null) {
      throw new IllegalArgumentException("No mapping from '" + id + "' via " + getClass().getSimpleName() + " found");
    }
    return TypeFactory.defaultInstance().constructSpecializedType(baseType, clazz);
  }

  @Override
  public String getDescForKnownTypeIds() {
    return "";
  }

  @Override
  public Id getMechanism() {
    return Id.CUSTOM;
  }

  private static String idFromType(Class<?> clazz) {
    JsonTypeName annotation = clazz.getAnnotation(JsonTypeName.class);
    if (annotation == null) {
      throw new IllegalArgumentException("Type " + clazz + " lacks a JsonTypeName annotation");
    }
    return annotation.value();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected static <T> Map<String, Class<? extends T>> loadClasses(Class<T> clazz) {
    ServiceLoader<JsonTypeContribution> serviceLoader = ServiceLoader.load(JsonTypeContribution.class);
    try {
      Map<String, Class<? extends T>>  result = (Map<String, Class<? extends T>>)
          StreamSupport.stream(serviceLoader.spliterator(), false)
              .filter(it -> it.baseType().equals(clazz))
              .flatMap(it -> it.subTypes().stream())
              .collect(Collectors.toMap(it -> idFromType((Class)it), it -> it));
      LOGGER.info(clazz + " types registered: {}", result);
      return result;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Duplicate " + clazz + " types registered", e);
    }
  }
}
