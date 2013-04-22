package com.xeiam.xchange;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.junit.Test;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * @author Kyje
 * Check that order quality is working as expected
 */
public class OrderEqualityTest {

	@Test
	public void testOrderEquality() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 1, 1);
		Date t1 = calendar.getTime();
		calendar.set(2013, 1, 2);
		Date t2 = calendar.getTime();
		LimitOrder a = new LimitOrder(OrderType.ASK, BigDecimal.valueOf(123), "BTC/USD", "USD", null, t1, BigMoney.of(CurrencyUnit.USD, 234));
		LimitOrder b = new LimitOrder(OrderType.ASK, BigDecimal.valueOf(123), "BTC/USD", "USD", null, t1, BigMoney.of(CurrencyUnit.USD, 234));
		LimitOrder c = new LimitOrder(OrderType.ASK, BigDecimal.valueOf(123), "BTC/USD", "USD", null, t2, BigMoney.of(CurrencyUnit.USD, 234));
		LimitOrder d = new LimitOrder(OrderType.BID, BigDecimal.valueOf(123), "BTC/USD", "USD", null, t1, BigMoney.of(CurrencyUnit.USD, 234));

		assertThat(a, is(equalTo(b))); // identical
		assertThat(a, is(equalTo(c))); // identical except time, considered equal
		assertThat(a, is(not(equalTo(d)))); // type is different
	}

	@Test
	public void testBookEquality() {
		LimitOrder a = new LimitOrder(OrderType.ASK, BigDecimal.valueOf(123), "BTC/USD", "USD", null, null, BigMoney.of(CurrencyUnit.USD, 234));
		LimitOrder b = new LimitOrder(OrderType.BID, BigDecimal.valueOf(345), "BTC/USD", "USD", null, null, BigMoney.of(CurrencyUnit.USD, 678));

		OrderBook book1 = new OrderBook(Arrays.asList(a), Arrays.asList(b));
		OrderBook book2 = new OrderBook(Arrays.asList(a), Arrays.asList(b));

		assertThat(book1, is(equalTo(book2)));
	}

}
