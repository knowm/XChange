package com.xeiam.xchange.bitstamp.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bitstamp.BitstampAdapters;
import com.xeiam.xchange.bitstamp.dto.BitstampException;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;

/**
 * @author Matija Mazi
 */
public class BitstampTradeService extends BitstampTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitstampTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, BitstampException {

    BitstampOrder[] openOrders = getBitstampOpenOrders();

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (BitstampOrder bitstampOrder : openOrders) {
      OrderType orderType = bitstampOrder.getType() == 0 ? OrderType.BID : OrderType.ASK;
      String id = Integer.toString(bitstampOrder.getId());
      BigDecimal price = bitstampOrder.getPrice();
      limitOrders.add(new LimitOrder(orderType, bitstampOrder.getAmount(), CurrencyPair.BTC_USD, id, bitstampOrder.getTime(), price));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, BitstampException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, BitstampException {

    BitstampOrder bitstampOrder;
    if (limitOrder.getType() == BID) {
      bitstampOrder = buyBitStampOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    }
    else {
      bitstampOrder = sellBitstampOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    }
    if (bitstampOrder.getErrorMessage() != null) {
      throw new ExchangeException(bitstampOrder.getErrorMessage());
    }

    return Integer.toString(bitstampOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BitstampException {

    return cancelBitstampOrder(Integer.parseInt(orderId));
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException, BitstampException {

    Long numberOfTransactions = 1000L;
    if (args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Number)) {
        throw new ExchangeException("Argument must be a Number!");
      }
      else {
        numberOfTransactions = ((Number) args[0]).longValue();
      }
    }

    return BitstampAdapters.adaptTradeHistory(getBitstampUserTransactions(numberOfTransactions));
  }

  /**
   * Required parameter types:
   * {@link TradeHistoryParamPaging#getPageLength()}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    return BitstampAdapters.adaptTradeHistory(getBitstampUserTransactions(Long.valueOf(((TradeHistoryParamPaging)params).getPageLength())));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new DefaultTradeHistoryParamPaging(1000);
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

}
