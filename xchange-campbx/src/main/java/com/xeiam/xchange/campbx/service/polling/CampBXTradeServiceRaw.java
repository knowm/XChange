package com.xeiam.xchange.campbx.service.polling;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.campbx.dto.CampBXResponse;
import com.xeiam.xchange.campbx.dto.trade.MyOpenOrders;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

/**
 * @author Matija Mazi
 */
public class CampBXTradeServiceRaw extends CampBXBasePollingService {

  private static final MessageFormat ID_FORMAT = new MessageFormat("{0}-{1}");

  private final CampBX campbx;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CampBXTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.campbx = RestProxyFactory.createProxy(CampBX.class, exchangeSpecification.getSslUri());
  }

  public MyOpenOrders getCampBXOpenOrders() throws IOException {

    MyOpenOrders myOpenOrders = campbx.getOpenOrders(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());

    return myOpenOrders;
  }

  public CampBXResponse placeCampBXMarketOrder(MarketOrder marketOrder) throws IOException {

    CampBX.AdvTradeMode mode = marketOrder.getType() == Order.OrderType.ASK ? CampBX.AdvTradeMode.AdvancedSell : CampBX.AdvTradeMode.AdvancedBuy;
    CampBXResponse campBXResponse =
        campbx.tradeAdvancedMarketEnter(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), mode, marketOrder.getTradableAmount(), CampBX.MarketPrice.Market, null, null, null);

    return campBXResponse;
  }

  public CampBXResponse placeCampBXLimitOrder(LimitOrder limitOrder) throws IOException {

    CampBX.TradeMode mode = limitOrder.getType() == Order.OrderType.ASK ? CampBX.TradeMode.QuickSell : CampBX.TradeMode.QuickBuy;
    CampBXResponse campBXResponse = campbx.tradeEnter(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), mode, limitOrder.getTradableAmount(), limitOrder.getLimitPrice());

    return campBXResponse;
  }

  public CampBXResponse cancelCampBXOrder(String orderId) throws IOException {

    ParsedId parsedId = parseOrderId(orderId);
    CampBXResponse campBXResponse = campbx.tradeCancel(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), parsedId.type, Long.parseLong(parsedId.id));

    return campBXResponse;
  }

  private ParsedId parseOrderId(String compositeId) {

    try {
      Object[] parts = ID_FORMAT.parse(compositeId);
      return new ParsedId(CampBX.OrderType.valueOf(parts[0].toString()), parts[1].toString());
    } catch (ParseException e) {
      throw new IllegalArgumentException("Can't parse order id: " + compositeId);
    }
  }

  private class ParsedId {

    final CampBX.OrderType type;
    final String id;

    private ParsedId(CampBX.OrderType type, String id) {

      this.type = type;
      this.id = id;
    }
  }

}
