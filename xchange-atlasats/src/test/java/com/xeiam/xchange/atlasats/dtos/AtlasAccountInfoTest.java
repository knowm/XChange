package com.xeiam.xchange.atlasats.dtos;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AtlasAccountInfoTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AtlasAccountInfoTest.class);

	private static InputStream inputStream;
	private static AtlasAccountInfo accountInfo;

	@BeforeClass
	public static void setupClass() {
		inputStream = AtlasAccountInfoTest.class
				.getResourceAsStream("/account/accountInfo.json");
		if (inputStream == null) {
			LOGGER.error("Could not Load test data.");
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		try {
			accountInfo = mapper.readValue(inputStream, AtlasAccountInfo.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		accountInfo = null;
		inputStream.close();
		inputStream = null;
	}

	@Test
	public void testMapping() {
		assertNotNull(accountInfo);
		LOGGER.info(accountInfo.toString());
	}

	@Test
	public void testGetAccountNumber() {
		String accountNumber = accountInfo.getAccountNumber();
		assertThat(accountNumber, is(equalTo("19")));
		LOGGER.info("Account Number: " + accountNumber);
	}

	@Test
	public void testGetExposure() {
		BigDecimal exposure = accountInfo.getExposure();
		assertThat(exposure, is(equalTo(BigDecimal.valueOf(0L))));
		LOGGER.info("Exposure: " + exposure);
	}

	@Test
	public void testGetUnrealizedProfit() {
		BigDecimal unrealizedProfit = accountInfo.getUnrealizedProfit();
		assertThat(unrealizedProfit, is(equalTo(BigDecimal.ZERO)));
		LOGGER.info("Unrealized Profit: " + unrealizedProfit);
	}

	@Test
	public void testGetMarginDebit() {
		BigDecimal marginDebit = accountInfo.getMarginDebit();
		assertThat(marginDebit, is(equalTo(BigDecimal.ZERO)));
		LOGGER.info("Margin Debit: " + marginDebit);
	}

	@Test
	public void testGetNetValue() {
		BigDecimal netValue = accountInfo.getNetValue();
		assertThat(netValue, is(equalTo(BigDecimal.valueOf(10000L))));
		LOGGER.info("Net Value: " + netValue);
	}

	@Test
	public void testGetMarginUsed() {
		BigDecimal marginUsed = accountInfo.getMarginUsed();
		assertThat(marginUsed, is(equalTo(BigDecimal.ZERO)));
		LOGGER.info("Margin Used: " + marginUsed);
	}

	@Test
	public void testGetWithdrawableCash() {
		BigDecimal withdrawableCash = accountInfo.getWithdrawableCash();
		assertThat(withdrawableCash, is(equalTo(BigDecimal.valueOf(10000L))));
		LOGGER.info("Withdrawable Cash: " + withdrawableCash);
	}

	@Test
	public void testGetLeverage() {
		BigDecimal leverage = accountInfo.getLeverage();
		assertThat(leverage, is(equalTo(BigDecimal.valueOf(10))));
		LOGGER.info("Leverage: " + leverage);
	}

	@Test
	public void testGetTotalProfit() {
		BigDecimal totalProfit = accountInfo.getTotalProfit();
		assertThat(totalProfit, is(equalTo(BigDecimal.ZERO)));
		LOGGER.info("Total Profit: " + totalProfit);
	}

	@Test
	public void testGetCurrency() {
		Currency currency = accountInfo.getCurrency();
		assertThat(currency, is(equalTo(Currency.getInstance("USD"))));
		LOGGER.info("Currency: " + currency);
	}

	@Test
	public void testGetPositions() {
		List<AtlasPosition> positions = accountInfo.getPositions();
		assertThat(positions.isEmpty(), is(true));
		LOGGER.info("Positions: " + positions);
	}

	@Test
	public void testGetBalance() {
		BigDecimal balance = accountInfo.getBalance();
		assertThat(balance, is(equalTo(BigDecimal.valueOf(10000L))));
		LOGGER.info("Balance: " + balance);
	}

	@Test
	public void testGetRealizedProfit() {
		BigDecimal realizedProfit = accountInfo.getRealizedProfit();
		assertThat(realizedProfit, is(equalTo(BigDecimal.ZERO)));
		LOGGER.info("Realized Profit: " + realizedProfit);
	}

	@Test
	public void testGetBuyingPower() {
		BigDecimal buyingPower = accountInfo.getBuyingPower();
		assertThat(buyingPower, is(equalTo(BigDecimal.valueOf(100000L))));
		LOGGER.info("Buying Power: " + buyingPower);
	}

	@Test
	public void testGetOrderIds() {
		List<AtlasOrderId> orderIds = accountInfo.getOrderIds();
		assertThat(orderIds.get(0).getValue(),
				is(equalTo("33-080513-225438-274")));
		assertThat(orderIds.get(1).getValue(),
				is(equalTo("33-080513-532400-323")));
		LOGGER.info("Order Ids: " + orderIds);
	}

	@Test
	public void testGetCommission() {
		BigDecimal commission = accountInfo.getCommission();
		assertThat(commission, is(equalTo(BigDecimal.ZERO)));
		LOGGER.info("Commission: " + commission);
	}

	@Test
	public void testGetMarginAvailable() {
		BigDecimal marginAvailable = accountInfo.getMarginAvailable();
		assertThat(marginAvailable, is(equalTo(BigDecimal.valueOf(10000L))));
		LOGGER.info("Margin Available: " + marginAvailable);

	}

}
