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
package com.xeiam.xchange.vircurex.service.polling;

import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.vircurex.VircurexAdapters;
import com.xeiam.xchange.vircurex.VircurexUtils;
import com.xeiam.xchange.vircurex.dto.marketdata.VircurexAccountInfoReturn;

/**
 * @author Matija Mazi
 */
public class VircurexPollingAccountService implements PollingAccountService {

	ExchangeSpecification exchangeSpecification;

	VircurexAuthenticated vircurex;

	static VircurexAccountInfoReturn cacheInfo;
	static long lastCache = 0;

	/**
	 * Constructor
	 * 
	 * @param exchangeSpecification
	 *          The {@link ExchangeSpecification}
	 */
	public VircurexPollingAccountService(ExchangeSpecification exchangeSpecification) {
		this.exchangeSpecification = exchangeSpecification;
		this.vircurex = RestProxyFactory.createProxy(VircurexAuthenticated.class, exchangeSpecification.getSslUri());
	}

	@Override
	public AccountInfo getAccountInfo() {
		if (lastCache + 10000 > System.currentTimeMillis()) {
			return VircurexAdapters.adaptAccountInfo(cacheInfo);
		}
		String timestamp = VircurexUtils.getUtcTimestamp();
		String nonce = (System.currentTimeMillis() / 250L) + "";
		VircurexSha2Digest digest = new VircurexSha2Digest(exchangeSpecification.getApiKey(), exchangeSpecification.getUserName(), timestamp, nonce, "get_balances");
		VircurexAccountInfoReturn info = cacheInfo = vircurex.getInfo(exchangeSpecification.getUserName(), nonce, digest.toString(), timestamp);
		// checkResult(info);
		lastCache = System.currentTimeMillis();
		return VircurexAdapters.adaptAccountInfo(info);
	}

	@Override
	public String withdrawFunds(BigDecimal amount, String address) {
		throw new UnsupportedOperationException("Funds withdrawal not supported by API.");
	}

	@Override
	public String requestBitcoinDepositAddress(final String... arguments) {
		throw new UnsupportedOperationException("Deposit address request not supported by API.");
	}
}
