package org.knowm.xchange.bybit.service;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;

public class BybitJacksonObjectMapperFactory extends DefaultJacksonObjectMapperFactory {

  @Override
  public void configureObjectMapper(ObjectMapper objectMapper) {
    super.configureObjectMapper(objectMapper);
    // depending on api version bybit sends jsons with double- and single-quotes
    objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
  }
}
