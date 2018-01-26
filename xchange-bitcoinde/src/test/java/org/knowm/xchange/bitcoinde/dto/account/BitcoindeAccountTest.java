package org.knowm.xchange.bitcoinde.dto.account;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author kaiserfr
 *
 */
public class BitcoindeAccountTest {

	@Test
	public void testBitcoindeOrderBook() throws JsonParseException, JsonMappingException, IOException {

		// Read in the JSON from the example resources
		InputStream is = BitcoindeAccountTest.class.getResourceAsStream("/account.json");

		// Use Jackson to parse it
		ObjectMapper mapper = new ObjectMapper();
		BitcoindeAccountWrapper bitcoindeTradesWrapper = mapper.readValue(is, BitcoindeAccountWrapper.class);
		System.out.println("bitcoindeTradesWrapper = " + bitcoindeTradesWrapper);

		// Make sure trade values are correct

		BigDecimal btcBalance = bitcoindeTradesWrapper.getData().getBalances().getBtc().getAvailableAmount();
		BigDecimal ethBalance = bitcoindeTradesWrapper.getData().getBalances().getEth().getAvailableAmount();
		
		BigDecimal reservedAmount = bitcoindeTradesWrapper.getData().getFidorReservation().getAvailableAmount();
		
		System.out.println(btcBalance);
		System.out.println(ethBalance);
		System.out.println(reservedAmount);

		assertEquals(btcBalance, new BigDecimal("0.009"));
		assertEquals(ethBalance, new BigDecimal("0.06463044"));
		assertEquals(reservedAmount, new BigDecimal("2000"));
	}
}
