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
package com.xeiam.xchange.atlasats.services;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.atlasats.AtlasExchangeSpecification;
import com.xeiam.xchange.atlasats.AtlasTestExchangeSpecification;
import com.xeiam.xchange.atlasats.dtos.AtlasAccountInfo;
import com.xeiam.xchange.atlasats.dtos.AtlasCurrencyPair;
import com.xeiam.xchange.atlasats.dtos.AtlasOptionContract;

public class AtlasPollingAccountServiceRawTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AtlasPollingAccountServiceRawTest.class);

	private AtlasPollingAccountServiceRaw accountServiceRaw;
	private AtlasTestExchangeSpecification exchangeSpecification;

	@Before
	public void setUp() throws Exception {
		exchangeSpecification = new AtlasTestExchangeSpecification();
		exchangeSpecification.setSslUri(AtlasExchangeSpecification.TEST_SSL_URL);
		accountServiceRaw = new AtlasPollingAccountServiceRaw(
				exchangeSpecification);
	}

	@After
	public void tearDown() throws Exception {
		accountServiceRaw = null;
		exchangeSpecification = null;
	}

	@Test
	public void testGetAccountInfo() {
		AtlasAccountInfo accountInfo = accountServiceRaw.getAccountInfo();
		assertNotNull(accountInfo);
		assertThat(accountInfo.getExposure(), is(equalTo(BigDecimal.ZERO)));
		LOGGER.info("Account Info: " + accountInfo);
	}

	@Test
	public void testGetExchangeSymbols() throws Exception {
		List<AtlasCurrencyPair> exchangeSymbols = accountServiceRaw
				.getExchangeSymbols();
		assertNotNull(exchangeSymbols);
		assertThat(exchangeSymbols.isEmpty(), is(not(true)));
		LOGGER.info("Exchange Symbols: " + exchangeSymbols);
	}

	@Test
	public void testGetOptionContracts() throws Exception {
		List<AtlasOptionContract> optionContracts = accountServiceRaw
				.getOptionContracts();
		assertNotNull(optionContracts);
		assertThat(optionContracts.isEmpty(), is(not(true)));
		LOGGER.info("Option Contracts: " + optionContracts);
	}

}
