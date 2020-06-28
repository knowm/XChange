package org.knowm.xchange.bitcoinde.v4.service;

import java.io.IOException;
import java.util.Date;
import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderState;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeType;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeIdResponse;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTrade;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTradesWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamOffset;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

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
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    BitcoindeIdResponse response = bitcoindePlaceLimitOrder(limitOrder);
    return response.getId();
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

  /**
   * Create trade history parameters with state specified to "Successful"
   *
   * @return tradeHistoryParams
   */
  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    BitcoindeTradeHistoryParams params = new BitcoindeTradeHistoryParams();
    params.setState(BitcoindeMyTrade.State.SUCCESSFUL);

    return params;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    CurrencyPair currencyPair = null;
    BitcoindeType type = null;
    BitcoindeMyTrade.State state = null;
    Boolean onlyTradesWithActionForPaymentOrTransferRequired = null;
    BitcoindeMyTrade.PaymentMethod paymentMethod = null;
    Date start = null;
    Date end = null;
    Integer pageNumber = null;

    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }

    if (params instanceof BitcoindeTradeHistoryParams) {
      if (((BitcoindeTradeHistoryParams) params).getType() != null) {
        switch (((BitcoindeTradeHistoryParams) params).getType()) {
          case BID:
            type = BitcoindeType.BUY;
            break;
          case ASK:
            type = BitcoindeType.SELL;
            break;
          default:
            throw new IllegalArgumentException(
                "Unsupported Order.OrderType: " + ((BitcoindeTradeHistoryParams) params).getType());
        }
      }

      state = ((BitcoindeTradeHistoryParams) params).getState();
      onlyTradesWithActionForPaymentOrTransferRequired =
          ((BitcoindeTradeHistoryParams) params)
              .getOnlyTradesWithActionForPaymentOrTransferRequired();
      paymentMethod = ((BitcoindeTradeHistoryParams) params).getPaymentMethod();
    }

    if (params instanceof TradeHistoryParamsTimeSpan) {
      start = ((TradeHistoryParamsTimeSpan) params).getStartTime();
      end = ((TradeHistoryParamsTimeSpan) params).getEndTime();
    }

    if (params instanceof TradeHistoryParamPaging) {
      pageNumber = ((TradeHistoryParamPaging) params).getPageNumber();
    }

    BitcoindeMyTradesWrapper result =
        getBitcoindeMyTrades(
            currencyPair,
            type,
            state,
            onlyTradesWithActionForPaymentOrTransferRequired,
            paymentMethod,
            start,
            end,
            pageNumber);

    // Report back paging information to user to enable efficient paging
    if (params instanceof BitcoindeTradeHistoryParams) {
      ((BitcoindeTradeHistoryParams) params).setPageNumber(result.getPage().getCurrent());
      ((BitcoindeTradeHistoryParams) params).setLastPageNumber(result.getPage().getLast());
    }

    return BitcoindeAdapters.adaptTradeHistory(result);
  }
}
