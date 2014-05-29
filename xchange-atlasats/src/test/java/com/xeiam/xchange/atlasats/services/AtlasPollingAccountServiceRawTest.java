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
