package com.xeiam.xchange.atlasats.services;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.atlasats.AtlasTestExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.account.AccountInfo;

public class AtlasPollingAccountServiceTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AtlasPollingAccountServiceTest.class);

	private AtlasPollingAccountService accountService;
	private ExchangeSpecification exchangeSpecification;

	@Before
	public void setUp() throws Exception {
		exchangeSpecification = new AtlasTestExchangeSpecification();
		accountService = new AtlasPollingAccountService(exchangeSpecification);
	}

	@After
	public void tearDown() throws Exception {
		accountService = null;
		exchangeSpecification = null;
	}

	@Test
	public void testGetAccountInfo() throws ExchangeException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		AccountInfo accountInfo = accountService.getAccountInfo();
		assertNotNull(accountInfo);
		LOGGER.info("Account Info: " + accountInfo);
	}

	@Test
	public void testGetExchangeSymbols() throws IOException {
		Collection<CurrencyPair> currencyPairs = accountService
				.getExchangeSymbols();
		assertNotNull(currencyPairs);
		assertFalse(currencyPairs.isEmpty());
		LOGGER.info("Currency Pairs: " + currencyPairs);
	}

}
