package org.knowm.xchange.atlasats.services;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.atlasats.AtlasExchangeSpecification;
import org.knowm.xchange.atlasats.AtlasTestExchangeSpecification;
import org.knowm.xchange.atlasats.dtos.AtlasAccountInfo;
import org.knowm.xchange.atlasats.dtos.AtlasCurrencyPair;
import org.knowm.xchange.atlasats.dtos.AtlasOptionContract;

public class AtlasPollingAccountServiceRawTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(AtlasPollingAccountServiceRawTest.class);

  private AtlasPollingAccountServiceRaw accountServiceRaw;
  private AtlasTestExchangeSpecification exchangeSpecification;

  @Before
  public void setUp() throws Exception {

    exchangeSpecification = new AtlasTestExchangeSpecification();
    exchangeSpecification.setSslUri(AtlasExchangeSpecification.TEST_SSL_URL);
    accountServiceRaw = new AtlasPollingAccountServiceRaw(exchangeSpecification);
  }

  @After
  public void tearDown() throws Exception {

    accountServiceRaw = null;
    exchangeSpecification = null;
  }

  @Test
  public void testGetAccountInfo() {

    AtlasAccountInfo accountInfo = accountServiceRaw.getAccountInfo();
    assertThat(accountInfo).isNotNull();
    assertThat(accountInfo.getExposure().equals(BigDecimal.ZERO));
    LOGGER.info("Account Info: " + accountInfo);
  }

  @Test
  public void testGetExchangeSymbols() throws Exception {

    List<AtlasCurrencyPair> exchangeSymbols = accountServiceRaw.getExchangeSymbols();
    assertNotNull(exchangeSymbols);
    assertThat(exchangeSymbols).isNotEmpty();
    LOGGER.info("Exchange Symbols: " + exchangeSymbols);
  }

  @Test
  public void testGetOptionContracts() throws Exception {

    List<AtlasOptionContract> optionContracts = accountServiceRaw.getOptionContracts();
    assertThat(optionContracts).isNotNull();
    assertThat(optionContracts).isNotEmpty();
    LOGGER.info("Option Contracts: " + optionContracts);
  }

}
