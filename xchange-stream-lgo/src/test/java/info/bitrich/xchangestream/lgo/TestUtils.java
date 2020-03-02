package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class TestUtils {

  private static final ObjectMapper mapper = new ObjectMapper();

  private TestUtils() {}

  public static JsonNode getJsonContent(String path) throws IOException {
    return mapper.readTree(LgoStreamingAccountServiceTest.class.getResourceAsStream(path));
  }

  public static JsonNode asJsonNode(String jsonString) throws IOException {
    return mapper.readTree(jsonString);
  }
}
