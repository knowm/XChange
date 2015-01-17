package com.xeiam.xchange.campbx.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.campbx.dto.CampBXOrder;
import com.xeiam.xchange.campbx.dto.CampBXResponse;
import com.xeiam.xchange.campbx.dto.trade.MyOpenOrders;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * @author Matija Mazi
 */
public class CampBXTradeService extends CampBXTradeServiceRaw implements PollingTradeService {

  private final Logger logger = LoggerFactory.getLogger(CampBXTradeService.class);

  private static final MessageFormat ID_FORMAT = new MessageFormat("{0}-{1}");

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CampBXTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    MyOpenOrders myOpenOrders = getCampBXOpenOrders();
    logger.debug("myOpenOrders = {}", myOpenOrders);

    if (!myOpenOrders.isError()) {

      // TODO move to adapter class
      List<LimitOrder> orders = new ArrayList<LimitOrder>();
      for (CampBXOrder cbo : myOpenOrders.getBuy()) {
        if (cbo.isError() || cbo.isInfo()) {
          logger.debug("Skipping non-order in Buy: " + cbo);
        }
        else {
          String id = composeOrderId(CampBX.OrderType.Buy, cbo.getOrderID());
          BigDecimal price = cbo.getPrice();
          orders.add(new LimitOrder(Order.OrderType.BID, cbo.getQuantity(), CurrencyPair.BTC_USD, id, cbo.getOrderEntered(), price));
        }
      }
      for (CampBXOrder cbo : myOpenOrders.getSell()) {
        if (cbo.isError() || cbo.isInfo()) {
          logger.debug("Skipping non-order in Sell: " + cbo);
        }
        else {

          String id = composeOrderId(CampBX.OrderType.Sell, cbo.getOrderID());
          BigDecimal price = cbo.getPrice();
          orders.add(new LimitOrder(Order.OrderType.ASK, cbo.getQuantity(), CurrencyPair.BTC_USD, id, cbo.getOrderEntered(), price));
        }
      }
      return new OpenOrders(orders);
    }
    else {
      throw new ExchangeException("Error calling getOpenOrders(): " + myOpenOrders.getError());
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    CampBXResponse campBXResponse = placeCampBXMarketOrder(marketOrder);
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return composeOrderId(campBXResponse.getSuccess(), marketOrder.getType());
    }
    else {
      throw new ExchangeException("Error calling placeMarketOrder(): " + campBXResponse.getError());
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    CampBXResponse campBXResponse = placeCampBXLimitOrder(limitOrder);
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return composeOrderId(campBXResponse.getSuccess(), limitOrder.getType());
    }
    else {
      throw new ExchangeException("Error calling placeLimitOrder(): " + campBXResponse.getError());
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    CampBXResponse campBXResponse = cancelCampBXOrder(orderId);
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return campBXResponse.isSuccess();
    }
    else {
      throw new ExchangeException("Error calling cancelOrder(): " + campBXResponse.getError());
    }
  }

  private String composeOrderId(String id, Order.OrderType orderType) {

    CampBX.OrderType type = orderType == Order.OrderType.ASK ? CampBX.OrderType.Sell : CampBX.OrderType.Buy;
    return composeOrderId(type, id);
  }

  private String composeOrderId(CampBX.OrderType type, String id) {

    return ID_FORMAT.format(new Object[] { type, id });
  }

  @Override
  public UserTrades getTradeHistory(Object... args) {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.TradeHistoryParams createTradeHistoryParams() {

    return null;
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
