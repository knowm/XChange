package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.itbit.v1.ItBitAdapters;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class ItBitTradeService extends ItBitTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchangeSpecification
   *          The {@link ExchangeSpecification}
   */
  public ItBitTradeService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return ItBitAdapters.adaptPrivateOrders(getItBitOpenOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return placeItBitLimitOrder(limitOrder).getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    cancelItBitOrder(orderId);
    return true;
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    String currency;
    if (arguments.length == 1) {
      CurrencyPair currencyPair = ((CurrencyPair) arguments[0]);
      currency = currencyPair.baseSymbol + currencyPair.counterSymbol;
    }
    else {
      currency = "XBTUSD";
    }

    return ItBitAdapters.adaptTradeHistory(getItBitTradeHistory(currency, "1", "1000"));
  }

  /**
   * Required parameters:
   * {@link TradeHistoryParamPaging}
   * {@link TradeHistoryParamCurrencyPair}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

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
  public com.xeiam.xchange.service.polling.trade.TradeHistoryParams createTradeHistoryParams() {

    return new ItBitTradeHistoryParams();
  }

  /**
   * Fetch the {@link com.xeiam.xchange.dto.marketdata.TradeServiceHelper} from the exchange.
   *
   * @return Map of currency pairs to their corresponding metadata.
   * @see com.xeiam.xchange.dto.marketdata.TradeServiceHelper
   */
  @Override public Map<CurrencyPair, ? extends TradeServiceHelper> getTradeServiceHelperMap() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  public static class ItBitTradeHistoryParams extends DefaultTradeHistoryParamPaging implements TradeHistoryParamCurrencyPair{

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
