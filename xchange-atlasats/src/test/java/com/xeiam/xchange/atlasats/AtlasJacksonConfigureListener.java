package com.xeiam.xchange.atlasats;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import si.mazi.rescu.JacksonConfigureListener;

public class AtlasJacksonConfigureListener implements JacksonConfigureListener {

	@Override
	public void configureObjectMapper(ObjectMapper arg0) {
		arg0.configure(Feature.ALLOW_COMMENTS, true);
	}

}
