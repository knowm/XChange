/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.campbx.service.polling;

import java.math.BigDecimal;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.campbx.dto.CampBXResponse;
import com.xeiam.xchange.campbx.dto.account.MyFunds;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;

/**
 * @author Matija Mazi
 */
public class CampBXPollingAccountService extends BasePollingExchangeService implements PollingAccountService {

	private final Logger logger = LoggerFactory.getLogger(CampBXPollingAccountService.class);

	private static AccountInfo accountInfo;
	private static long lastCache;
	private final CampBX campBX;

	/**
	 * Constructor
	 * 
	 * @param exchangeSpecification
	 *            The {@link ExchangeSpecification}
	 */
	public CampBXPollingAccountService(ExchangeSpecification exchangeSpecification) {

		super(exchangeSpecification);
		this.campBX = RestProxyFactory.createProxy(CampBX.class, exchangeSpecification.getSslUri());
	}

	@Override
	public AccountInfo getAccountInfo() {
		if (lastCache + 10000 > System.currentTimeMillis()) {
			return accountInfo;
		}
		MyFunds myFunds = campBX.getMyFunds(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
		//logger.debug("myFunds = {}", myFunds);

		if (!myFunds.isError()) {
			lastCache = System.currentTimeMillis();
			return accountInfo = new AccountInfo(exchangeSpecification.getUserName(), Arrays.asList(Wallet.createInstance("BTC", myFunds.getTotalBTC()), Wallet.createInstance("USD", myFunds.getTotalUSD())));
		} else {
			throw new ExchangeException("Error calling getAccountInfo(): " + myFunds.getError());
		}
	}

	@Override
	public String withdrawFunds(BigDecimal amount, String address) {

		CampBXResponse campBXResponse = campBX.withdrawBtc(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), address, amount);
		logger.debug("campBXResponse = {}", campBXResponse);

		if (!campBXResponse.isError()) {
			return campBXResponse.getSuccess();
		} else {
			throw new ExchangeException("Error calling withdrawFunds(): " + campBXResponse.getError());
		}
	}

	@Override
	public String requestBitcoinDepositAddress(String... arguments) {

		CampBXResponse campBXResponse = campBX.getDepositAddress(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
		logger.debug("campBXResponse = {}", campBXResponse);

		if (!campBXResponse.isError()) {
			return campBXResponse.getSuccess();
		} else {
			throw new ExchangeException("Error calling requestBitcoinDepositAddress(): " + campBXResponse.getError());
		}
	}

}
