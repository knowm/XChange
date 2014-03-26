package com.xeiam.xchange.atlasats.services;

public class AtlasAuthenticatedService {

	private String apiKey;

	public AtlasAuthenticatedService(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiKey() {
		return apiKey;
	}

}
