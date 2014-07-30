package com.xeiam.xchange.atlasats.dtos.translators;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.xeiam.xchange.atlasats.dtos.AtlasCurrencyPair;
import com.xeiam.xchange.atlasats.dtos.AtlasOptionContract;

public class AtlasOptionContractFromMapTranslator implements
		AtlasTranslator<Map<String, Object>, AtlasOptionContract> {

	@Override
	public AtlasOptionContract translate(Map<String, Object> sourceObject) {
		long id = Long.valueOf((String) sourceObject.get("id"));
		String description = (String) sourceObject.get("item");
		String baseSymbol = (String) sourceObject.get("undly");
		String counterSymbol = (String) sourceObject.get("currency");
		Date expirationDate;
		try {
			expirationDate = new SimpleDateFormat("yyyy-mm-dd").parse(
					(String) sourceObject.get("exp"));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		AtlasCurrencyPair currencyPair = new AtlasCurrencyPair(baseSymbol,
				counterSymbol);
				
		AtlasOptionContract optionContract = new AtlasOptionContract();
		optionContract.setId(BigInteger.valueOf(id));
		optionContract.setDescription(description);
		optionContract.setCurrencyPair(currencyPair);
		optionContract.setExpiration(expirationDate);
		return optionContract;
	}

}
