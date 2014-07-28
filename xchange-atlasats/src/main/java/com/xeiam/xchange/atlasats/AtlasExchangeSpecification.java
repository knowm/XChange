/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
