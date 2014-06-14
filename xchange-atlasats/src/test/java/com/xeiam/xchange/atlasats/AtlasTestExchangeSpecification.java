package com.xeiam.xchange.atlasats;

public class AtlasTestExchangeSpecification extends AtlasExchangeSpecification {

	public AtlasTestExchangeSpecification(String apiKey) {
		super();
		setHost(TEST_HOST_NAME);
		setSslUri(TEST_SSL_URL);
		setSslUriStreaming(TEST_SSL_STREAMING_URL);
		setApiKey(apiKey);
	}

	public AtlasTestExchangeSpecification() {
		this(TEST_API_KEY);
		setJacksonConfigureListener(new AtlasJacksonConfigureListener());
	}

}
