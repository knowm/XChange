package info.bitrich.xchangestream.bitmex.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import lombok.Data;

@Data
public final class Config {

  private ObjectMapper objectMapper;

  private static Config instance = new Config();

  private Config() {
    objectMapper = StreamingObjectMapperHelper.getObjectMapper();

    configureObjectMapper(objectMapper);
  }

  public static Config getInstance() {
    return instance;
  }

  public static void configureObjectMapper(ObjectMapper objectMapper) {
    // enable default values for some enums
    objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);

    // enable jsr310 types
    objectMapper.registerModule(new JavaTimeModule());
  }
}
