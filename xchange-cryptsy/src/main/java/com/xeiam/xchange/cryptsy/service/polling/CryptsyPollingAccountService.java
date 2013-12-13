/**
 * Copyright (C) 2012 - 2013 Matija Mazi
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.cryptsy.service.polling;

import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * @author Matija Mazi
 */
public class CryptsyPollingAccountService extends CryptsyBasePollingService implements PollingAccountService {

	static AccountInfo accountInfo;
	static long lastCache = 0;

	/**
	 * Constructor
	 * 
	 * @param exchangeSpecification
	 *          The {@link ExchangeSpecification}
	 */
	public CryptsyPollingAccountService(ExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification);
	}

	@Override
	public AccountInfo getAccountInfo() {

		if (lastCache + 2000 > System.currentTimeMillis()) {
			return accountInfo;
		}
		CryptsyAccountInfoReturn info = cryptsy.getInfo(apiKey, signatureCreator, "getinfo", nextNonce());
		checkResult(info);
		lastCache = System.currentTimeMillis();
		return accountInfo = CryptsyAdapters.adaptAccountInfo(username, info.getResult());
	}

	protected static void checkResult(CryptsyAccountInfoReturn info) {

		if (info.getSuccess() == 0) {
			throw new ExchangeException("Cryptsy returned an error: " + info.getError());
		}
	}

	@Override
	public String withdrawFunds(BigDecimal amount, String address) {

		throw new UnsupportedOperationException("Funds withdrawal not supported by BTCE API.");
	}

	@Override
	public String requestBitcoinDepositAddress(final String... arguments) {

		throw new UnsupportedOperationException("Deposit address request not supported by BTCE API.");
	}
}
