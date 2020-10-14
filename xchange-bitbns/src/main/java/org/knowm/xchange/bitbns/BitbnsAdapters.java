package org.knowm.xchange.bitbns;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.bitbns.dto.BitbnsOrderStatusResponse;
import org.knowm.xchange.bitbns.dto.Data;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public final class BitbnsAdapters {

	  public static Order adaptOrder(BitbnsOrderStatusResponse orderStatusResponse,String symbol) {
		  	List<Data> data =orderStatusResponse.getData();
		    long id = data.get(0).getEntry_id();
		    CurrencyPair currencyPair = adaptCurrencyPair(symbol);
		    BigDecimal averageExecutionPrice = data.get(0).getAvg_cost();
		    BigDecimal executedAmount = data.get(0).getRemaining();
		    BigDecimal originalAmount = data.get(0).getTotal();
		    OrderType orderType = (data.get(0).getSide().equals("buy")) ? OrderType.BID : OrderType.ASK;
		    OrderStatus orderStatus = adaptOrderstatus(orderStatusResponse.getData());
		    Date timestamp = data.get(0).getTime();

		    if (data.get(0).getType()==1) {

		      BigDecimal limitPrice = data.get(0).getRate();

		      return new LimitOrder(
		          orderType,
		          originalAmount,
		          currencyPair,
		          String.valueOf(id),
		          timestamp,
		          limitPrice,
		          averageExecutionPrice,
		          executedAmount,
		          null,
		          orderStatus);

		    } else if (data.get(0).getType()==2) {

		      return new MarketOrder(
		          orderType,
		          originalAmount,
		          currencyPair,
		          String.valueOf(id),
		          timestamp,
		          averageExecutionPrice,
		          executedAmount,
		          null,
		          orderStatus);
		    }

		    throw new NotYetImplementedForExchangeException();
		  }
	  
	  public static List<CurrencyPair> adaptCurrencyPairs(Collection<String> GeminiSymbol) {

	    List<CurrencyPair> currencyPairs = new ArrayList<>();
	    for (String symbol : GeminiSymbol) {
	      currencyPairs.add(adaptCurrencyPair(symbol));
	    }
	    return currencyPairs;
	  }
	  
	  public static CurrencyPair adaptCurrencyPair(String GeminiSymbol) {

		    String tradableIdentifier = GeminiSymbol.substring(0, 3).toUpperCase();
		    String transactionCurrency = GeminiSymbol.substring(3).toUpperCase();
		    return new CurrencyPair(tradableIdentifier, transactionCurrency);
	  }

	  private static OrderStatus adaptOrderstatus(List<Data> data) {
		  
	    if (data.get(0).getStatus() == 2) return OrderStatus.CANCELED;

	    if (data.get(0).getRemaining().equals(new BigDecimal(0.0)))
	      return OrderStatus.FILLED;

	    if (data.get(0).getRemaining().compareTo(new BigDecimal(0.0)) > 0)
	      return OrderStatus.PARTIALLY_FILLED;

	    throw new NotYetImplementedForExchangeException();
	  }
}
