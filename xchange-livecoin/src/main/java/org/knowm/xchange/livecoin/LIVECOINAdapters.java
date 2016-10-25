package org.knowm.xchange.livecoin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.livecoin.dto.marketdata.LIVECOINRestriction;

public class LIVECOINAdapters {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

	static {
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	private LIVECOINAdapters() {

	}

	public static CurrencyPair adaptCurrencyPair(LIVECOINRestriction product) {
		String[] data = product.getCurrencyPair().split("\\/");
		return new CurrencyPair(data[0], data[1]);
	}

	public static ExchangeMetaData adaptToExchangeMetaData(ExchangeMetaData exchangeMetaData,
			List<LIVECOINRestriction> products) {

		Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
		Map<Currency, CurrencyMetaData> currencies = new HashMap<>();
		for (LIVECOINRestriction product : products) {
			BigDecimal minSize = product.getMinLimitQuantity().setScale(product.getPriceScale(),
					BigDecimal.ROUND_UNNECESSARY);
			CurrencyPairMetaData cpmd = new CurrencyPairMetaData(null, minSize, null, 8);
			CurrencyPair pair = adaptCurrencyPair(product);
			currencyPairs.put(pair, cpmd);
			currencies.put(pair.base, null);
			currencies.put(pair.counter, null);
		}
		return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
	}

}
