package org.knowm.xchange.bitmex.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;

public class BitmexJacksonObjectMapperFactory extends DefaultJacksonObjectMapperFactory {

  @Override
  public void configureObjectMapper(ObjectMapper objectMapper) {
    super.configureObjectMapper(objectMapper);

    // enable default values for some enums
    objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);

    // enable jsr310 types
    objectMapper.registerModule(new JavaTimeModule());

    // don't render null's
    objectMapper.setSerializationInclusion(Include.NON_NULL);

    // store object mapper for using in module
    Config.getInstance().setObjectMapper(objectMapper);

  }
}
