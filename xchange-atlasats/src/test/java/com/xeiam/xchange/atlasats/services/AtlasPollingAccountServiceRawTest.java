package com.xeiam.xchange.atlasats.services;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xeiam.xchange.atlasats.AtlasTestExchangeSpecification;
import com.xeiam.xchange.atlasats.dtos.AtlasAccountInfo;

public class AtlasPollingAccountServiceRawTest {

	private AtlasPollingAccountServiceRaw accountServiceRaw;
	private AtlasTestExchangeSpecification exchangeSpecification;

	@Before
	public void setUp() throws Exception {
		exchangeSpecification = new AtlasTestExchangeSpecification();
		accountServiceRaw = new AtlasPollingAccountServiceRaw(
				exchangeSpecification);
	}

	@After
	public void tearDown() throws Exception {
		accountServiceRaw = null;
	}

	@Test
	public void testGetAtlasAccountInfo() {
		AtlasAccountInfo accountInfo = accountServiceRaw.getAtlasAccountInfo();
		assertNotNull(accountInfo);
		assertThat(accountInfo.getExposure(), is(equalTo(BigDecimal.ZERO)));
	}

}
