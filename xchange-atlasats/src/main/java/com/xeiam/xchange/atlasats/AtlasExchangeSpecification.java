package com.xeiam.xchange.atlasats;

import si.mazi.rescu.JacksonConfigureListener;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;

public class AtlasExchangeSpecification extends ExchangeSpecification {

	public static final String TEST_API_KEY = "YOUR API KEY";
	public static final String TEST_SSL_STREAMING_URL = "https://private-2da0-atlasxchange.apiary.io:4000";
	public static final String TEST_SSL_URL = "https://private-2da0-atlasxchange.apiary.io";
	public static final String TEST_HOST_NAME = "private-2da0-atlasxchange.apiary.io";
	public static final String EXCHANGE_NAME = "Atlas ATS, Inc.";
	public static final String DEFAULT_HOST_NAME = "atlasats.com";
	public static final String SSL_STREAMING_URL = "https://data.atlasats.com:4000";
	public static final String SSL_URL = "https://atlasats.com";

	private JacksonConfigureListener jacksonConfigureListener;
	private ExchangeStreamingConfiguration streamingConfiguration;

	public AtlasExchangeSpecification() {
		this(AtlasExchange.class, "");
	}

	public AtlasExchangeSpecification(String apiKey) {
		this(AtlasExchange.class, apiKey);
	}

	public AtlasExchangeSpecification(Class<? extends Exchange> exchangeClass,
			String apiKey) {
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

	public JacksonConfigureListener getJacksonConfigureListener() {
		return jacksonConfigureListener;
	}

	public void setJacksonConfigureListener(JacksonConfigureListener jacksonConfigureListener) {
		this.jacksonConfigureListener = jacksonConfigureListener;
	}

	public ExchangeStreamingConfiguration getStreamingConfiguration() {
		return streamingConfiguration;
	}

	protected void setStreamingConfiguration(
			ExchangeStreamingConfiguration streamingConfiguration) {
		this.streamingConfiguration = streamingConfiguration;
	}

}
