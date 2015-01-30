package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.itbit.v1.ItBitAdapters;
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

    return ItBitAdapters.adaptPrivateOrders(getItBitOpenOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotYetImplementedForExchangeException();
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

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws IOException {

    String currency;
    if (arguments.length == 1) {
      CurrencyPair currencyPair = ((CurrencyPair) arguments[0]);
      currency = currencyPair.baseSymbol + currencyPair.counterSymbol;
    } else {
      currency = "XBTUSD";
    }

    return ItBitAdapters.adaptTradeHistory(getItBitTradeHistory(currency, "1", "1000"));
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
    String currency = pair.baseSymbol + pair.counterSymbol;

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
