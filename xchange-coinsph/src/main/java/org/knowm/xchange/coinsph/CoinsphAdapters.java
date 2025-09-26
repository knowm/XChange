package org.knowm.xchange.coinsph;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.coinsph.dto.account.CoinsphAccount;
import org.knowm.xchange.coinsph.dto.account.CoinsphBalance;
import org.knowm.xchange.coinsph.dto.account.CoinsphDepositRecord;
import org.knowm.xchange.coinsph.dto.account.CoinsphFundingRecord;
import org.knowm.xchange.coinsph.dto.account.CoinsphTradeFee;
import org.knowm.xchange.coinsph.dto.account.CoinsphWithdrawalRecord;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphOrderBook;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphOrderBookEntry;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphPublicTrade;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphTicker;
import org.knowm.xchange.coinsph.dto.meta.CoinsphExchangeInfo;
import org.knowm.xchange.coinsph.dto.meta.CoinsphSymbol;
import org.knowm.xchange.coinsph.dto.trade.CoinsphOrder;
import org.knowm.xchange.coinsph.dto.trade.CoinsphUserTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;

public final class CoinsphAdapters {

  // Enum for Coins.ph specific order flags, if any (e.g. for quoteOrderQty)
  public enum CoinsphOrderFlags implements Order.IOrderFlags {
    QUOTE_ORDER_QTY // Used for market orders to specify the amount of quote asset to spend
  }

  private CoinsphAdapters() {
    // Private constructor for utility class
  }

  public static String toSymbol(CurrencyPair currencyPair) {
    if (currencyPair == null) {
      return null;
    }
    return currencyPair.getBase().getCurrencyCode() + currencyPair.getCounter().getCurrencyCode();
  }

  public static String toSymbol(Instrument instrument) {
    if (instrument == null) {
      return null;
    }
    return toSymbol(new CurrencyPair(instrument.getBase(), instrument.getCounter()));
  }

  public static CurrencyPair toCurrencyPair(String symbol) {
    if (symbol == null || symbol.length() < 6) { // Assuming symbols like BTCPHP (3+3 chars)
      return null;
    }
    // This is a common way, but Coins.ph might have fixed length for base/quote
    // Need to confirm from their symbol list or exchangeInfo
    // For now, assume 3-char base and 3-char counter for common pairs like BTCPHP
    // Or, more robustly, iterate through known symbols from exchangeInfo
    // A simple split might not work for all cases (e.g. USDTPHP vs BTCUSDT)
    // Let's assume for now a common pattern or rely on exchangeInfo for parsing
    // For BTCPHP: base=BTC, counter=PHP
    // For ETHPHP: base=ETH, counter=PHP
    // For BTCUSDT: base=BTC, counter=USDT
    // A common approach is to check against known quote currencies.
    // For now, a placeholder, this needs to be robust.
    // A common pattern is that the last 3 or 4 chars are the quote.
    String counter = symbol.substring(symbol.length() - 3);
    String base = symbol.substring(0, symbol.length() - 3);
    if (counter.equals("SDT")) counter = "USDT"; // common case for USDT

    // A more robust way would be to use the list of symbols from exchangeInfo
    // to determine base and counter. For now, this is a simplification.
    return new CurrencyPair(base, counter);
  }

  public static AccountInfo adaptAccountInfo(CoinsphAccount coinsphAccount, String username) {
    List<Balance> balances = new ArrayList<>();
    if (coinsphAccount.getBalances() != null) {
      for (CoinsphBalance coinsphBalance : coinsphAccount.getBalances()) {
        balances.add(
            new Balance(
                new Currency(coinsphBalance.getAsset()),
                coinsphBalance.getFree().add(coinsphBalance.getLocked()), // total = free + locked
                coinsphBalance.getFree(),
                coinsphBalance.getLocked()));
      }
    }
    return new AccountInfo(username, Wallet.Builder.from(balances).build());
  }

  public static ExchangeMetaData adaptExchangeMetaData(CoinsphExchangeInfo exchangeInfo) {
    List<CoinsphSymbol> symbols = exchangeInfo.getSymbols();
    java.util.Map<Instrument, InstrumentMetaData> currencyPairs = new java.util.HashMap<>();
    java.util.Map<Currency, CurrencyMetaData> currencies = new java.util.HashMap<>();

    for (CoinsphSymbol symbol : symbols) {
      CurrencyPair pair = toCurrencyPair(symbol.getSymbol());
      if (pair == null) continue; // Skip if symbol parsing fails

      // For now, using defaults or placeholders
      InstrumentMetaData pairMetaData =
          InstrumentMetaData.builder()
              .tradingFee(null) // tradingFee
              .minimumAmount(null) // minimumAmount
              .maximumAmount(null) // maximumAmount
              .priceScale(
                  symbol.getQuoteAssetPrecision()) // priceScale (assuming quoteAssetPrecision is
              // price scale)
              .feeTiers(null) // feeTiers
              .build();
      currencyPairs.put(pair, pairMetaData);

      if (!currencies.containsKey(pair.getBase())) {
        currencies.put(
            pair.getBase(),
            new CurrencyMetaData(symbol.getBaseAssetPrecision(), null)); // scale, fee
      }
      if (!currencies.containsKey(pair.getCounter())) {
        currencies.put(
            pair.getCounter(),
            new CurrencyMetaData(symbol.getQuoteAssetPrecision(), null)); // scale, fee
      }
    }

    return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
  }

  public static Ticker adaptTicker(CoinsphTicker coinsphTicker) {
    if (coinsphTicker == null) {
      return null;
    }
    CurrencyPair pair = toCurrencyPair(coinsphTicker.getSymbol());
    return new Ticker.Builder()
        .instrument(pair)
        .open(coinsphTicker.getOpenPrice())
        .last(coinsphTicker.getLastPrice())
        .bid(coinsphTicker.getBidPrice())
        .ask(coinsphTicker.getAskPrice())
        .high(coinsphTicker.getHighPrice())
        .low(coinsphTicker.getLowPrice())
        .volume(coinsphTicker.getVolume())
        .quoteVolume(coinsphTicker.getQuoteVolume())
        .timestamp(new Date(coinsphTicker.getCloseTime())) // Using closeTime as timestamp
        .bidSize(coinsphTicker.getBidQty())
        .askSize(coinsphTicker.getAskQty())
        .percentageChange(coinsphTicker.getPriceChangePercent())
        .build();
  }

  public static List<Ticker> adaptTickers(List<CoinsphTicker> coinsphTickers) {
    if (coinsphTickers == null) {
      return Collections.emptyList();
    }
    return coinsphTickers.stream().map(CoinsphAdapters::adaptTicker).collect(Collectors.toList());
  }

  private static List<LimitOrder> adaptOrderBookList(
      List<CoinsphOrderBookEntry> entries, OrderType orderType, CurrencyPair currencyPair) {
    return entries.stream()
        .map(
            entry ->
                new LimitOrder(
                    orderType, entry.getQuantity(), currencyPair, null, null, entry.getPrice()))
        .collect(Collectors.toList());
  }

  public static OrderBook adaptOrderBook(
      CoinsphOrderBook coinsphOrderBook, CurrencyPair currencyPair) {
    if (coinsphOrderBook == null) {
      return null;
    }
    List<LimitOrder> asks =
        adaptOrderBookList(coinsphOrderBook.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids =
        adaptOrderBookList(coinsphOrderBook.getBids(), OrderType.BID, currencyPair);
    return new OrderBook(
        new Date(coinsphOrderBook.getLastUpdateId()),
        asks,
        bids); // Assuming lastUpdateId is a timestamp
  }

  public static Trade adaptTrade(CoinsphPublicTrade coinsphTrade, CurrencyPair currencyPair) {
    return Trade.builder()
        .instrument(currencyPair)
        .originalAmount(coinsphTrade.getQty())
        .price(coinsphTrade.getPrice())
        .timestamp(new Date(coinsphTrade.getTime()))
        .id(String.valueOf(coinsphTrade.getId()))
        .type(
            coinsphTrade.isBuyerMaker()
                ? OrderType.ASK
                : OrderType.BID) // if buyer is maker, it was a sell order that got filled
        .build();
  }

  public static Trades adaptTrades(
      List<CoinsphPublicTrade> coinsphTrades, CurrencyPair currencyPair) {
    List<Trade> trades =
        coinsphTrades.stream()
            .map(trade -> adaptTrade(trade, currencyPair))
            .collect(Collectors.toList());
    // Coins.ph trades are sorted old to new. XChange expects new to old.
    Collections.reverse(trades);
    // lastID can be used for pagination if needed, not directly part of Trades DTO
    long lastId =
        coinsphTrades.isEmpty() ? 0L : coinsphTrades.get(coinsphTrades.size() - 1).getId();
    return new Trades(trades, lastId, Trades.TradeSortType.SortByTimestamp);
  }

  public static org.knowm.xchange.coinsph.dto.trade.CoinsphOrderSide toSide(OrderType orderType) {
    switch (orderType) {
      case BID:
      case EXIT_ASK: // Assuming exit ask is a form of buy
        return org.knowm.xchange.coinsph.dto.trade.CoinsphOrderSide.BUY;
      case ASK:
      case EXIT_BID: // Assuming exit bid is a form of sell
        return org.knowm.xchange.coinsph.dto.trade.CoinsphOrderSide.SELL;
      default:
        throw new IllegalArgumentException("Unsupported order type: " + orderType);
    }
  }

  public static org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType toCoinsphOrderType(
      Order order) {
    if (order instanceof LimitOrder) {
      return org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType.LIMIT;
    } else if (order instanceof MarketOrder) {
      return org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType.MARKET;
    } else if (order instanceof org.knowm.xchange.dto.trade.StopOrder) {
      // For StopOrder, the specific type (STOP_LOSS, STOP_LOSS_LIMIT, etc.)
      // is determined in CoinsphTradeServiceRaw based on presence of limit price.
      // This adapter might need more context or be called differently for stop orders.
      // For now, throwing, as direct mapping is ambiguous here.
      // Or, could default to STOP_LOSS if no limit price, STOP_LOSS_LIMIT if limit price.
      // This logic is better handled in the service layer (CoinsphTradeServiceRaw).
      if (((org.knowm.xchange.dto.trade.StopOrder) order).getLimitPrice() != null) {
        return org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType
            .STOP_LOSS_LIMIT; // Or TAKE_PROFIT_LIMIT
      } else {
        return org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType.STOP_LOSS; // Or TAKE_PROFIT
      }
    }
    throw new IllegalArgumentException(
        "Unsupported order class for direct CoinsphOrderType mapping: "
            + order.getClass().getName());
  }

  public static OrderType adaptOrderType(String side) {
    switch (side.toUpperCase()) {
      case "BUY":
        return OrderType.BID;
      case "SELL":
        return OrderType.ASK;
      default:
        return null; // Or throw exception
    }
  }

  public static org.knowm.xchange.dto.Order adaptOrder(CoinsphOrder coinsphOrder) {
    if (coinsphOrder == null) {
      return null;
    }
    OrderType type = adaptOrderType(coinsphOrder.getSide());
    CurrencyPair pair = toCurrencyPair(coinsphOrder.getSymbol());
    Date timestamp = new Date(coinsphOrder.getTime()); // Or updateTime if more appropriate

    BigDecimal executedQty = coinsphOrder.getExecutedQty();
    BigDecimal cummulativeQuoteQty = coinsphOrder.getCummulativeQuoteQty();
    BigDecimal averagePrice = null;
    if (executedQty != null
        && executedQty.compareTo(BigDecimal.ZERO) > 0
        && cummulativeQuoteQty != null) {
      try {
        // Ensure quote currency precision is appropriate here if known, otherwise using a default
        // like 8
        averagePrice = cummulativeQuoteQty.divide(executedQty, 8, java.math.RoundingMode.HALF_UP);
      } catch (ArithmeticException e) {
        // This might happen if executedQty is extremely small, leading to precision issues
        // Or if cummulativeQuoteQty is 0 and executedQty is also 0 (already handled by compareTo)
        // Log error or handle as appropriate; for now, averagePrice remains null
      }
    }

    LimitOrder.Builder builder =
        new LimitOrder.Builder(type, pair)
            .id(String.valueOf(coinsphOrder.getOrderId()))
            .originalAmount(coinsphOrder.getOrigQty())
            .cumulativeAmount(executedQty) // Use the variable already fetched
            .timestamp(timestamp)
            .orderStatus(adaptOrderStatus(coinsphOrder.getStatus()))
            .limitPrice(coinsphOrder.getPrice()) // Price is present for limit orders
            .averagePrice(averagePrice)
            .userReference(coinsphOrder.getClientOrderId());
    return builder.build();
  }

  public static org.knowm.xchange.dto.Order.OrderStatus adaptOrderStatus(String coinsphStatus) {
    if (coinsphStatus == null) return org.knowm.xchange.dto.Order.OrderStatus.UNKNOWN;
    switch (coinsphStatus.toUpperCase()) {
      case "NEW":
        return org.knowm.xchange.dto.Order.OrderStatus.NEW;
      case "PARTIALLY_FILLED":
        return org.knowm.xchange.dto.Order.OrderStatus.PARTIALLY_FILLED;
      case "FILLED":
        return org.knowm.xchange.dto.Order.OrderStatus.FILLED;
      case "CANCELED": // Spelled with one L in Coins.ph docs
        return org.knowm.xchange.dto.Order.OrderStatus.CANCELED;
      case "PENDING_CANCEL":
        return org.knowm.xchange.dto.Order.OrderStatus.PENDING_CANCEL;
      case "REJECTED":
        return org.knowm.xchange.dto.Order.OrderStatus.REJECTED;
      case "EXPIRED":
        return org.knowm.xchange.dto.Order.OrderStatus.EXPIRED;
      default:
        return org.knowm.xchange.dto.Order.OrderStatus.UNKNOWN;
    }
  } // Added missing closing brace for the method adaptOrderStatus

  public static OpenOrders adaptOpenOrders(List<CoinsphOrder> coinsphOrders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    List<Order> otherOrders = new ArrayList<>(); // For any non-limit orders if applicable

    if (coinsphOrders != null) {
      for (CoinsphOrder coinsphOrder : coinsphOrders) {
        Order order = adaptOrder(coinsphOrder);
        if (order instanceof LimitOrder) {
          limitOrders.add((LimitOrder) order);
        } else {
          // Market orders usually don't appear in open orders lists once (partially) filled
          // If Coins.ph can have other types of open orders, handle them here
          otherOrders.add(order);
        }
      }
    }
    return new OpenOrders(limitOrders, otherOrders);
  }

  public static Map<Instrument, Fee> adaptTradeFees(List<CoinsphTradeFee> coinsphTradeFees) {
    Map<Instrument, org.knowm.xchange.dto.account.Fee> fees = new HashMap<>();
    if (coinsphTradeFees != null) {
      for (CoinsphTradeFee fee : coinsphTradeFees) {
        Instrument instrument = toCurrencyPair(fee.getSymbol());
        if (instrument != null) {
          // Assuming maker and taker are distinct fees.
          // XChange Fee DTO takes one maker and one taker fee.
          fees.put(
              instrument,
              new org.knowm.xchange.dto.account.Fee(
                  fee.getMakerCommission(), fee.getTakerCommission()));
        }
      }
    }
    return fees;
  }

  public static UserTrade adaptUserTrade(CoinsphUserTrade coinsphTrade) {
    if (coinsphTrade == null) {
      return null;
    }
    CurrencyPair currencyPair = toCurrencyPair(coinsphTrade.getSymbol());
    OrderType orderType = coinsphTrade.isBuyer() ? OrderType.BID : OrderType.ASK;
    Date timestamp = new Date(coinsphTrade.getTime());
    String tradeId = String.valueOf(coinsphTrade.getId());
    String orderId = String.valueOf(coinsphTrade.getOrderId());

    return UserTrade.builder()
        .instrument(currencyPair)
        .id(tradeId)
        .orderId(orderId)
        .timestamp(timestamp)
        .type(orderType)
        .price(coinsphTrade.getPrice())
        .originalAmount(coinsphTrade.getQty())
        .feeAmount(coinsphTrade.getCommission())
        .feeCurrency(Currency.getInstance(coinsphTrade.getCommissionAsset()))
        // .orderUserReference(null) // Not directly available in CoinsphUserTrade
        .build();
  }

  public static UserTrades adaptUserTrades(List<CoinsphUserTrade> coinsphTrades) {
    if (coinsphTrades == null) {
      return new UserTrades(Collections.emptyList(), Trades.TradeSortType.SortByTimestamp);
    }
    List<UserTrade> trades =
        coinsphTrades.stream().map(CoinsphAdapters::adaptUserTrade).collect(Collectors.toList());
    // Coins.ph /myTrades are sorted old to new by default (by tradeId).
    // XChange UserTrades are typically sorted by timestamp, newest first.
    // The list from stream().map() will preserve original order.
    // If sorting is needed (e.g. newest first), do it here.
    // For now, assume the order from API is acceptable or will be handled by caller.
    // The API docs say "If fromId (tradeId) is set, it will get id (tradeId) >= that fromId
    // (tradeId).
    // Otherwise most recent trades are returned." This implies newest first if fromId is not used.
    // If fromId is used, it's oldest first from that ID.
    // XChange expects newest first. So if fromId is used, we might need to reverse.
    // However, the `Trades.TradeSortType.SortByTimestamp` implies the list should be sorted by
    // time.
    // The API returns trades by tradeId, which generally correlates with time.
    // For now, let's not reverse, assuming "most recent" means newest first.
    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  /**
   * Adapts a CoinsphDepositRecord to an XChange FundingRecord
   *
   * @param depositRecord Coins.ph deposit record
   * @return XChange FundingRecord
   */
  public static FundingRecord adaptDepositRecord(CoinsphDepositRecord depositRecord) {
    FundingRecord.Status status;
    switch (depositRecord.getStatus()) {
      case 0:
        status = FundingRecord.Status.PROCESSING;
        break;
      case 1:
      case 3:
        status = FundingRecord.Status.COMPLETE;
        break;
      case 2:
        status = FundingRecord.Status.FAILED;
        break;
      default:
        status = FundingRecord.Status.PROCESSING;
    }

    return FundingRecord.builder()
        .address(depositRecord.getAddress())
        .addressTag(depositRecord.getAddressTag())
        .amount(depositRecord.getAmount())
        .currency(new Currency(depositRecord.getCoin()))
        .date(new Date(depositRecord.getInsertTime()))
        .fee(BigDecimal.ZERO) // Deposits typically don't have fees
        .internalId(depositRecord.getId())
        .status(status)
        .type(FundingRecord.Type.DEPOSIT)
        .description("Deposit via " + depositRecord.getNetwork())
        .blockchainTransactionHash(depositRecord.getTxId())
        .build();
  }

  /**
   * Adapts a CoinsphWithdrawalRecord to an XChange FundingRecord
   *
   * @param withdrawalRecord Coins.ph withdrawal record
   * @return XChange FundingRecord
   */
  public static FundingRecord adaptWithdrawalRecord(CoinsphWithdrawalRecord withdrawalRecord) {
    FundingRecord.Status status;
    switch (withdrawalRecord.getStatus()) {
      case 0:
        status = FundingRecord.Status.PROCESSING;
        break;
      case 1:
        status = FundingRecord.Status.COMPLETE;
        break;
      case 2:
        status = FundingRecord.Status.FAILED;
        break;
      default:
        status = FundingRecord.Status.PROCESSING;
    }

    String description = withdrawalRecord.getInfo();
    if (description == null || description.isEmpty()) {
      description = "Withdrawal via " + withdrawalRecord.getNetwork();
    }

    return FundingRecord.builder()
        .address(withdrawalRecord.getAddress())
        .addressTag(withdrawalRecord.getAddressTag())
        .amount(withdrawalRecord.getAmount())
        .currency(new Currency(withdrawalRecord.getCoin()))
        .date(new Date(withdrawalRecord.getApplyTime()))
        .fee(withdrawalRecord.getTransactionFee())
        .internalId(withdrawalRecord.getId())
        .status(status)
        .type(FundingRecord.Type.WITHDRAWAL)
        .description(description)
        .blockchainTransactionHash(withdrawalRecord.getTxId())
        .build();
  }

  /**
   * Adapts a CoinsphFundingRecord to an XChange FundingRecord
   *
   * @param fundingRecord Coins.ph funding record
   * @return XChange FundingRecord
   */
  public static FundingRecord adaptFundingRecord(CoinsphFundingRecord fundingRecord) {
    FundingRecord.Status status;
    switch (fundingRecord.getStatus()) {
      case 0: // 0 - PROCESSING
        status = FundingRecord.Status.PROCESSING;
        break;
      case 1: // 1 - SUCCESS
      case 3: // 3 - NEED_FILL_DATA(travel rule info), however money is available to user
        status = FundingRecord.Status.COMPLETE;
        break;
      case 2: // 2 - FAILED
        status = FundingRecord.Status.FAILED;
        break;
      default:
        status = FundingRecord.Status.PROCESSING;
    }

    FundingRecord.Type type =
        fundingRecord.getType() == CoinsphFundingRecord.Type.DEPOSIT
                || fundingRecord.getType() == CoinsphFundingRecord.Type.FIAT_DEPOSIT
            ? FundingRecord.Type.DEPOSIT
            : FundingRecord.Type.WITHDRAWAL;

    return FundingRecord.builder()
        .address(fundingRecord.getAddress())
        .addressTag(fundingRecord.getAddressTag())
        .amount(fundingRecord.getAmount())
        .currency(new Currency(fundingRecord.getCurrency()))
        .date(fundingRecord.getTimestamp())
        .fee(fundingRecord.getFee())
        .internalId(fundingRecord.getId())
        .status(status)
        .type(type)
        .description(fundingRecord.getDescription())
        .blockchainTransactionHash(fundingRecord.getTxId())
        .build();
  }

  /**
   * Adapts a list of CoinsphFundingRecords to a list of XChange FundingRecords
   *
   * @param fundingRecords List of Coins.ph funding records
   * @return List of XChange FundingRecords
   */
  public static List<FundingRecord> adaptFundingRecords(List<CoinsphFundingRecord> fundingRecords) {
    if (fundingRecords == null) {
      return Collections.emptyList();
    }
    return fundingRecords.stream()
        .map(CoinsphAdapters::adaptFundingRecord)
        .collect(Collectors.toList());
  }
}
