package com.xeiam.xchange.atlasats.services;

import com.xeiam.xchange.atlasats.AtlasExchangeSpecification;
import com.xeiam.xchange.atlasats.dtos.AtlasAccountInfo;

public class AtlasPollingAccountServiceRaw extends AtlasAuthenticatedService {

	public AtlasPollingAccountServiceRaw(
			AtlasExchangeSpecification exchangeSpecification) {
		this(exchangeSpecification.getApiKey());
	}

	private AtlasPollingAccountServiceRaw(String apiKey) {
		super(apiKey);
	}

	public AtlasAccountInfo getAtlasAccountInfo() {
		AtlasAccountInfo accountInfo = new AtlasAccountInfo();
		return accountInfo;
	}

}
