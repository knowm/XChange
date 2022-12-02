package org.knowm.xchange.coinone.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinone.dto.CoinoneException;
import org.knowm.xchange.coinone.dto.trade.CoinoneTradeCancelRequest;
import org.knowm.xchange.coinone.dto.trade.CoinoneTradeResponse;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

/** @author interwater */
public class CoinoneTradeService extends CoinoneTradeServiceRaw implements TradeService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinoneTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder)
      throws IOException, NotAvailableFromExchangeException {
    CoinoneTradeResponse response = super.placeLimitOrderRaw(limitOrder);
    if (!response.getErrorCode().equals("0")) {
      throw new CoinoneException(CoinoneException.resMsgMap.get(response.getErrorCode()));
    }
    return response.getOrderId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    return null;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    CoinoneTradeResponse response = super.cancerOrder((CoinoneTradeCancelRequest) orderParams);
    if (!response.getErrorCode().equals("0")) {
      throw new CoinoneException(CoinoneException.resMsgMap.get(response.getErrorCode()));
    }
    return true;
  }
}
