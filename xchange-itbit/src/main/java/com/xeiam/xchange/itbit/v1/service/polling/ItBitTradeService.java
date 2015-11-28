package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.itbit.v1.ItBitAdapters;
import com.xeiam.xchange.itbit.v1.dto.trade.ItBitOrder;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

public class ItBitTradeService extends ItBitTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ItBitTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
	List<ItBitOrder> orders = new ArrayList<>();
	for (CurrencyPair currencyPair : getExchangeSymbols()) {
		orders.addAll(Arrays.asList(getItBitOpenOrders(currencyPair)));
	}
	ItBitOrder[] empty = {};
    return ItBitAdapters.adaptPrivateOrders(orders.isEmpty()? empty : Arrays.copyOf(orders.toArray(), orders.size(), ItBitOrder[].class));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return placeItBitLimitOrder(limitOrder).getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    cancelItBitOrder(orderId);
    return true;
  }

  /**
   * Required parameters: {@link TradeHistoryParamPaging} {@link TradeHistoryParamCurrencyPair}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
    Integer pageLength = paging.getPageLength();
    Integer pageNumber = paging.getPageNumber();

    // pages supposedly start from 1
    ++pageNumber;

    CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    String currency = pair.base.getCurrencyCode() + pair.counter.getCurrencyCode();

    return ItBitAdapters.adaptTradeHistory(getItBitTradeHistory(currency, toString(pageNumber), toString(pageLength)));
  }

  private String toString(Object o) {

    return o == null ? null : o.toString();
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    return new ItBitTradeHistoryParams();
  }

  public static class ItBitTradeHistoryParams extends DefaultTradeHistoryParamPaging implements TradeHistoryParamCurrencyPair {

    private CurrencyPair pair;

    public ItBitTradeHistoryParams() {
    }

    public ItBitTradeHistoryParams(Integer pageLength, Integer pageNumber, CurrencyPair pair) {

      super(pageLength, pageNumber);
      this.pair = pair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {

      this.pair = pair;
    }

    @Override
    public CurrencyPair getCurrencyPair() {

      return pair;
    }
  }
}
