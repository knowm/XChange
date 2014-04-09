package com.xeiam.xchange.atlasats.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.JacksonConfigureListener;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.atlasats.AtlasExchangeSpecification;
import com.xeiam.xchange.atlasats.dtos.AtlasAccountInfo;
import com.xeiam.xchange.atlasats.dtos.AtlasCurrencyPair;
import com.xeiam.xchange.atlasats.dtos.AtlasOptionContract;
import com.xeiam.xchange.atlasats.dtos.translators.AtlasCurrencyPairFromMapTranslator;
import com.xeiam.xchange.atlasats.dtos.translators.AtlasOptionContractFromMapTranslator;

public class AtlasPollingAccountServiceRaw extends AtlasAuthenticatedService {

	private AtlasExchangeSpecification exchangeSpecification;
	private AtlasAccountService accountService;
	private AtlasCurrencyPairFromMapTranslator atlasCurrencyPairFromMapTranslator;
	private AtlasOptionContractFromMapTranslator atlasOptionContractFromMapTranslator;

	public AtlasPollingAccountServiceRaw(
			AtlasExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification.getApiKey());
		this.exchangeSpecification = exchangeSpecification;
		this.accountService = createAccountService();
		this.atlasCurrencyPairFromMapTranslator = new AtlasCurrencyPairFromMapTranslator();
		this.atlasOptionContractFromMapTranslator = new AtlasOptionContractFromMapTranslator();
	}

	private AtlasAccountService createAccountService() {

		ClientConfig clientConfig = new ClientConfig();
		JacksonConfigureListener configureListener = exchangeSpecification
				.getJacksonConfigureListener();
		clientConfig.setJacksonConfigureListener(configureListener);
		AtlasAccountService accountService = RestProxyFactory.createProxy(
				AtlasAccountService.class, exchangeSpecification.getSslUri(),
				clientConfig);
		return accountService;
	}

	public AtlasAccountInfo getAccountInfo() {
		return getAccountInfo(getApiKey());
	}

	private AtlasAccountInfo getAccountInfo(String apiKey) {
		return accountService.getAccountInfo(apiKey);
	}

	public List<AtlasCurrencyPair> getExchangeSymbols() {
		return getExchangeSymbols(getApiKey());
	}

	private List<AtlasCurrencyPair> getExchangeSymbols(String apiKey) {
		List<Map<String, Object>> response = accountService
				.getMarketSymbols(apiKey);
		List<AtlasCurrencyPair> currencyPairs = new ArrayList<AtlasCurrencyPair>();
		for (Map<String, Object> map : response) {
			Integer market_id = (Integer) map.get("market_id");
			if (market_id.equals(0)) {
				currencyPairs.add(atlasCurrencyPairFromMapTranslator
						.translate(map));
			}
		}
		return currencyPairs;
	}

	public List<AtlasOptionContract> getOptionContracts() {
		return getOptionContracts(getApiKey());
	}

	private List<AtlasOptionContract> getOptionContracts(String apiKey) {
		List<Map<String, Object>> response = accountService
				.getMarketSymbols(apiKey);
		List<AtlasOptionContract> optionContracts = new ArrayList<AtlasOptionContract>();
		for (Map<String, Object> map : response) {
			if (map.containsKey("exp")) {
				optionContracts.add(atlasOptionContractFromMapTranslator
						.translate(map));
			}
		}
		return optionContracts;
	}

}
