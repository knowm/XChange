package org.knowm.xchange.bitso.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.BitsoAdapters;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.trade.BitsoAllOrders;
import org.knowm.xchange.bitso.dto.trade.BitsoOrderResponse;
import org.knowm.xchange.bitso.dto.trade.BitsoPlaceOrder;
import org.knowm.xchange.bitso.dto.trade.Payload;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/** @author Piotr Ładyżyński */
public class BitsoTradeService extends BitsoTradeServiceRaw implements TradeService {

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
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'SSSS");

    BitsoAllOrders openOrders = getBitsoOpenOrders();

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (Payload bitsoOrder : openOrders.getPayload()) {
      OrderType orderType = bitsoOrder.getType().equals("buy") ? OrderType.BID : OrderType.ASK;
      String id = bitsoOrder.getOid();

      BigDecimal price = new BigDecimal(bitsoOrder.getPrice());

      Date date = null;
      try {
        date = format.parse(bitsoOrder.getCreatedAt());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      String baseCurrency = bitsoOrder.getBook().split("_")[0].toUpperCase();
      String counterCurrency = bitsoOrder.getBook().split("_")[1].toUpperCase();

      CurrencyPair currencyPair = new CurrencyPair(baseCurrency, counterCurrency);
      limitOrders.add(
          new LimitOrder(
              orderType,
              new BigDecimal(bitsoOrder.getOriginalAmount()),
              currencyPair,
              id,
              date,
              price));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, BitsoException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, BitsoException {

    BitsoPlaceOrder bitsoPlaceOrder = new BitsoPlaceOrder();

    String book = limitOrder.getInstrument().toString().replace("/", "_").toLowerCase();
    String price = limitOrder.getLimitPrice().toPlainString();
    String major = limitOrder.getOriginalAmount().setScale(2, RoundingMode.HALF_UP).toPlainString();

    bitsoPlaceOrder.setBook(book);
    bitsoPlaceOrder.setPrice(price);
    bitsoPlaceOrder.setMajor(major);
    bitsoPlaceOrder.setType("limit");

    BitsoOrderResponse bitsoOrder;
    if (limitOrder.getType() == BID) {
      bitsoPlaceOrder.setSide("buy");
      // super.signatureCreator.digestParams(restInvocation);
      bitsoOrder = placeBitsOrder(bitsoPlaceOrder);

    } else {
      bitsoPlaceOrder.setSide("sell");
      bitsoOrder = placeBitsOrder(bitsoPlaceOrder);
    }
    // if (bitsoOrder.getErrorMessage() != null) {
    // throw new ExchangeException(bitsoOrder.getErrorMessage());
    // }

    return bitsoOrder.getPayload().getOid();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BitsoException {

    return cancelBitsoOrder(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /**
   * Required parameter types: {@link TradeHistoryParamPaging#getPageLength()}
   *
   * <p>Warning: using a limit here can be misleading. The underlying call retrieves trades,
   * withdrawals, and deposits. So the example here will limit the result to 17 of those types and
   * from those 17 only trades are returned. It is recommended to use the raw service demonstrated
   * below if you want to use this feature.
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    return BitsoAdapters.adaptTradeHistory(
        getBitsoUserTransactions(Long.valueOf(((TradeHistoryParamPaging) params).getPageLength())));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamPaging(1000);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    BitsoAllOrders openOrders = getBitsoOrderByIds(orderIds);

    //		OrderType type, BigDecimal originalAmount, Instrument instrument, String id, Date timestamp
    Collection<Order> ordersList = new ArrayList<>(orderIds.length);

    for (Payload payload : openOrders.getPayload()) {
      ordersList.add(BitsoAdapters.adaptOrder(payload));
    }
    return ordersList;
  }
}
