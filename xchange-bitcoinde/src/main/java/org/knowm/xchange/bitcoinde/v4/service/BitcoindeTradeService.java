package org.knowm.xchange.bitcoinde.v4.service;

import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderState;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamOffset;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.Date;

public class BitcoindeTradeService extends BitcoindeTradeServiceRaw implements TradeService {

  public BitcoindeTradeService(BitcoindeExchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new BitcoindeOpenOrdersParams(BitcoindeOrderState.PENDING);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(final OpenOrdersParams params) throws IOException {
    CurrencyPair currencyPair = null;
    BitcoindeType type = null;
    BitcoindeOrderState state = null;
    Date start = null;
    Date end = null;
    Integer offset = null;

    if (params instanceof OpenOrdersParamCurrencyPair) {
      currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
    }

    if (params instanceof BitcoindeOpenOrdersParams) {
      type = ((BitcoindeOpenOrdersParams) params).getType();
      state = ((BitcoindeOpenOrdersParams) params).getState();
      start = ((BitcoindeOpenOrdersParams) params).getStart();
      end = ((BitcoindeOpenOrdersParams) params).getEnd();
    }

    if (params instanceof OpenOrdersParamOffset) {
      offset = ((OpenOrdersParamOffset) params).getOffset();
    }

    return BitcoindeAdapters.adaptOpenOrders(
        getBitcoindeMyOrders(currencyPair, type, state, start, end, offset));
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof BitcoindeCancelOrderByIdAndCurrencyPair) {
      BitcoindeCancelOrderByIdAndCurrencyPair cob =
          (BitcoindeCancelOrderByIdAndCurrencyPair) orderParams;
      bitcoindeCancelOrders(cob.getId(), cob.getCurrencyPair());
    }

    return true;
  }
}
