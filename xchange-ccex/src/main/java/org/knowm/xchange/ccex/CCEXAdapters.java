package org.knowm.xchange.ccex;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.knowm.xchange.ccex.dto.marketdata.CCEXBuySellData;
import org.knowm.xchange.ccex.dto.marketdata.CCEXGetorderbook;
import org.knowm.xchange.ccex.dto.marketdata.CCEXMarket;
import org.knowm.xchange.ccex.dto.marketdata.CCEXTrade;
import org.knowm.xchange.ccex.dto.marketdata.CCEXTrades;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;

public class CCEXAdapters {

	private CCEXAdapters() {

	}
	
	public static Trades adaptTrades(CCEXTrades cCEXTrades, CurrencyPair currencyPair) {

	    List<Trade> trades = new ArrayList<Trade>();
	    List<CCEXTrade> cCEXTradestmp = cCEXTrades.getResult();
	    
	    for (CCEXTrade cCEXTrade: cCEXTradestmp) {
	      trades.add(adaptCCEXPublicTrade(cCEXTrade, currencyPair));
	    }

	    return new Trades(trades, TradeSortType.SortByTimestamp);
	  }

	  public static Trade adaptCCEXPublicTrade(CCEXTrade cCEXTrade, CurrencyPair currencyPair) {

	    OrderType type = cCEXTrade.getOrderType().equalsIgnoreCase("BUY") ? OrderType.BID : OrderType.ASK;
	    Date timestamp = stringToDate(cCEXTrade.getTimestamp());
	    
	    Trade trade = new Trade(type, cCEXTrade.getQuantity(), currencyPair, cCEXTrade.getPrice(), timestamp, cCEXTrade.getId());
	    return trade;
	  }

	/**
	 * Adapts a org.knowm.xchange.ccex.api.model.OrderBook to a OrderBook Object
	 *
	 * @param currencyPair
	 *            (e.g. BTC/USD)
	 * @return The C-Cex OrderBook
	 */
	public static OrderBook adaptOrderBook(CCEXGetorderbook ccexOrderBook, CurrencyPair currencyPair) {

		List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, ccexOrderBook.getAsks());
		List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, ccexOrderBook.getBids());
		Date date = new Date();
		return new OrderBook(date, asks, bids);
	}

	public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType,
			List<CCEXBuySellData> orders) {

		List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
		for (CCEXBuySellData ask : orders) {
			limitOrders.add(createOrder(currencyPair, ask, orderType));
		}
		return limitOrders;
	}

	public static LimitOrder createOrder(CurrencyPair currencyPair, CCEXBuySellData priceAndAmount,
			Order.OrderType orderType) {

		return new LimitOrder(orderType, priceAndAmount.getQuantity(), currencyPair, "", null,
				priceAndAmount.getRate());
	}
	
	public static CurrencyPair adaptCurrencyPair(CCEXMarket product) {
	    return new CurrencyPair(product.getBaseCurrency(), product.getMarketCurrency());
	  }

	public static ExchangeMetaData adaptToExchangeMetaData(ExchangeMetaData exchangeMetaData,
			List<CCEXMarket> products) {
		Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
		Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

		for (CCEXMarket product : products) {
			BigDecimal minSize = product.getMinTradeSize();
			CurrencyPairMetaData cpmd = new CurrencyPairMetaData(null, minSize, null, 0);
			CurrencyPair pair = adaptCurrencyPair(product);
			currencyPairs.put(pair, cpmd);
			currencies.put(pair.base, null);
			currencies.put(pair.counter, null);
		}

		return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
	}
	
	public static CurrencyPair adaptCurrencyPair(String pair) {

		final String[] currencies = pair.toUpperCase().split("-");
		return new CurrencyPair(currencies[0].toUpperCase(), currencies[1].toUpperCase());
	}
	
	public static Date stringToDate(String dateString) {

	    try {
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	      return sdf.parse(dateString);
	    } catch (ParseException e) {
	      return new Date(0);
	    }
	  }
}
