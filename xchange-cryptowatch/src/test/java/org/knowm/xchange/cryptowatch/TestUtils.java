package org.knowm.xchange.cryptowatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

public class TestUtils {

  public static <T> T getObject(String path, Class<T> cls) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is = TestUtils.class.getResourceAsStream(path);
    return mapper.readValue(is, cls);
  }
}
