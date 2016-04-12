package org.knowm.xchange.atlasats;

import si.mazi.rescu.serialization.jackson.JacksonConfigureListener;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AtlasJacksonConfigureListener implements JacksonConfigureListener {

  @Override
  public void configureObjectMapper(ObjectMapper arg0) {

    arg0.configure(Feature.ALLOW_COMMENTS, true);
  }

}
