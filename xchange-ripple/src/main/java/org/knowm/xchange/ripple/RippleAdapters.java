package org.knowm.xchange.ripple;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.ripple.dto.RippleAmount;
import org.knowm.xchange.ripple.dto.account.ITransferFeeSource;
import org.knowm.xchange.ripple.dto.account.RippleAccountBalances;
import org.knowm.xchange.ripple.dto.account.RippleBalance;
import org.knowm.xchange.ripple.dto.marketdata.RippleOrder;
import org.knowm.xchange.ripple.dto.marketdata.RippleOrderBook;
import org.knowm.xchange.ripple.dto.trade.IRippleTradeTransaction;
import org.knowm.xchange.ripple.dto.trade.RippleAccountOrders;
import org.knowm.xchange.ripple.dto.trade.RippleAccountOrdersBody;
import org.knowm.xchange.ripple.dto.trade.RippleLimitOrder;
import org.knowm.xchange.ripple.dto.trade.RippleUserTrade;
import org.knowm.xchange.ripple.service.RippleAccountService;
import org.knowm.xchange.ripple.service.RippleTradeServiceRaw;
import org.knowm.xchange.ripple.service.params.RippleMarketDataParams;
import org.knowm.xchange.ripple.service.params.RippleTradeHistoryPreferredCurrencies;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Various adapters for converting from Ripple DTOs to XChange DTOs */
public abstract class RippleAdapters {

  private static final Set<Currency> EMPTY_CURRENCY_SET = Collections.emptySet();

  private static final Logger logger = LoggerFactory.getLogger(RippleAdapters.class);

  /** private Constructor */
  private RippleAdapters() {}

  /** Adapts a Ripple Account to an XChange Wallet object. */
  public static AccountInfo adaptAccountInfo(
      final RippleAccountBalances account, final String username) {

    // Adapt account balances to XChange balances
    final Map<String, List<Balance>> balances = new HashMap<>();
    for (final RippleBalance balance : account.getBalances()) {
      final String walletId;
      if (balance.getCurrency().equals("XRP")) {
        walletId = null;
      } else {
        walletId = balance.getCounterparty();
      }
      if (!balances.containsKey(walletId)) {
        balances.put(walletId, new LinkedList<Balance>());
      }
      balances
          .get(walletId)
          .add(new Balance(Currency.getInstance(balance.getCurrency()), balance.getValue()));
    }

    final List<Wallet> accountInfo = new ArrayList<>(balances.size());
    for (final Map.Entry<String, List<Balance>> wallet : balances.entrySet()) {
      accountInfo.add(new Wallet(wallet.getKey(), wallet.getValue()));
    }

    return new AccountInfo(username, BigDecimal.ZERO, accountInfo);
  }

  /**
   * Adapts a Ripple OrderBook to an XChange OrderBook object. Counterparties are not mapped since
   * the application calling this should know and keep track of the counterparties it is using in
   * the polling thread.
   */
  public static OrderBook adaptOrderBook(
      final RippleOrderBook rippleOrderBook,
      final RippleMarketDataParams params,
      final CurrencyPair currencyPair) {
    final String orderBook =
        rippleOrderBook.getOrderBook(); // e.g. XRP/BTC+rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B
    final String[] splitPair = orderBook.split("/");

    final String[] baseSplit = splitPair[0].split("\\+");
    final String baseSymbol = baseSplit[0];
    if (baseSymbol.equals(currencyPair.base.getCurrencyCode()) == false) {
      throw new IllegalStateException(
          String.format(
              "base symbol in Ripple order book %s does not match requested base %s",
              orderBook, currencyPair));
    }
    final String baseCounterparty;
    if (baseSymbol.equals("XRP")) {
      baseCounterparty = ""; // native currency
    } else {
      baseCounterparty = baseSplit[1];
    }
    if (baseCounterparty.equals(params.getBaseCounterparty()) == false) {
      throw new IllegalStateException(
          String.format(
              "base counterparty in Ripple order book %s does not match requested counterparty %s",
              orderBook, params.getBaseCounterparty()));
    }

    final String[] counterSplit = splitPair[1].split("\\+");
    final String counterSymbol = counterSplit[0];
    if (counterSymbol.equals(currencyPair.counter.getCurrencyCode()) == false) {
      throw new IllegalStateException(
          String.format(
              "counter symbol in Ripple order book %s does not match requested base %s",
              orderBook, currencyPair));
    }
    final String counterCounterparty;
    if (counterSymbol.equals("XRP")) {
      counterCounterparty = ""; // native currency
    } else {
      counterCounterparty = counterSplit[1];
    }
    if (counterCounterparty.equals(params.getCounterCounterparty()) == false) {
      throw new IllegalStateException(
          String.format(
              "counter counterparty in Ripple order book %s does not match requested counterparty %s",
              orderBook, params.getCounterCounterparty()));
    }

    final List<LimitOrder> bids =
        createOrders(
            currencyPair,
            OrderType.BID,
            rippleOrderBook.getBids(),
            baseCounterparty,
            counterCounterparty);
    final List<LimitOrder> asks =
        createOrders(
            currencyPair,
            OrderType.ASK,
            rippleOrderBook.getAsks(),
            baseCounterparty,
            counterCounterparty);
    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> createOrders(
      final CurrencyPair currencyPair,
      final OrderType orderType,
      final List<RippleOrder> orders,
      final String baseCounterparty,
      final String counterCounterparty) {
    final List<LimitOrder> limitOrders = new ArrayList<>();
    for (final RippleOrder rippleOrder : orders) {

      // Taker Pays = the amount the taker must pay to consume this order.
      // Taker Gets = the amount the taker will get once the order is consumed.
      //
      // Funded vs Unfunded https://wiki.ripple.com/Unfunded_offers

      // amount of base currency
      final BigDecimal originalAmount;
      if (orderType == OrderType.BID) {
        originalAmount = rippleOrder.getTakerPaysFunded().getValue();
      } else {
        originalAmount = rippleOrder.getTakerGetsFunded().getValue();
      }

      // price in counter currency
      final BigDecimal price = rippleOrder.getPrice().getValue();

      final RippleLimitOrder order =
          new RippleLimitOrder(
              orderType,
              originalAmount,
              currencyPair,
              Integer.toString(rippleOrder.getSequence()),
              null,
              price,
              baseCounterparty,
              counterCounterparty);
      limitOrders.add(order);
    }
    return limitOrders;
  }

  /**
   * Adapts a Ripple Account Orders object to an XChange OpenOrders object Counterparties set in
   * additional data since there is no other way of the application receiving this information.
   */
  public static OpenOrders adaptOpenOrders(
      final RippleAccountOrders rippleOrders, final int scale) {
    final List<LimitOrder> list = new ArrayList<>(rippleOrders.getOrders().size());
    for (final RippleAccountOrdersBody order : rippleOrders.getOrders()) {

      final OrderType orderType;

      final RippleAmount baseAmount;
      final RippleAmount counterAmount;
      if (order.getType().equals("buy")) {
        // buying: we receive base and pay with counter, taker receives counter and pays with base
        counterAmount = order.getTakerGets();
        baseAmount = order.getTakerPays();
        orderType = OrderType.BID;
      } else {
        // selling: we receive counter and pay with base, taker receives base and pays with counter
        baseAmount = order.getTakerGets();
        counterAmount = order.getTakerPays();
        orderType = OrderType.ASK;
      }

      final String baseSymbol = baseAmount.getCurrency();
      final String counterSymbol = counterAmount.getCurrency();

      // need to provide rounding scale to prevent ArithmeticException
      final BigDecimal price =
          counterAmount
              .getValue()
              .divide(baseAmount.getValue(), scale, RoundingMode.HALF_UP)
              .stripTrailingZeros();
      final CurrencyPair pair = new CurrencyPair(baseSymbol, counterSymbol);

      final RippleLimitOrder xchangeOrder =
          (RippleLimitOrder)
              new RippleLimitOrder.Builder(orderType, pair)
                  .baseCounterparty(baseAmount.getCounterparty())
                  .counterCounterparty(counterAmount.getCounterparty())
                  .id(Long.toString(order.getSequence()))
                  .limitPrice(price)
                  .timestamp(null)
                  .originalAmount(baseAmount.getValue())
                  .build();
      list.add(xchangeOrder);
    }

    return new OpenOrders(list);
  }

  public static UserTrade adaptTrade(
      final IRippleTradeTransaction trade,
      final TradeHistoryParams params,
      final ITransferFeeSource transferFeeSource,
      final int scale)
      throws IOException {

    // The order{} section of the body cannot be used to determine trade facts e.g. if the order was
    // to sell BTC.Bitstamp and buy
    // BTC.SnapSwap, and traded via XRP, and our trade was one of the XRP legs, all we'd see would
    // be the taker getting and paying BTC.
    //
    // Details in the balance_changes{} and order_changes{} blocks are relative to the perspective
    // account, i.e. the Ripple account address used in the URI.

    final List<RippleAmount> balanceChanges = trade.getBalanceChanges();
    final Iterator<RippleAmount> iterator = balanceChanges.iterator();
    while (iterator.hasNext()) {
      final RippleAmount amount = iterator.next();
      if (amount.getCurrency().equals("XRP") && trade.getFee().equals(amount.getValue().negate())) {
        // XRP balance change is just the fee - it should not be part of the currency pair
        // considerations
        iterator.remove();
      }
    }

    if (balanceChanges.size() != 2) {
      logger.warn(
          "for hash[{}] of type[{}] balance changes section should contains 2 currency amounts but found {}",
          trade.getHash(),
          trade.getClass().getSimpleName(),
          balanceChanges);
      return null;
    }

    // There is no way of telling the original entered base or counter currency - Ripple just
    // provides 2 currency adjustments.
    // Check if TradeHistoryParams expressed a preference, otherwise arrange the currencies in the
    // order they are supplied.
    final Collection<Currency> preferredBase, preferredCounter;
    if (params instanceof RippleTradeHistoryPreferredCurrencies) {
      final RippleTradeHistoryPreferredCurrencies rippleParams =
          (RippleTradeHistoryPreferredCurrencies) params;
      preferredBase = rippleParams.getPreferredBaseCurrency();
      preferredCounter = rippleParams.getPreferredCounterCurrency();
    } else {
      preferredBase = preferredCounter = EMPTY_CURRENCY_SET;
    }

    final RippleAmount base, counter;
    if (preferredBase.contains(Currency.getInstance(balanceChanges.get(0).getCurrency()))) {
      base = balanceChanges.get(0);
      counter = balanceChanges.get(1);
    } else if (preferredBase.contains(Currency.getInstance(balanceChanges.get(1).getCurrency()))) {
      base = balanceChanges.get(1);
      counter = balanceChanges.get(0);
    } else if (preferredCounter.contains(
        Currency.getInstance(balanceChanges.get(0).getCurrency()))) {
      counter = balanceChanges.get(0);
      base = balanceChanges.get(1);
    } else if (preferredCounter.contains(
        Currency.getInstance(balanceChanges.get(1).getCurrency()))) {
      counter = balanceChanges.get(1);
      base = balanceChanges.get(0);

    } else if ((params instanceof TradeHistoryParamCurrencyPair)
        && (((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null)) {
      // Searching for a specific currency pair - use this direction
      final CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      if (pair.base.getCurrencyCode().equals(balanceChanges.get(0).getCurrency())
          && pair.counter.getCurrencyCode().equals(balanceChanges.get(1).getCurrency())) {
        base = balanceChanges.get(0);
        counter = balanceChanges.get(1);
      } else if (pair.base.getCurrencyCode().equals(balanceChanges.get(1).getCurrency())
          && pair.counter.getCurrencyCode().equals(balanceChanges.get(0).getCurrency())) {
        base = balanceChanges.get(1);
        counter = balanceChanges.get(0);
      } else {
        // Unexpected: this should have been filtered out in
        // RippleTradeServiceRaw.getTradesForAccount(..) method.
        throw new IllegalStateException(
            String.format(
                "trade history param currency filter specified %s but trade query returned %s and %s",
                pair, balanceChanges.get(0).getCurrency(), balanceChanges.get(1).getCurrency()));
      }

    } else { // select the currency direction as return from the API
      base = balanceChanges.get(0);
      counter = balanceChanges.get(1);
    }

    final OrderType type;
    if (base.getValue().signum() == 1) {
      type = OrderType.BID;
    } else {
      type = OrderType.ASK;
    }

    final String currencyPairString = base.getCurrency() + "/" + counter.getCurrency();
    final CurrencyPair currencyPair =
        CurrencyPairDeserializer.getCurrencyPairFromString(currencyPairString);

    // Ripple has 2 types of fee.
    //
    // (a) Transaction fee is a network charge levied in XRP.
    // https://wiki.ripple.com/Transaction_Fee
    //
    // (b) Transfer fee charged by the issuer levied in the currency of traded instrument. Whoever
    // sends an asset that has a transfer fee pays the fee, the receiver does not incur a charge.
    // https://wiki.ripple.com/Transit_Fee
    // https://ripple.com/knowledge_center/transfer-fees/

    // Ripple supplies XRP with net quantity and price, must apply these to the
    // trade as gross amounts to ensure the same as the other XChange connections.

    final BigDecimal baseTransferFee =
        RippleTradeServiceRaw.getExpectedTransferFee(
            transferFeeSource, base.getCounterparty(), base.getCurrency(), base.getValue(), type);
    final BigDecimal baseValue = base.getValue().abs().subtract(baseTransferFee);

    final OrderType counterDirection;
    if (type == OrderType.BID) {
      counterDirection = OrderType.ASK;
    } else {
      counterDirection = OrderType.BID;
    }
    final BigDecimal counterTransferFee =
        RippleTradeServiceRaw.getExpectedTransferFee(
            transferFeeSource,
            counter.getCounterparty(),
            counter.getCurrency(),
            counter.getValue(),
            counterDirection);
    final BigDecimal counterValue = counter.getValue().abs().subtract(counterTransferFee);

    // Account for transaction fee in quantities.
    final BigDecimal transactionFee = trade.getFee();
    final BigDecimal quantity;
    if (base.getCurrency().equals("XRP")) {
      if (type == OrderType.BID) {
        quantity = baseValue.add(transactionFee);
      } else { // OrderType.ASK
        quantity = baseValue.subtract(transactionFee);
      }
    } else {
      quantity = baseValue;
    }

    final BigDecimal counterAmount;
    if (counter.getCurrency().equals("XRP")) {
      if (type == OrderType.ASK) {
        counterAmount = counterValue.add(transactionFee);
      } else { // OrderType.BID
        counterAmount = counterValue.subtract(transactionFee);
      }
    } else {
      counterAmount = counterValue;
    }

    // need to provide rounding scale to prevent ArithmeticException
    final BigDecimal price = counterAmount.divide(quantity, scale, RoundingMode.HALF_UP);

    final String orderId = Long.toString(trade.getOrderId());

    final RippleUserTrade.Builder builder =
        (RippleUserTrade.Builder)
            new RippleUserTrade.Builder()
                .currencyPair(currencyPair)
                .feeAmount(transactionFee)
                .feeCurrency(Currency.XRP)
                .id(trade.getHash())
                .orderId(orderId)
                .price(price.stripTrailingZeros())
                .timestamp(trade.getTimestamp())
                .originalAmount(quantity.stripTrailingZeros())
                .type(type);
    builder.baseTransferFee(baseTransferFee.abs());
    builder.counterTransferFee(counterTransferFee.abs());
    if (base.getCounterparty().length() > 0) {
      builder.baseCounterparty(base.getCounterparty());
    }
    if (counter.getCounterparty().length() > 0) {
      builder.counterCounterparty(counter.getCounterparty());
    }
    return builder.build();
  }

  public static UserTrades adaptTrades(
      final Collection<IRippleTradeTransaction> tradesForAccount,
      final TradeHistoryParams params,
      final RippleAccountService accountService,
      final int roundingScale)
      throws IOException {
    final List<UserTrade> trades = new ArrayList<>();
    for (final IRippleTradeTransaction orderDetails : tradesForAccount) {
      final UserTrade trade = adaptTrade(orderDetails, params, accountService, roundingScale);
      if (trade == null) {
        // any issue should have been reported by adaptTrade
      } else {
        trades.add(trade);
      }
    }
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }
}
