/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.cryptotrade.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo.CryptoTradeAccountPermissions;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeTransactions.CryptoTradeTransaction;
import com.xeiam.xchange.currency.Currencies;

public class CryptoTradeAccountJsonTests {

	@Test
	public void testDeserializeAccountInfo() throws IOException {

		// Read in the JSON from the example resources
		InputStream is = CryptoTradeAccountJsonTests.class
				.getResourceAsStream("/account/example-account-info-data.json");

		// Use Jackson to parse it
		ObjectMapper mapper = new ObjectMapper();
		CryptoTradeAccountInfo accountInfo = mapper.readValue(is,
				CryptoTradeAccountInfo.class);

		Map<String, BigDecimal> funds = accountInfo.getFunds();
		assertThat(funds.size()).isEqualTo(11);
		assertThat(funds.get("ltc")).isEqualTo("1000");

		CryptoTradeAccountPermissions permissions = accountInfo
				.getPermissions();
		assertThat(permissions.getHistory()).isEqualTo(1);
		assertThat(permissions.getInfo()).isEqualTo(1);
		assertThat(permissions.getTrade()).isEqualTo(1);

		assertThat(accountInfo.getActiveOrders()).isEqualTo(0);
		assertThat(accountInfo.getTransactionCount()).isEqualTo(43);
		assertThat(accountInfo.getServerTimestamp()).isEqualTo(1370985781);
	}

	@Test
	public void testDeserializeTransactionHistory() throws IOException {

		// Read in the JSON from the example resources
		InputStream is = CryptoTradeAccountJsonTests.class
				.getResourceAsStream("/account/example-transaction-history-data.json");

		// Use Jackson to parse it
		ObjectMapper mapper = new ObjectMapper();
		CryptoTradeTransactions transactionHistory = mapper.readValue(is,
				CryptoTradeTransactions.class);

		List<CryptoTradeTransaction> transactionList = transactionHistory
				.getTransactions();
		assertThat(transactionList.size()).isEqualTo(2);

		CryptoTradeTransaction transaction = transactionList.get(0);
		assertThat(transaction.getId()).isEqualTo(2425);
		assertThat(transaction.getTimestamp()).isEqualTo(1371058368);
		assertThat(transaction.getCurrency()).isEqualTo(Currencies.BTC);
		assertThat(transaction.getType()).isEqualTo("Bought");
		assertThat(transaction.getAmount()).isEqualTo("0.49925");
		assertThat(transaction.getDescription()).isEqualTo(
				"Bought 0.5 BTC (-0.15% Fees) @ 123 USD from Order #8");
		assertThat(transaction.getStatus()).isEqualTo("Completed");
	}
}
