package org.knowm.xchange.gateio.service;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;

public class GateioJacksonObjectMapperFactory extends DefaultJacksonObjectMapperFactory {

  @Override
  public void configureObjectMapper(ObjectMapper objectMapper) {
    super.configureObjectMapper(objectMapper);
    objectMapper.configure(READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    // enable parsing to Instant
    objectMapper.registerModule(new JavaTimeModule());
  }
}
