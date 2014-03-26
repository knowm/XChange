package com.xeiam.xchange.atlasats;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;

public class AtlasExchangeSpecification extends ExchangeSpecification {

	protected static final String TEST_API_KEY = "9d52f9deef5603e27aa8045e9be9eb57";
	protected static final String TEST_SSL_STREAMING_URL = "https://private-2da0-atlasxchange.apiary.io:4000/api";
	protected static final String TEST_SSL_URL = "https://private-2da0-atlasxchange.apiary.io/api/v1";
	protected static final String TEST_HOST_NAME = "private-2da0-atlasxchange.apiary.io";
	protected static final String EXCHANGE_NAME = "Atlas ATS, Inc.";
	protected static final String DEFAULT_HOST_NAME = "data.atlasats.com";
	protected static final String SSL_STREAMING_URL = "https://data.atlasats.com:4000/api";
	protected static final String SSL_URL = "https://data.atlasats.com/api/v1";

	private ExchangeStreamingConfiguration streamingConfiguration;

	public AtlasExchangeSpecification() {
		this(AtlasExchange.class, "");
	}
	
	public AtlasExchangeSpecification(String apiKey){
		this(AtlasExchange.class, apiKey);
	}

	public AtlasExchangeSpecification(Class<? extends Exchange> exchangeClass, String apiKey) {
		this(exchangeClass, apiKey, AtlasStreamingConfiguration.DEFAULT);
	}

	public AtlasExchangeSpecification(Class<? extends Exchange> exchangeClass,
			String apiKey, ExchangeStreamingConfiguration streamingConfiguration) {
		super(exchangeClass);
		setExchangeName(EXCHANGE_NAME);
		setHost(DEFAULT_HOST_NAME);
		setSslUri(SSL_URL);
		setSslUriStreaming(SSL_STREAMING_URL);
		setApiKey(apiKey);
		setStreamingConfiguration(streamingConfiguration);
	}

	public ExchangeStreamingConfiguration getStreamingConfiguration() {
		return streamingConfiguration;
	}

	protected void setStreamingConfiguration(
			ExchangeStreamingConfiguration streamingConfiguration) {
		this.streamingConfiguration = streamingConfiguration;
	}

}
