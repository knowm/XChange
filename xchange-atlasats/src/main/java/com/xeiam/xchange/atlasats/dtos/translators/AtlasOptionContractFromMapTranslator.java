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
