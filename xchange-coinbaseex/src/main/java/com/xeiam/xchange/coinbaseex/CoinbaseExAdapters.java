package com.xeiam.xchange.coinbaseex;

import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBook;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBookEntry;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductStats;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class CoinbaseExAdapters {

	public static Ticker adaptTicker(CoinbaseExProductTicker ticker, CoinbaseExProductStats stats, CoinbaseExProductBook book, CurrencyPair currencyPair) {

		BigDecimal last = ticker != null ? ticker.getPrice() : null;
		BigDecimal high = stats != null ? stats.getHigh() : null;
		BigDecimal low = stats != null ? stats.getLow() : null;
		BigDecimal buy = book != null ? book.getBestBid().getPrice() : null;
		BigDecimal sell = book != null ? book.getBestAsk().getPrice() : null;
		BigDecimal volume = stats != null ? stats.getVolume() : null;
		Date date = ticker != null ? ticker.getTime() : new Date();

		return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).bid(buy).ask(sell).volume(volume).timestamp(date).build();
	}

	public static OrderBook adaptOrderBook(CoinbaseExProductBook book, CurrencyPair currencyPair) {
		List<LimitOrder> asks = toLimitOrderList(book.getAsks(), OrderType.ASK, currencyPair);
		List<LimitOrder> bids = toLimitOrderList(book.getBids(), OrderType.BID, currencyPair);

		return new OrderBook(null, asks, bids);
	}

	private static List<LimitOrder> toLimitOrderList(
			CoinbaseExProductBookEntry[] levels, OrderType orderType, CurrencyPair currencyPair) {

		List<LimitOrder> allLevels = new ArrayList<LimitOrder>(levels.length);
		for(int i = 0; i < levels.length; i++) {
			CoinbaseExProductBookEntry ask = levels[i];

			allLevels.add(new LimitOrder(orderType, ask.getVolume(), currencyPair, "0", null, ask.getPrice()));
		}

		return allLevels;

	}
}
