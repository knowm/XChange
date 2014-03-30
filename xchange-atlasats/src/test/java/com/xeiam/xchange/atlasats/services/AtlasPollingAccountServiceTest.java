package com.xeiam.xchange.atlasats.services;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.atlasats.AtlasExchangeSpecification;
import com.xeiam.xchange.atlasats.AtlasTestExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.account.AccountInfo;

public class AtlasPollingAccountServiceTest {

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
	}

	@Test
	public void testGetAccountInfo() throws ExchangeException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		AccountInfo accountInfo = accountService.getAccountInfo();
		assertNotNull(accountInfo);
	}

	@Test
	public void testGetExchangeSymbols() throws IOException {
		Collection<CurrencyPair> currencyPairs = accountService
				.getExchangeSymbols();
		assertNotNull(currencyPairs);
		assertFalse(currencyPairs.isEmpty());
	}

}
