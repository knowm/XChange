package org.knowm.xchange.bitso;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;

public class BitsoJacksonObjectMapperFactory extends DefaultJacksonObjectMapperFactory {
  @Override
  public void configureObjectMapper(ObjectMapper objectMapper) {
    super.configureObjectMapper(objectMapper);

    // enable default values for some enums
    objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);

    // enable jsr310 types
    objectMapper.registerModule(new JavaTimeModule());

    // don't render null's or empty lists
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
  }

  public static ObjectMapper getInstance() {
    BitsoJacksonObjectMapperFactory factory = new BitsoJacksonObjectMapperFactory();
    ObjectMapper objectMapper = factory.createObjectMapper();
    factory.configureObjectMapper(objectMapper);
    return objectMapper;
  }
}
