package com.xeiam.xchange.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class ListUtils {

	public static List<LimitOrder> sortAsks(List<LimitOrder> orderList) {

		Collections.sort(orderList, new Comparator<LimitOrder>() {

			@Override
			public int compare(LimitOrder entry1, LimitOrder entry2) {

				if (entry1.getLimitPrice().getAmount().doubleValue() > entry2
						.getLimitPrice().getAmount().doubleValue()) {
					return 1;
				} else if (entry1.getLimitPrice().getAmount().doubleValue() < entry2
						.getLimitPrice().getAmount().doubleValue()) {
					return -1;
				} else
					return 0;
			}
		});

		return orderList;
	}

	public static List<LimitOrder> sortBids(List<LimitOrder> orderList) {

		Collections.sort(orderList, new Comparator<LimitOrder>() {

			@Override
			public int compare(LimitOrder entry1, LimitOrder entry2) {

				if (entry1.getLimitPrice().getAmount().doubleValue() < entry2
						.getLimitPrice().getAmount().doubleValue()) {
					return 1;
				} else if (entry1.getLimitPrice().getAmount().doubleValue() > entry2
						.getLimitPrice().getAmount().doubleValue()) {
					return -1;
				} else
					return 0;
			}
		});

		return orderList;
	}

	public static List<Trade> sortTrades(List<Trade> tradeList) {

		Collections.sort(tradeList, new Comparator<Trade>() {

			@Override
			public int compare(Trade entry1, Trade entry2) {

				if (entry1.getTimestamp().getMillis() > entry2.getTimestamp()
						.getMillis()) {
					return 1;
				} else if (entry1.getTimestamp().getMillis() < entry2
						.getTimestamp().getMillis()) {
					return -1;
				} else
					return 0;
			}
		});

		return tradeList;
	}
}
