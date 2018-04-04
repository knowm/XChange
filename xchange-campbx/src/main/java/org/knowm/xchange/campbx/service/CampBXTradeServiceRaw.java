package org.knowm.xchange.campbx.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.campbx.CampBX;
import org.knowm.xchange.campbx.dto.CampBXResponse;
import org.knowm.xchange.campbx.dto.trade.MyOpenOrders;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

/** @author Matija Mazi */
public class CampBXTradeServiceRaw extends CampBXBaseService {

  private static final MessageFormat ID_FORMAT = new MessageFormat("{0}-{1}");

  /**
   * Constructor
   *
   * @param exchange
   */
  public CampBXTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public MyOpenOrders getCampBXOpenOrders() throws IOException {

    MyOpenOrders myOpenOrders =
        campBX.getOpenOrders(
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getPassword());

    return myOpenOrders;
  }

  public CampBXResponse placeCampBXMarketOrder(MarketOrder marketOrder) throws IOException {

    CampBX.AdvTradeMode mode =
        marketOrder.getType() == Order.OrderType.ASK
            ? CampBX.AdvTradeMode.AdvancedSell
            : CampBX.AdvTradeMode.AdvancedBuy;
    CampBXResponse campBXResponse =
        campBX.tradeAdvancedMarketEnter(
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getPassword(),
            mode,
            marketOrder.getOriginalAmount(),
            CampBX.MarketPrice.Market,
            null,
            null,
            null);

    return campBXResponse;
  }

  public CampBXResponse placeCampBXLimitOrder(LimitOrder limitOrder) throws IOException {

    CampBX.TradeMode mode =
        limitOrder.getType() == Order.OrderType.ASK
            ? CampBX.TradeMode.QuickSell
            : CampBX.TradeMode.QuickBuy;
    CampBXResponse campBXResponse =
        campBX.tradeEnter(
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getPassword(),
            mode,
            limitOrder.getOriginalAmount(),
            limitOrder.getLimitPrice());

    return campBXResponse;
  }

  public CampBXResponse cancelCampBXOrder(String orderId) throws IOException {

    ParsedId parsedId = parseOrderId(orderId);
    CampBXResponse campBXResponse =
        campBX.tradeCancel(
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getPassword(),
            parsedId.type,
            Long.parseLong(parsedId.id));

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
