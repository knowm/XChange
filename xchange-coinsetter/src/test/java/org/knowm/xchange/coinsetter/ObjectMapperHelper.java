package org.knowm.xchange.coinsetter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class ObjectMapperHelper {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private ObjectMapperHelper() {

  }

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

}
