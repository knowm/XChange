package com.xeiam.xchange.cryptofacilities;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCumulatedBidAsk;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCumulativeBidAsk;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrder;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrders;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrder;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTicker;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTrade;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTrades;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesAdapters {

	  public static Ticker adaptTicker(CryptoFacilitiesTicker cryptoFacilitiesTicker, CurrencyPair currencyPair) {

		    Ticker.Builder builder = new Ticker.Builder();
		    
		    builder.ask(cryptoFacilitiesTicker.getAsk());
		    builder.bid(cryptoFacilitiesTicker.getBid());
		    builder.last(cryptoFacilitiesTicker.getLast());
		    builder.currencyPair(currencyPair);

		    return builder.build();
	  }
	  
	  public static Currency adaptCurrency(String code)
	  {
	    return new Currency(code);
	  }
	  
	  public static AccountInfo adaptBalance(Map<String, BigDecimal> cryptoFacilitiesBalance, String username) {

		    List<Balance> balances = new ArrayList<Balance>(cryptoFacilitiesBalance.size());
		    for (Entry<String, BigDecimal> balancePair : cryptoFacilitiesBalance.entrySet()) {
		      Currency currency = adaptCurrency(balancePair.getKey());
		      Balance balance = new Balance(currency, balancePair.getValue());
		      balances.add(balance);		      
		    }
		    return new AccountInfo(username, new Wallet(balances));
	  }

	  public static String adaptOrderId(CryptoFacilitiesOrder order) {

		  return order.getOrderId();
		  
	  }
	  
	  public static OrderType adaptOrderType(String cryptoFacilitiesOrderType) {

		    return cryptoFacilitiesOrderType.equals("Buy") ? OrderType.BID : OrderType.ASK;
	  }
	  
	  public static LimitOrder adaptLimitOrder(CryptoFacilitiesOpenOrder ord)
	  {
		  return new LimitOrder(adaptOrderType(ord.getDirection()), ord.getQuantity(), new CurrencyPair(ord.getTradeable(), ord.getUnit()), ord.getUid(), ord.getTimestamp(),
			        ord.getLimitPrice());
	  }
	  
	  public static OpenOrders adaptOpenOrders(CryptoFacilitiesOpenOrders orders)
	  {
		  List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
		  
		  if(orders != null)
		  {
			  for(CryptoFacilitiesOpenOrder ord : orders.getOrders())
			  {
				// how to handle stop-loss orders?
			    // ignore anything but a plain limit order for now
				if(ord.getType().equals("LMT"))
				{
					limitOrders.add(adaptLimitOrder(ord));				
				}			  
			  }			  
		  }
		  
		  return new OpenOrders(limitOrders);
		  
	  }
	  
	  public static UserTrade adaptTrade(CryptoFacilitiesTrade trade)
	  {
		  return new UserTrade(adaptOrderType(trade.getDirection()), trade.getQuantity(), new CurrencyPair(trade.getTradeable(), trade.getUnit()), trade.getPrice(), trade.getTimestamp(), trade.getUid(), null);
	  }
	  
	  public static UserTrades adaptTrades(CryptoFacilitiesTrades cryptoFacilitiesTrades)
	  {
		  List<UserTrade> trades = new ArrayList<UserTrade>();
		  for(CryptoFacilitiesTrade trade : cryptoFacilitiesTrades.getTrades())
			  trades.add(adaptTrade(trade));
		  
		  return new UserTrades(trades, TradeSortType.SortByTimestamp);
	  }
	  
	  public static LimitOrder adaptOrderBookOrder(CryptoFacilitiesCumulatedBidAsk cumulBidAsk, String direction, String tradeable, String unit)
	  {
		  LimitOrder order = new LimitOrder(adaptOrderType(direction), cumulBidAsk.getQuantity(), new CurrencyPair(tradeable, unit), null, null, cumulBidAsk.getPrice());

		  return order;
	  }
	  
	  public static List<LimitOrder> adaptOrderBookSide(List<CryptoFacilitiesCumulatedBidAsk> cumulBidAsks, String direction, String tradeable, String unit)
	  {
		  List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
		  
		  for(CryptoFacilitiesCumulatedBidAsk cumulBidAsk : cumulBidAsks)
			  limitOrders.add(adaptOrderBookOrder(cumulBidAsk, direction, tradeable, unit));

		  return limitOrders;
	  }
	  
	  public static OrderBook adaptOrderBook(CryptoFacilitiesCumulativeBidAsk cumul) throws IOException
	  {
		  List<CryptoFacilitiesCumulatedBidAsk> cumulBids = cumul.getCumulatedBids();
		  List<CryptoFacilitiesCumulatedBidAsk> cumulAsks = cumul.getCumulatedAsks();
		  
		  return new OrderBook(null, adaptOrderBookSide(cumulAsks, "Sell", "Forward", "USD"), adaptOrderBookSide(cumulBids, "Buy", "Forward", "USD"));
	  }

}
