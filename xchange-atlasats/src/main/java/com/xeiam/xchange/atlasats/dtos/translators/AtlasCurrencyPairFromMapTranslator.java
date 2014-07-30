package com.xeiam.xchange.atlasats.dtos.translators;

import java.util.Map;

import com.xeiam.xchange.atlasats.dtos.AtlasCurrencyPair;

public class AtlasCurrencyPairFromMapTranslator implements
		AtlasTranslator<Map<String, Object>, AtlasCurrencyPair> {

	@Override
	public AtlasCurrencyPair translate(Map<String, Object> map) {
		String baseSymbol = (String) map.get("item");
		String counterSymbol = (String) map.get("currency");
		AtlasCurrencyPair currencyPair = new AtlasCurrencyPair(baseSymbol,
				counterSymbol);
		return currencyPair;
	}

}
