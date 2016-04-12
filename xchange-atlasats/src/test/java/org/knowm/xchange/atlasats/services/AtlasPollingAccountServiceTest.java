package org.knowm.xchange.atlasats.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.ExchangeException;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.NotAvailableFromExchangeException;
import org.knowm.xchange.NotYetImplementedForExchangeException;
import org.knowm.xchange.atlasats.AtlasTestExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;

public class AtlasPollingAccountServiceTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(AtlasPollingAccountServiceTest.class);

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
  public void testGetAccountInfo() throws  IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    assertNotNull(accountInfo);
    LOGGER.info("Account Info: " + accountInfo);
  }

  @Test
  public void testGetExchangeSymbols() throws IOException {

    Collection<CurrencyPair> currencyPairs = accountService.getExchangeSymbols();
    assertNotNull(currencyPairs);
    assertFalse(currencyPairs.isEmpty());
    LOGGER.info("Currency Pairs: " + currencyPairs);
  }

}
