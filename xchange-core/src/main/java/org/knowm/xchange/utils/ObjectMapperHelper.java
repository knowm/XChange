package org.knowm.xchange.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectMapperHelper {

  private static final Logger logger = LoggerFactory.getLogger(ObjectMapperHelper.class);
  private static final ObjectMapper objectMapperWithIndentation = initWithIndentation();

  private static final ObjectMapper objectMapperWithoutIndentation = initWithoutIndentation();

  private ObjectMapperHelper() {}

  public static <T> T readValue(URL src, Class<T> valueType) throws IOException {
    try (InputStream inputStream = src.openStream()) {
      Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
      return objectMapperWithoutIndentation.readValue(reader, valueType);
    }
  }

  public static <T> T readValue(String value, Class<T> valueType) throws IOException {
    return objectMapperWithoutIndentation.readValue(value, valueType);
  }

  public static <T> String toJSON(T valueType) {
    return toJSON(objectMapperWithIndentation, valueType);
  }

  public static <T> String toCompactJSON(T valueType) {
    return toJSON(objectMapperWithoutIndentation, valueType);
  }

  private static <T> String toJSON(ObjectMapper objectMapper, T valueType) {
    try {
      return objectMapper.writeValueAsString(valueType);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "Problem serializing " + valueType.getClass();
    }
  }

  private static ObjectMapper initWithIndentation() {
    return new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .enable(SerializationFeature.INDENT_OUTPUT);
  }

  private static ObjectMapper initWithoutIndentation() {
    return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
