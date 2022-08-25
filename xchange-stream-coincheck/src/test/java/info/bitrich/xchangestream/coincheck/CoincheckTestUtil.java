package info.bitrich.xchangestream.coincheck;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.InputStream;
import java.util.Objects;
import lombok.SneakyThrows;

public class CoincheckTestUtil {
  public static InputStream load(String filename) {
    String dir = CoincheckTestUtil.class.getPackage().getName().replace('.', '/');

    String path = '/' + dir + '/' + filename;
    return Objects.requireNonNull(CoincheckTestUtil.class.getResourceAsStream(path), path);
  }

  @SneakyThrows
  public static JsonNode loadJson(String filename) {
    return StreamingObjectMapperHelper.getObjectMapper().readTree(load(filename));
  }
}
