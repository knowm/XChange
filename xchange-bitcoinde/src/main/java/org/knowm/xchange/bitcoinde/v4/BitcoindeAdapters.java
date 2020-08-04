package org.knowm.xchange.bitcoinde.v4;

import static org.knowm.xchange.bitcoinde.v4.dto.BitcoindeAccountLedgerType.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import org.knowm.xchange.bitcoinde.v4.dto.*;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedger;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAllocation;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeBalance;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.*;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOrder;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOrdersWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTrade;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTradesWrapper;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public final class BitcoindeAdapters {

  /** Private constructor. */
  private BitcoindeAdapters() {}

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook object to an OrderBook
   * object.
   *
   * @param bitcoindeOrderbookWrapper the exchange specific OrderBook object
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptCompactOrderBook(
      BitcoindeCompactOrderbookWrapper bitcoindeOrderbookWrapper, CurrencyPair currencyPair) {
    final List<LimitOrder> asks =
        createCompactOrders(
            currencyPair, OrderType.ASK, bitcoindeOrderbookWrapper.getBitcoindeOrders().getAsks());
    final List<LimitOrder> bids =
        createCompactOrders(
            currencyPair, OrderType.BID, bitcoindeOrderbookWrapper.getBitcoindeOrders().getBids());

    Collections.sort(asks);
    Collections.sort(bids);

    return new OrderBook(null, asks, bids);
  }

  public static OrderBook adaptOrderBook(
      BitcoindeOrderbookWrapper asksWrapper,
      BitcoindeOrderbookWrapper bidsWrapper,
      CurrencyPair currencyPair) {
    final List<LimitOrder> asks =
        createOrders(currencyPair, OrderType.ASK, asksWrapper.getBitcoindeOrders());
    final List<LimitOrder> bids =
        createOrders(currencyPair, OrderType.BID, bidsWrapper.getBitcoindeOrders());

    Collections.sort(asks);
    Collections.sort(bids);

    return new OrderBook(null, asks, bids);
  }

  /** Create a list of orders from a list of asks or bids. */
  public static List<LimitOrder> createCompactOrders(
      CurrencyPair currencyPair, OrderType orderType, BitcoindeCompactOrder[] orders) {
    final List<LimitOrder> limitOrders = new ArrayList<>();

    for (BitcoindeCompactOrder order : orders) {
      limitOrders.add(
          new LimitOrder(orderType, order.getAmount(), currencyPair, null, null, order.getPrice()));
    }

    return limitOrders;
  }

  private static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, OrderType orderType, BitcoindeOrder[] orders) {
    final List<LimitOrder> limitOrders = new ArrayList<>();

    for (BitcoindeOrder order : orders) {
      limitOrders.add(createOrder(currencyPair, order, orderType, order.getOrderId(), null));
    }

    return limitOrders;
  }

  /** Create an individual order. */
  public static LimitOrder createOrder(
      CurrencyPair currencyPair,
      BitcoindeOrder bitcoindeOrder,
      OrderType orderType,
      String orderId,
      Date timeStamp) {
    final LimitOrder.Builder limitOrder =
        new LimitOrder.Builder(orderType, currencyPair)
            .id(orderId)
            .timestamp(timeStamp)
            .originalAmount(bitcoindeOrder.getMaxAmount())
            .limitPrice(bitcoindeOrder.getPrice())
            .orderStatus(OrderStatus.NEW);

    limitOrder.flag(
        new BitcoindeOrderFlagsOrderQuantities(
            bitcoindeOrder.getMinAmount(),
            bitcoindeOrder.getMaxAmount(),
            bitcoindeOrder.getMinVolume(),
            bitcoindeOrder.getMaxVolume()));
    if (bitcoindeOrder.getTradingPartnerInformation() != null) {
      final BitcoindeOrderFlagsTradingPartnerInformation tpi =
          new BitcoindeOrderFlagsTradingPartnerInformation(
              bitcoindeOrder.getTradingPartnerInformation().getUserName(),
              bitcoindeOrder.getTradingPartnerInformation().getKycFull(),
              bitcoindeOrder.getTradingPartnerInformation().getTrustLevel());
      tpi.setBankName(bitcoindeOrder.getTradingPartnerInformation().getBankName());
      tpi.setBic(bitcoindeOrder.getTradingPartnerInformation().getBic());
      tpi.setSeatOfBank(bitcoindeOrder.getTradingPartnerInformation().getSeatOfBank());
      tpi.setRating(bitcoindeOrder.getTradingPartnerInformation().getRating());
      tpi.setNumberOfTrades(bitcoindeOrder.getTradingPartnerInformation().getAmountTrades());
      limitOrder.flag(tpi);
    }
    if (bitcoindeOrder.getOrderRequirements() != null) {
      limitOrder.flag(adaptOrderRequirements(bitcoindeOrder.getOrderRequirements()));
    }

    return limitOrder.build();
  }

  private static BitcoindeOrderFlagsOrderRequirements adaptOrderRequirements(
      BitcoindeOrderRequirements requirements) {
    return new BitcoindeOrderFlagsOrderRequirements(
        requirements.getMinTrustLevel(),
        requirements.getOnlyKycFull(),
        requirements.getSeatOfBank(),
        requirements.getPaymentOption());
  }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade[] object to a Trades object.
   *
   * @param bitcoindeTradesWrapper Exchange specific trades
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(
      BitcoindeTradesWrapper bitcoindeTradesWrapper, CurrencyPair currencyPair) {
    final List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;

    for (BitcoindeTrade bitcoindeTrade : bitcoindeTradesWrapper.getTrades()) {
      final long tid = bitcoindeTrade.getTid();

      if (tid > lastTradeId) {
        lastTradeId = tid;
      }
      trades.add(
          new Trade.Builder()
              .originalAmount(bitcoindeTrade.getAmount())
              .currencyPair(currencyPair)
              .price(bitcoindeTrade.getPrice())
              .timestamp(bitcoindeTrade.getDate())
              .id(String.valueOf(tid))
              .build());
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeAccount object to an AccountInfo
   * object.
   *
   * @param bitcoindeAccount
   * @return AccountInfo
   */
  public static AccountInfo adaptAccountInfo(BitcoindeAccountWrapper bitcoindeAccount) {

    final boolean hasBalances =
        bitcoindeAccount.getData().getBalances() != null
            && bitcoindeAccount.getData().getBalances().size() > 0;
    final boolean hasAllocations =
        bitcoindeAccount.getData().getFidorReservation() != null
            && bitcoindeAccount.getData().getFidorReservation().getAllocation() != null
            && bitcoindeAccount.getData().getFidorReservation().getAllocation().size() > 0;

    final Map<String, BitcoindeBalance> bitcoindeBalances =
        hasBalances ? bitcoindeAccount.getData().getBalances() : new HashMap<>();
    final Map<String, BitcoindeAllocation> fidorAllocations =
        hasAllocations
            ? bitcoindeAccount.getData().getFidorReservation().getAllocation()
            : new HashMap<>();

    final Set<String> currencyStrings = new HashSet<>();
    currencyStrings.addAll(bitcoindeBalances.keySet());
    currencyStrings.addAll(fidorAllocations.keySet());

    final List<Wallet> wallets =
        currencyStrings.stream()
            .map(
                currencyString -> {
                  final Currency currency = Currency.getInstance(currencyString);
                  final List<Balance> balances = new LinkedList<>();

                  if (bitcoindeBalances.containsKey(currencyString)) {
                    final BitcoindeBalance balance = bitcoindeBalances.get(currencyString);
                    balances.add(
                        new Balance(
                            Currency.getInstance(currencyString),
                            balance.getTotalAmount(),
                            balance.getAvailableAmount(),
                            balance.getReservedAmount()));
                  }

                  if (fidorAllocations.containsKey(currencyString)) {
                    final BitcoindeAllocation allocation = fidorAllocations.get(currencyString);
                    balances.add(
                        new Balance(
                            Currency.EUR,
                            allocation.getMaxEurVolume(),
                            allocation
                                .getMaxEurVolume()
                                .subtract(allocation.getEurVolumeOpenOrders())));
                  }

                  return Wallet.Builder.from(balances).id(currency.getCurrencyCode()).build();
                })
            .collect(Collectors.toList());

    return new AccountInfo(wallets);
  }

  public static List<FundingRecord> adaptFundingHistory(
      final Currency currency,
      final List<BitcoindeAccountLedger> accountLedgers,
      final boolean leaveFeesSeperate) {

    List<BitcoindeAccountLedger> feeLedgers =
        accountLedgers.stream()
            .filter(ledger -> !leaveFeesSeperate && OUTGOING_FEE_VOLUNTARY == ledger.getType())
            .collect(Collectors.toList());

    return accountLedgers.stream()
        .filter(ledger -> SELL != ledger.getType() && BUY != ledger.getType())
        .filter(ledger -> leaveFeesSeperate || OUTGOING_FEE_VOLUNTARY != ledger.getType())
        .map(
            ledger -> {
              FundingRecord.Type type = adaptFundingRecordType(ledger.getType());

              FundingRecord.Builder builder =
                  new FundingRecord.Builder()
                      .setType(type)
                      .setDate(ledger.getDate())
                      .setCurrency(currency)
                      .setAmount(ledger.getCashflow().abs())
                      .setBalance(ledger.getBalance())
                      .setStatus(FundingRecord.Status.COMPLETE)
                      .setDescription(ledger.getType().getValue());

              if (INPAYMENT == ledger.getType() || PAYOUT == ledger.getType()) {
                builder.setBlockchainTransactionHash(ledger.getReference());
              } else {
                builder.setInternalId(ledger.getReference());
              }

              if (!leaveFeesSeperate && PAYOUT == ledger.getType()) {
                Optional<BitcoindeAccountLedger> feeLedger =
                    findFeeLedger(ledger.getReference(), feeLedgers);
                if (feeLedger.isPresent()) {
                  BigDecimal fee = feeLedger.get().getCashflow().abs();
                  builder.setAmount(ledger.getCashflow().abs().add(fee));
                  builder.setFee(fee);

                  /*
                   * There can be multiple {@code PAYOUTS}s with the same reference/ blockchain
                   * transaction id. We remove this feeLedgerEntry from the list, so it can't be
                   * applied twice.
                   */
                  feeLedgers.remove(feeLedger.get());
                }
              }

              return builder.build();
            })
        .collect(Collectors.toList());
  }

  private static Optional<BitcoindeAccountLedger> findFeeLedger(
      final String reference, final List<BitcoindeAccountLedger> feeLedgers) {
    return feeLedgers.stream()
        .filter(
            ledger ->
                OUTGOING_FEE_VOLUNTARY == ledger.getType()
                    && reference.equals(ledger.getReference()))
        .findFirst();
  }

  public static FundingRecord.Type adaptFundingRecordType(BitcoindeAccountLedgerType type) {
    switch (type) {
      case INPAYMENT:
        return FundingRecord.Type.DEPOSIT;
      case PAYOUT:
        return FundingRecord.Type.WITHDRAWAL;
      case WELCOME_BTC:
        return FundingRecord.Type.AIRDROP;
      case AFFILIATE:
      case KICKBACK:
        return FundingRecord.Type.OTHER_INFLOW;
      case BUY_YUBIKEY:
      case BUY_GOLDSHOP:
      case BUY_DIAMONDSHOP:
        return FundingRecord.Type.OTHER_OUTFLOW;
    }

    throw new IllegalArgumentException("Can't adapt \"" + type + "\" to FundingRecord.Type");
  }

  /**
   * Helper function for adapting a BitcoindeMyTradesWrapper into a list of trades sorting them with
   * respect to their timestamps.
   *
   * @param bitcoindeMyTradesWrapper
   * @return the list of trades parsed from the API without any modifications
   */
  public static UserTrades adaptTradeHistory(BitcoindeMyTradesWrapper bitcoindeMyTradesWrapper) {
    return adaptTradeHistory(bitcoindeMyTradesWrapper, TradeSortType.SortByTimestamp);
  }

  /**
   * Adapt a BitcoindeMyTradesWrapper into a list of trades.
   *
   * @param bitcoindeMyTradesWrapper
   * @param sortType Sort the trades with respect to their IDs or timestamps.
   * @return UserTrades
   */
  public static UserTrades adaptTradeHistory(
      BitcoindeMyTradesWrapper bitcoindeMyTradesWrapper, TradeSortType sortType) {
    List<BitcoindeMyTrade> trades = bitcoindeMyTradesWrapper.getTrades();

    List<UserTrade> result = new ArrayList<>(trades.size());
    for (BitcoindeMyTrade trade : trades) {
      BigDecimal fee;
      Currency feeCurrency;
      if (trade.getType() == BitcoindeType.BUY) {
        fee = trade.getFeeCurrencyToTrade();
        feeCurrency = trade.getTradingPair().base;
      } else if (trade.getType() == BitcoindeType.SELL) {
        fee = trade.getFeeCurrencyToPay();
        feeCurrency = trade.getTradingPair().counter;
      } else {
        throw new TypeNotPresentException(trade.getType().toString(), null);
      }

      Date timestamp =
          trade.getSuccessfullyFinishedAt() != null
              ? trade.getSuccessfullyFinishedAt()
              : trade.getCreatedAt();

      result.add(
          new UserTrade.Builder()
              .id(trade.getTradeId())
              .timestamp(timestamp)
              .currencyPair(trade.getTradingPair())
              .type(adaptOrderType(trade.getType()))
              .originalAmount(trade.getAmountCurrencyToTrade())
              .price(trade.getPrice())
              .feeAmount(fee)
              .feeCurrency(feeCurrency)
              .build());
    }

    return new UserTrades(result, sortType);
  }

  /**
   * @param bitcoindeOpenOrdersWrapper
   * @return
   */
  public static OpenOrders adaptOpenOrders(BitcoindeMyOrdersWrapper bitcoindeOpenOrdersWrapper) {
    final List<LimitOrder> orders = new ArrayList<>();

    for (BitcoindeMyOrder bitcoindeMyOrder : bitcoindeOpenOrdersWrapper.getOrders()) {
      final LimitOrder limitOrder =
          new LimitOrder.Builder(
                  adaptOrderType(bitcoindeMyOrder.getType()), bitcoindeMyOrder.getTradingPair())
              .id(bitcoindeMyOrder.getOrderId())
              .timestamp(bitcoindeMyOrder.getCreatedAt())
              .originalAmount(bitcoindeMyOrder.getMaxAmount())
              .limitPrice(bitcoindeMyOrder.getPrice())
              .orderStatus(adaptOrderStatus(bitcoindeMyOrder.getState()))
              .flag(
                  new BitcoindeOrderFlagsOrderQuantities(
                      bitcoindeMyOrder.getMinAmount(),
                      bitcoindeMyOrder.getMaxAmount(),
                      bitcoindeMyOrder.getMinVolume(),
                      bitcoindeMyOrder.getMaxVolume()))
              .flag(adaptOrderRequirements(bitcoindeMyOrder.getOrderRequirements()))
              .build();

      orders.add(limitOrder);
    }

    return new OpenOrders(orders);
  }

  public static OrderType adaptOrderType(final BitcoindeType type) {
    return type == BitcoindeType.BUY ? OrderType.BID : OrderType.ASK;
  }

  public static OrderStatus adaptOrderStatus(final BitcoindeOrderState state) {
    switch (state) {
      case EXPIRED:
        return OrderStatus.EXPIRED;
      case CANCELLED:
        return OrderStatus.CANCELED;
      case PENDING:
      default:
        return OrderStatus.NEW;
    }
  }
}
