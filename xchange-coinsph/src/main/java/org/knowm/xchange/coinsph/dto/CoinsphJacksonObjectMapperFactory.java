package org.knowm.xchange.coinsph.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;

public class CoinsphJacksonObjectMapperFactory extends DefaultJacksonObjectMapperFactory {
  @Override
  public void configureObjectMapper(ObjectMapper objectMapper) {
    super.configureObjectMapper(objectMapper);

    // don't write nulls
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    // enable parsing to Instant
    objectMapper.registerModule(new JavaTimeModule());
  }
}
