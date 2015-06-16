package com.xeiam.xchange.bitso.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitso.BitsoAdapters;
import com.xeiam.xchange.bitso.dto.BitsoException;
import com.xeiam.xchange.bitso.dto.trade.BitsoOrder;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoTradeService extends BitsoTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitsoTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, BitsoException {

    BitsoOrder[] openOrders = getBitsoOpenOrders();

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (BitsoOrder bitsoOrder : openOrders) {
      OrderType orderType = bitsoOrder.getType() == 0 ? OrderType.BID : OrderType.ASK;
      String id = bitsoOrder.getId();
      BigDecimal price = bitsoOrder.getPrice();
      limitOrders.add(new LimitOrder(orderType, bitsoOrder.getAmount(), new CurrencyPair(Currencies.BTC, Currencies.MXN),
              id, bitsoOrder.getTime(), price));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, BitsoException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, BitsoException {

    BitsoOrder bitsoOrder;
    if (limitOrder.getType() == BID) {
      bitsoOrder = buyBitoOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    } else {
      bitsoOrder = sellBitsoOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    }
    if (bitsoOrder.getErrorMessage() != null) {
      throw new ExchangeException(bitsoOrder.getErrorMessage());
    }

    return bitsoOrder.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BitsoException {

    return cancelBitsoOrder(orderId);
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException, BitsoException {

    Long numberOfTransactions = 1000L;
    if (args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Number)) {
        throw new ExchangeException("Argument must be a Number!");
      } else {
        numberOfTransactions = ((Number) args[0]).longValue();
      }
    }

    return BitsoAdapters.adaptTradeHistory(getBitsoUserTransactions(numberOfTransactions));
  }

  /**
   * Required parameter types: {@link com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging#getPageLength()}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    return BitsoAdapters.adaptTradeHistory(getBitsoUserTransactions(Long.valueOf(((TradeHistoryParamPaging) params).getPageLength())));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new DefaultTradeHistoryParamPaging(1000);
  }

}
