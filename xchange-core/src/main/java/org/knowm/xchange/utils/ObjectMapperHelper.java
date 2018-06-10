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

public class ObjectMapperHelper {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private ObjectMapperHelper() {}

  public static <T> T readValue(URL src, Class<T> valueType) throws IOException {

    InputStream inputStream = null;
    try {
      inputStream = src.openStream();
      Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
      return objectMapper.readValue(reader, valueType);
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }
  }

  public static <T> T readValue(String value, Class<T> valueType) throws IOException {
    return objectMapper.readValue(value, valueType);
  }

  public static <T> String toJSON(T valueType) {
    return toJSON(valueType, SerializationFeature.INDENT_OUTPUT);
  }

  public static <T> String toCompactJSON(T valueType) {
    return toJSON(valueType);
  }

  private static <T> String toJSON(T valueType, SerializationFeature... features) {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    for (SerializationFeature feature : features) {
      objectMapper.enable(feature);
    }
    String json = "Problem serializing " + valueType.getClass();
    try {
      json = objectMapper.writeValueAsString(valueType);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }
}
