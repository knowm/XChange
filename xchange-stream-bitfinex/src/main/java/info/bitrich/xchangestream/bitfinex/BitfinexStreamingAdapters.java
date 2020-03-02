package info.bitrich.xchangestream.bitfinex;

import static java.util.stream.StreamSupport.stream;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthBalance;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthOrder;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthPreTrade;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthTrade;
import io.reactivex.annotations.Nullable;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.bitfinex.v1.BitfinexOrderType;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BitfinexStreamingAdapters {

  private static final Logger LOG = LoggerFactory.getLogger(BitfinexStreamingAdapters.class);

  private static final BigDecimal THOUSAND = new BigDecimal(1000);

  @Nullable
  static BitfinexWebSocketAuthPreTrade adaptPreTrade(JsonNode preTrade) {
    if (preTrade.size() < 12) {
      LOG.error(
          "addPreTrade unexpected record size={}, record={}", preTrade.size(), preTrade.toString());
      return null;
    }
    long id = preTrade.get(0).longValue();
    String pair = preTrade.get(1).textValue();
    long mtsCreate = preTrade.get(2).longValue();
    long orderId = preTrade.get(3).longValue();
    BigDecimal execAmount = preTrade.get(4).decimalValue();
    BigDecimal execPrice = preTrade.get(5).decimalValue();
    String orderType = preTrade.get(6).textValue();
    BigDecimal orderPrice = preTrade.get(7).decimalValue();
    int maker = preTrade.get(8).intValue();
    BitfinexWebSocketAuthPreTrade preTradeObject =
        new BitfinexWebSocketAuthPreTrade(
            id, pair, mtsCreate, orderId, execAmount, execPrice, orderType, orderPrice, maker);
    LOG.debug("New pre trade: {}", preTradeObject);
    return preTradeObject;
  }

  @Nullable
  static BitfinexWebSocketAuthTrade adaptTrade(JsonNode trade) {
    if (trade.size() < 11) {
      LOG.error("addTrade unexpected record size={}, record={}", trade.size(), trade.toString());
      return null;
    }
    long id = trade.get(0).longValue();
    String pair = trade.get(1).textValue();
    long mtsCreate = trade.get(2).longValue();
    long orderId = trade.get(3).longValue();
    BigDecimal execAmount = trade.get(4).decimalValue();
    BigDecimal execPrice = trade.get(5).decimalValue();
    String orderType = trade.get(6).textValue();
    BigDecimal orderPrice = trade.get(7).decimalValue();
    int maker = trade.get(8).intValue();
    BigDecimal fee = trade.get(9).decimalValue();
    String currency = trade.get(10).textValue();
    BitfinexWebSocketAuthTrade tradeObject =
        new BitfinexWebSocketAuthTrade(
            id,
            pair,
            mtsCreate,
            orderId,
            execAmount,
            execPrice,
            orderType,
            orderPrice,
            maker,
            fee,
            currency);
    LOG.debug("New trade: {}", tradeObject);
    return tradeObject;
  }

  static Stream<BitfinexWebSocketAuthOrder> adaptOrders(JsonNode orders) {
    Iterable<JsonNode> iterator = () -> orders.iterator();
    return stream(iterator.spliterator(), false)
        .filter(o -> o.size() >= 32)
        .map(BitfinexStreamingAdapters::createOrderObject)
        .peek(o -> LOG.debug("New order: {}", o));
  }

  @Nullable
  static BitfinexWebSocketAuthOrder adaptOrder(JsonNode order) {
    BitfinexWebSocketAuthOrder orderObject = createOrderObject(order);
    if (orderObject == null) {
      return null;
    }
    LOG.debug("Updated order: {}", orderObject);
    return orderObject;
  }

  @Nullable
  static BitfinexWebSocketAuthBalance adaptBalance(JsonNode balance) {
    BitfinexWebSocketAuthBalance balanceObject = createBalanceObject(balance);
    if (balanceObject == null) {
      return null;
    }
    LOG.debug("Balance: {}", balanceObject);
    return balanceObject;
  }

  static Stream<BitfinexWebSocketAuthBalance> adaptBalances(JsonNode balances) {
    Iterable<JsonNode> iterator = () -> balances.iterator();
    return stream(iterator.spliterator(), false)
        .filter(o -> o.size() >= 5)
        .map(BitfinexStreamingAdapters::createBalanceObject)
        .peek(o -> LOG.debug("Balance: {}", o));
  }

  @Nullable
  private static BitfinexWebSocketAuthBalance createBalanceObject(JsonNode balance) {
    if (balance.size() < 5) {
      LOG.error(
          "createBalanceObject unexpected record size={}, record={}",
          balance.size(),
          balance.toString());
      return null;
    }

    String walletType = balance.get(0).textValue();
    String currency = balance.get(1).textValue();
    BigDecimal balanceValue = balance.get(2).decimalValue();
    BigDecimal unsettledInterest = balance.get(3).decimalValue();
    BigDecimal balanceAvailable =
        balance.get(4).asText().equals("null") ? null : balance.get(4).decimalValue();

    return new BitfinexWebSocketAuthBalance(
        walletType, currency, balanceValue, unsettledInterest, balanceAvailable);
  }

  @Nullable
  private static BitfinexWebSocketAuthOrder createOrderObject(JsonNode order) {
    if (order.size() < 32) {
      LOG.error(
          "createOrderObject unexpected record size={}, record={}", order.size(), order.toString());
      return null;
    }

    long id = order.get(0).longValue();
    long groupId = order.get(1).longValue();
    long cid = order.get(2).longValue();
    String symbol = order.get(3).textValue();
    long mtsCreate = order.get(4).longValue();
    long mtsUpdate = order.get(5).longValue();
    BigDecimal amount = order.get(6).decimalValue();
    BigDecimal amountOrig = order.get(7).decimalValue();
    String type = order.get(8).textValue();
    String typePrev = order.get(9).textValue();
    int flags = order.get(12).intValue();
    String orderStatus = order.get(13).textValue();
    BigDecimal price = order.get(16).decimalValue();
    BigDecimal priceAvg = order.get(17).decimalValue();
    BigDecimal priceTrailing = order.get(18).decimalValue();
    BigDecimal priceAuxLimit = order.get(19).decimalValue();
    long placedId = order.get(25).longValue();

    return new BitfinexWebSocketAuthOrder(
        id,
        groupId,
        cid,
        symbol,
        mtsCreate,
        mtsUpdate,
        amount,
        amountOrig,
        type,
        typePrev,
        orderStatus,
        price,
        priceAvg,
        priceTrailing,
        priceAuxLimit,
        placedId,
        flags);
  }

  private static BitfinexOrderType adaptV2OrderTypeToV1(String orderType) {
    switch (orderType) {
      case "LIMIT":
        return BitfinexOrderType.MARGIN_LIMIT;
      case "MARKET":
        return BitfinexOrderType.MARGIN_MARKET;
      case "STOP":
        return BitfinexOrderType.MARGIN_STOP;
      case "TRAILING STOP":
        return BitfinexOrderType.MARGIN_TRAILING_STOP;
      case "EXCHANGE MARKET":
        return BitfinexOrderType.MARKET;
      case "EXCHANGE LIMIT":
        return BitfinexOrderType.LIMIT;
      case "EXCHANGE STOP":
        return BitfinexOrderType.STOP;
      case "EXCHANGE TRAILING STOP":
        return BitfinexOrderType.TRAILING_STOP;
      case "FOK":
        return BitfinexOrderType.MARGIN_FILL_OR_KILL;
      case "EXCHANGE FOK":
        return BitfinexOrderType.FILL_OR_KILL;
      default:
        return BitfinexOrderType.LIMIT; // Safe fallback
    }
  }

  private static String adaptV2SymbolToV1(String symbol) {
    return symbol.substring(1);
  }

  /**
   * We adapt the websocket message to what we expect from the V1 REST API, so that we don't
   * re-implement the complex logic which works out whether we need limit orders, stop orders,
   * market orders etc.
   */
  private static BitfinexOrderStatusResponse adaptOrderToRestResponse(
      BitfinexWebSocketAuthOrder authOrder) {
    int signum = authOrder.getAmountOrig().signum();
    return new BitfinexOrderStatusResponse(
        authOrder.getId(),
        adaptV2SymbolToV1(authOrder.getSymbol()),
        authOrder.getPrice(),
        authOrder.getPriceAvg(),
        signum > 0 ? "buy" : "sell",
        adaptV2OrderTypeToV1(authOrder.getType()).getValue(),
        new BigDecimal(authOrder.getMtsCreate()).divide(THOUSAND),
        "ACTIVE".equals(authOrder.getOrderStatus()),
        "CANCELED".equals(authOrder.getOrderStatus())
            || "FILLORKILL CANCELED".equals(authOrder.getOrderStatus()),
        false, // wasForced,
        signum >= 0 ? authOrder.getAmountOrig() : authOrder.getAmountOrig().negate(),
        signum >= 0 ? authOrder.getAmount() : authOrder.getAmount().negate(),
        signum >= 0
            ? authOrder.getAmountOrig().subtract(authOrder.getAmount())
            : authOrder.getAmountOrig().subtract(authOrder.getAmount()).negate());
  }

  static Order adaptOrder(BitfinexWebSocketAuthOrder authOrder) {
    BitfinexOrderStatusResponse[] orderStatus = {adaptOrderToRestResponse(authOrder)};
    OpenOrders orders = BitfinexAdapters.adaptOrders(orderStatus);
    if (orders.getOpenOrders().isEmpty()) {
      if (orders.getHiddenOrders().isEmpty()) {
        throw new IllegalStateException("No order in message");
      }
      return orders.getHiddenOrders().get(0);
    }
    return orders.getOpenOrders().get(0);
  }

  static UserTrade adaptUserTrade(BitfinexWebSocketAuthTrade authTrade) {
    return new UserTrade.Builder()
        .currencyPair(BitfinexAdapters.adaptCurrencyPair(adaptV2SymbolToV1(authTrade.getPair())))
        .feeAmount(authTrade.getFee().abs())
        .feeCurrency(Currency.getInstance(authTrade.getFeeCurrency()))
        .id(Long.toString(authTrade.getId()))
        .orderId(Long.toString(authTrade.getOrderId()))
        .originalAmount(authTrade.getExecAmount().abs())
        .price(authTrade.getExecPrice())
        .timestamp(DateUtils.fromMillisUtc(authTrade.getMtsCreate()))
        .type(authTrade.getExecAmount().signum() == 1 ? BID : ASK)
        .build();
  }

  static Balance adaptBalance(BitfinexWebSocketAuthBalance authBalance) {
    return new Balance(
        Currency.getInstance(authBalance.getCurrency()),
        authBalance.getBalance(),
        authBalance.getBalanceAvailable());
  }
}
