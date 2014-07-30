package com.xeiam.xchange.bitstamp.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitstamp.BitstampAdapters;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

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
  public OpenOrders getOpenOrders() throws IOException {

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
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

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
  public boolean cancelOrder(String orderId) throws IOException {

    return cancelBitstampOrder(Integer.parseInt(orderId));
  }

  @Override
  public Trades getTradeHistory(Object... args) throws IOException {

    Long numberOfTransactions = Long.MAX_VALUE;
    if (args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Long)) {
        throw new ExchangeException("Argument must be of type Long!");
      }
      else {
        numberOfTransactions = (Long) args[0];
      }
    }

    return BitstampAdapters.adaptTradeHistory(getBitstampUserTransactions(numberOfTransactions));
  }

}
