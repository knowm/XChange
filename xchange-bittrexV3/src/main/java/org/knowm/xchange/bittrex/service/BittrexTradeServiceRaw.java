package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexConstants;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.batch.BatchResponse;
import org.knowm.xchange.bittrex.dto.batch.order.BatchOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexNewOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrders;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BittrexTradeServiceRaw extends BittrexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexTradeServiceRaw(BittrexExchange exchange) {
    super(exchange);
  }

  public String placeBittrexLimitOrder(LimitOrder limitOrder) throws IOException {
    BittrexNewOrder bittrexNewOrder =
        new BittrexNewOrder(
            BittrexUtils.toPairString(limitOrder.getCurrencyPair()),
            OrderType.BID.equals(limitOrder.getType())
                ? BittrexConstants.BUY
                : BittrexConstants.SELL,
            BittrexConstants.LIMIT,
            limitOrder.getRemainingAmount().toPlainString(),
            null,
            limitOrder.getLimitPrice().toPlainString(),
            BittrexConstants.GOOD_TIL_CANCELLED,
            null,
            null);
    return bittrexAuthenticated
        .placeOrder(
            apiKey, System.currentTimeMillis(), contentCreator, signatureCreator, bittrexNewOrder)
        .getId();
  }

  public BittrexOrder cancelBittrexLimitOrder(String orderId) throws IOException {
    return bittrexAuthenticated.cancelOrder(
        apiKey, System.currentTimeMillis(), contentCreator, signatureCreator, orderId);
  }

  public List<BittrexOrder> getBittrexOpenOrders(OpenOrdersParams params) throws IOException {
    return bittrexAuthenticated.getOpenOrders(
        apiKey, System.currentTimeMillis(), contentCreator, signatureCreator);
  }

  public SequencedOpenOrders getBittrexSequencedOpenOrders(OpenOrdersParams params)
      throws IOException {
    BittrexOrders openOrders =
        bittrexAuthenticated.getOpenOrders(
            apiKey, System.currentTimeMillis(), contentCreator, signatureCreator);
    return new SequencedOpenOrders(
        openOrders.getSequence(), BittrexAdapters.adaptOpenOrders(openOrders));
  }

  public List<BittrexOrder> getBittrexUserTradeHistory(CurrencyPair currencyPair)
      throws IOException {
    return bittrexAuthenticated.getClosedOrders(
        apiKey,
        System.currentTimeMillis(),
        contentCreator,
        signatureCreator,
        BittrexUtils.toPairString(currencyPair),
        200);
  }

  public List<BittrexOrder> getBittrexUserTradeHistory() throws IOException {
    return getBittrexUserTradeHistory(null);
  }

  public BittrexOrder getBittrexOrder(String orderId) throws IOException {
    return bittrexAuthenticated.getOrder(
        apiKey, System.currentTimeMillis(), contentCreator, signatureCreator, orderId);
  }

  public BatchResponse[] executeOrdersBatch(BatchOrder[] batchOrders) throws IOException {
    return bittrexAuthenticated.executeOrdersBatch(
        apiKey, System.currentTimeMillis(), contentCreator, signatureCreator, batchOrders);
  }

  @AllArgsConstructor
  @Getter
  public static class SequencedOpenOrders {
    private final String sequence;
    private final List<LimitOrder> openOrders;
  }
}
