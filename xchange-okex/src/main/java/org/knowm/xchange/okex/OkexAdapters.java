package org.knowm.xchange.okex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPosition.Type;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.account.Wallet.WalletFeature;
import org.knowm.xchange.dto.marketdata.CandleStick;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.FundingRate;
import org.knowm.xchange.dto.marketdata.FundingRate.FundingRateInterval;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.meta.WalletHealth;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.LimitOrder.Builder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.dto.OkexInstType;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.account.OkexAccountPositionRisk;
import org.knowm.xchange.okex.dto.account.OkexAccountPositionRisk.PositionData;
import org.knowm.xchange.okex.dto.account.OkexAssetBalance;
import org.knowm.xchange.okex.dto.account.OkexPosition;
import org.knowm.xchange.okex.dto.account.OkexTradeFee;
import org.knowm.xchange.okex.dto.account.OkexTradeFee.FiatList;
import org.knowm.xchange.okex.dto.account.OkexWalletBalance;
import org.knowm.xchange.okex.dto.marketdata.OkexCandleStick;
import org.knowm.xchange.okex.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.dto.marketdata.OkexFundingRate;
import org.knowm.xchange.okex.dto.marketdata.OkexInstrument;
import org.knowm.xchange.okex.dto.marketdata.OkexOrderbook;
import org.knowm.xchange.okex.dto.marketdata.OkexPublicOrder;
import org.knowm.xchange.okex.dto.marketdata.OkexTicker;
import org.knowm.xchange.okex.dto.marketdata.OkexTrade;
import org.knowm.xchange.okex.dto.trade.OkexAmendOrderRequest;
import org.knowm.xchange.okex.dto.trade.OkexOrderDetails;
import org.knowm.xchange.okex.dto.trade.OkexOrderFlags;
import org.knowm.xchange.okex.dto.trade.OkexOrderRequest;
import org.knowm.xchange.okex.dto.trade.OkexOrderType;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexAdapters {

  private static final String TRADING_WALLET_ID = "trading";
  private static final String FOUNDING_WALLET_ID = "founding";
  private static final String FUTURES_WALLET_ID = "futures";

  public static UserTrades adaptUserTrades(
      List<OkexOrderDetails> okexTradeHistory, ExchangeMetaData exchangeMetaData) {
    List<UserTrade> userTradeList = new ArrayList<>();

    okexTradeHistory.forEach(
        okexOrderDetails -> {
          Instrument instrument = adaptOkexInstrumentId(okexOrderDetails.getInstrumentId());
          userTradeList.add(
              UserTrade.builder()
                  .originalAmount(
                      convertContractSizeToVolume(
                          new BigDecimal(okexOrderDetails.getAmount()),
                          instrument,
                          exchangeMetaData.getInstruments().get(instrument).getContractValue()))
                  .instrument(instrument)
                  .price(new BigDecimal(okexOrderDetails.getAverageFilledPrice()))
                  .type(adaptOkexOrderSideToOrderType(okexOrderDetails.getSide()))
                  .id(okexOrderDetails.getOrderId())
                  .orderId(okexOrderDetails.getOrderId())
                  .timestamp(
                      Date.from(
                          Instant.ofEpochMilli(Long.parseLong(okexOrderDetails.getUpdateTime()))))
                  .feeAmount(new BigDecimal(okexOrderDetails.getFee()))
                  .feeCurrency(new Currency(okexOrderDetails.getFeeCurrency()))
                  .orderUserReference(okexOrderDetails.getClientOrderId())
                  .build());
        });

    return new UserTrades(userTradeList, TradeSortType.SortByTimestamp);
  }

  public static LimitOrder adaptOrder(OkexOrderDetails order, ExchangeMetaData exchangeMetaData) {
    Instrument instrument = adaptOkexInstrumentId(order.getInstrumentId());
    return new LimitOrder(
        "buy".equals(order.getSide()) ? OrderType.BID : OrderType.ASK,
        convertContractSizeToVolume(
            new BigDecimal(order.getAmount()),
            instrument,
            exchangeMetaData.getInstruments().get(instrument).getContractValue()),
        instrument,
        order.getOrderId(),
        new Date(Long.parseLong(order.getCreationTime())),
        new BigDecimal(order.getPrice()),
        order.getAverageFilledPrice().isEmpty()
            ? BigDecimal.ZERO
            : new BigDecimal(order.getAverageFilledPrice()),
        new BigDecimal(order.getAccumulatedFill()),
        new BigDecimal(order.getFee()),
        "live".equals(order.getState())
            ? OrderStatus.OPEN
            : OrderStatus.valueOf(order.getState().toUpperCase(Locale.ENGLISH)),
        order.getClientOrderId());
  }

  private static Order adaptOrderChange(
      OkexOrderDetails okexOrder, ExchangeMetaData exchangeMetaData) {
    Instrument instrument = adaptOkexInstrumentId(okexOrder.getInstrumentId());
    OrderType orderType = "buy".equals(okexOrder.getSide()) ? OrderType.BID : OrderType.ASK;
    Order order;
    if (okexOrder.getOrderType().equals(OkexOrderType.market.name())) {
      order =
          new MarketOrder.Builder(orderType, instrument)
              .originalAmount(
                  convertContractSizeToVolume(
                      new BigDecimal(okexOrder.getAmount()),
                      instrument,
                      exchangeMetaData.getInstruments().get(instrument).getContractValue()))
              .cumulativeAmount(
                  convertContractSizeToVolume(
                      new BigDecimal(okexOrder.getAccumulatedFill()),
                      instrument,
                      exchangeMetaData.getInstruments().get(instrument).getContractValue()))
              .id(okexOrder.getOrderId())
              .timestamp(new Date(Long.parseLong(okexOrder.getUpdateTime())))
              .averagePrice(new BigDecimal(okexOrder.getAverageFilledPrice()))
              .fee(new BigDecimal(okexOrder.getFee()).negate())
              .userReference(okexOrder.getClientOrderId())
              .orderStatus(
                  "live".equals(okexOrder.getState())
                      ? OrderStatus.OPEN
                      : OrderStatus.valueOf(okexOrder.getState().toUpperCase(Locale.ENGLISH)))
              .build();
    } else {
      order =
          new Builder(orderType, instrument)
              .originalAmount(
                  convertContractSizeToVolume(
                      new BigDecimal(okexOrder.getAmount()),
                      instrument,
                      exchangeMetaData.getInstruments().get(instrument).getContractValue()))
              .cumulativeAmount(
                  convertContractSizeToVolume(
                      new BigDecimal(okexOrder.getAccumulatedFill()),
                      instrument,
                      exchangeMetaData.getInstruments().get(instrument).getContractValue()))
              .id(okexOrder.getOrderId())
              .timestamp(new Date(Long.parseLong(okexOrder.getUpdateTime())))
              .limitPrice(
                  okexOrder.getLastFilledPrice().isEmpty()
                          || okexOrder.getLastFilledPrice().equals("0")
                      ? new BigDecimal(okexOrder.getPrice())
                      : new BigDecimal(okexOrder.getLastFilledPrice()))
              .averagePrice(new BigDecimal(okexOrder.getAverageFilledPrice()))
              .fee(new BigDecimal(okexOrder.getFee()).negate())
              .userReference(okexOrder.getClientOrderId())
              .orderStatus(
                  "live".equals(okexOrder.getState())
                      ? OrderStatus.OPEN
                      : OrderStatus.valueOf(okexOrder.getState().toUpperCase(Locale.ENGLISH)))
              .build();
    }
    return order;
  }

  public static OpenOrders adaptOpenOrders(
      List<OkexOrderDetails> orders, ExchangeMetaData exchangeMetaData) {
    List<LimitOrder> openOrders =
        orders.stream()
            .map(order -> OkexAdapters.adaptOrder(order, exchangeMetaData))
            .collect(Collectors.toList());
    return new OpenOrders(openOrders);
  }

  public static List<Order> adaptOrdersChanges(
      List<OkexOrderDetails> okexOrderDetailsList, ExchangeMetaData exchangeMetaData) {
    List<Order> orders =
        okexOrderDetailsList.stream()
            .map(order -> OkexAdapters.adaptOrderChange(order, exchangeMetaData))
            .collect(Collectors.toList());
    return orders;
  }

  public static OkexAmendOrderRequest adaptAmendOrder(
      LimitOrder order, ExchangeMetaData exchangeMetaData) {
    return OkexAmendOrderRequest.builder()
        .instrumentId(adaptInstrument(order.getInstrument()))
        .orderId(order.getId())
        .clientOrderId(order.getUserReference())
        .amendedAmount(convertVolumeToContractSize(order, exchangeMetaData))
        .amendedPrice(order.getLimitPrice().toString())
        .build();
  }

  public static OkexOrderRequest adaptOrder(
      MarketOrder order, ExchangeMetaData exchangeMetaData, String accountLevel) {
    return OkexOrderRequest.builder()
        .instrumentId(adaptInstrument(order.getInstrument()))
        .tradeMode(adaptTradeMode(order.getInstrument(), accountLevel))
        .side(getSide(order))
        .posSide(null) // PosSide should come as a input from an extended LimitOrder class to
        // support Futures/Swap capabilities of Okex, till then it should be null to
        // perform "net" orders
        .reducePosition(order.hasFlag(OkexOrderFlags.REDUCE_ONLY))
        .clientOrderId(order.getUserReference())
        .orderType(OkexOrderType.market.name())
        .amount(convertVolumeToContractSize(order, exchangeMetaData))
        .tradeQuoteCcy(order.getInstrument().getCounter().getCurrencyCode())
        .build();
  }

  /**
   * contract_size to volume: crypto-margined contracts：contract_size,volume(contract_size to
   * volume:volume = sz*ctVal/price) USDT-margined contracts:sz,volume,USDT(contract_size to
   * volume:volume = contract_size*ctVal;contract_size to USDT:volume = contract_size*ctVal*price)
   * OPTION:volume = sz*ctMult volume to contract_size: crypto-margined
   * contracts：contract_size,volume(coin to contract_size:contract_size = volume*price/ctVal)
   * USDT-margined contracts:contract_size,volume,USDT(coin to contract_size:contract_size =
   * volume/ctVal;USDT to contract_size:contract_size = volume/ctVal/price)
   */
  private static String convertVolumeToContractSize(
      Order order, ExchangeMetaData exchangeMetaData) {
    InstrumentMetaData metaData = exchangeMetaData.getInstruments().get(order.getInstrument());
    return (order.getInstrument() instanceof FuturesContract)
        ? order
            .getOriginalAmount()
            .divide(metaData.getContractValue(), 20, RoundingMode.HALF_DOWN)
            .stripTrailingZeros()
            .toPlainString()
        : order.getOriginalAmount().toString();
  }

  private static BigDecimal convertContractSizeToVolume(
      BigDecimal okexSize, Instrument instrument, BigDecimal contractValue) {
    return (instrument instanceof FuturesContract)
        ? okexSize.multiply(contractValue).stripTrailingZeros()
        : okexSize.stripTrailingZeros();
  }

  public static String adaptTradeMode(Instrument instrument, String accountLevel) {
    if (accountLevel.equals("3") || accountLevel.equals("4")) {
      return "cross";
    } else {
      return (instrument instanceof CurrencyPair) ? "cash" : "cross";
    }
  }

  public static OkexOrderRequest adaptOrder(
      LimitOrder order, ExchangeMetaData exchangeMetaData, String accountLevel) {
    return OkexOrderRequest.builder()
        .instrumentId(adaptInstrument(order.getInstrument()))
        .tradeMode(adaptTradeMode(order.getInstrument(), accountLevel))
        .side(getSide(order))
        .posSide(null) // PosSide should come as a input from an extended LimitOrder class to
        // support Futures/Swap capabilities of Okex, till then it should be null to
        // perform "net" orders
        .clientOrderId(order.getUserReference())
        .reducePosition(order.hasFlag(OkexOrderFlags.REDUCE_ONLY))
        .orderType(
            (order.hasFlag(OkexOrderFlags.POST_ONLY))
                ? OkexOrderType.post_only.name()
                : (order.hasFlag(OkexOrderFlags.OPTIMAL_LIMIT_IOC)
                        && order.getInstrument() instanceof FuturesContract)
                    ? OkexOrderType.optimal_limit_ioc.name()
                    : OkexOrderType.limit.name())
        .amount(convertVolumeToContractSize(order, exchangeMetaData))
        .price(order.getLimitPrice().toPlainString())
        .tradeQuoteCcy(order.getInstrument().getCounter().getCurrencyCode())
        .build();
  }

  private static String getSide(Order order) {
    String side = "";
    switch (order.getType()) {
      case BID:
        side = "buy";
        break;
      case ASK:
        side = "sell";
        break;
      case EXIT_ASK:
        side = "buy";
        order.getOrderFlags().add(OkexOrderFlags.REDUCE_ONLY);
        break;
      case EXIT_BID:
        side = "sell";
        order.getOrderFlags().add(OkexOrderFlags.REDUCE_ONLY);
        break;
    }
    return side;
  }

  public static LimitOrder adaptLimitOrder(
      OkexPublicOrder okexPublicOrder,
      Instrument instrument,
      OrderType orderType,
      Date timestamp,
      BigDecimal contractValue) {
    return adaptOrderbookOrder(
        convertContractSizeToVolume(okexPublicOrder.getVolume(), instrument, contractValue),
        okexPublicOrder.getPrice(),
        instrument,
        orderType,
        timestamp);
  }

  public static OrderBook adaptOrderBook(
      List<OkexOrderbook> okexOrderbooks,
      Instrument instrument,
      ExchangeMetaData exchangeMetaData) {
    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();
    Date timeStamp = new Date(Long.parseLong(okexOrderbooks.get(0).getTs()));

    okexOrderbooks
        .get(0)
        .getAsks()
        .forEach(
            okexAsk ->
                asks.add(
                    adaptLimitOrder(
                        okexAsk,
                        instrument,
                        OrderType.ASK,
                        timeStamp,
                        exchangeMetaData.getInstruments().get(instrument).getContractValue())));

    okexOrderbooks
        .get(0)
        .getBids()
        .forEach(
            okexBid ->
                bids.add(
                    adaptLimitOrder(
                        okexBid,
                        instrument,
                        OrderType.BID,
                        timeStamp,
                        exchangeMetaData.getInstruments().get(instrument).getContractValue())));

    return new OrderBook(timeStamp, asks, bids);
  }

  public static OrderBook adaptOrderBook(
      OkexResponse<List<OkexOrderbook>> okexOrderbook,
      Instrument instrument,
      ExchangeMetaData exchangeMetaData) {
    return adaptOrderBook(okexOrderbook.getData(), instrument, exchangeMetaData);
  }

  public static LimitOrder adaptOrderbookOrder(
      BigDecimal amount,
      BigDecimal price,
      Instrument instrument,
      OrderType orderType,
      Date timestamp) {

    return new LimitOrder(orderType, amount, instrument, "", timestamp, price);
  }

  public static Ticker adaptTicker(OkexTicker okexTicker) {
    BigDecimal quoteVolume = BigDecimal.ZERO;
    // for new coins 24h volume can be zero and getLast null
    if ((okexTicker.getInstrumentType().equals("SWAP")
        || okexTicker.getInstrumentType().equals("FUTURES"))) {
      if (okexTicker.getLast() != null) {
        quoteVolume = okexTicker.getVolumeCurrency24h().multiply(okexTicker.getLast());
      }
    } else {
      quoteVolume = okexTicker.getVolumeCurrency24h();
    }
    return new Ticker.Builder()
        .instrument(adaptOkexInstrumentId(okexTicker.getInstrumentId()))
        .open(okexTicker.getOpen24h())
        .last(okexTicker.getLast())
        .bid(okexTicker.getBidPrice())
        .ask(okexTicker.getAskPrice())
        .high(okexTicker.getHigh24h())
        .low(okexTicker.getLow24h())
        // .vwap(null)
        .volume(
            (okexTicker.getInstrumentType().equals("SWAP")
                    || okexTicker.getInstrumentType().equals("FUTURES"))
                ? okexTicker.getVolumeCurrency24h()
                : okexTicker.getVolume24h())
        .quoteVolume(quoteVolume)
        .timestamp(okexTicker.getTimestamp())
        .bidSize(okexTicker.getBidSize())
        .askSize(okexTicker.getAskSize())
        .percentageChange(null)
        .build();
  }

  public static Instrument adaptOkexInstrumentId(String instrumentId) {
    String[] tokens = instrumentId.split("-");
    if (tokens.length == 2) {
      // SPOT or Margin
      return new CurrencyPair(tokens[0], tokens[1]);
    } else if (tokens.length == 3) {
      // Future Or Swap
      return new FuturesContract(instrumentId.replace("-", "/"));
    } else if (tokens.length == 5) {
      // Option
      return new OptionsContract(instrumentId.replace("-", "/"));
    }
    return null;
  }

  public static String adaptInstrument(Instrument instrument) {
    if (instrument instanceof CurrencyPair) {
      CurrencyPair pair = (CurrencyPair) instrument;
      String base = pair.getBase().getCurrencyCode();
      String counter = pair.getCounter().getCurrencyCode();
      // Adapt for USDC after delist:
      // https://www.okx.com/docs-v5/log_en/#2025-08-20-unified-usd-orderbook-revamp
      if ("USDC".equals(counter)) {
        counter = "USD";
      }

      return base + "-" + counter;
    } else {
      // OKX expects DASH, not slash
      return instrument.toString().replace("/", "-");
    }
  }

  public static Trades adaptTrades(
      List<OkexTrade> okexTrades, Instrument instrument, ExchangeMetaData exchangeMetaData) {
    List<Trade> trades = new ArrayList<>();

    okexTrades.forEach(
        okexTrade ->
            trades.add(
                Trade.builder()
                    .id(okexTrade.getTradeId())
                    .instrument(instrument)
                    .originalAmount(
                        convertContractSizeToVolume(
                            okexTrade.getSz(),
                            instrument,
                            exchangeMetaData.getInstruments().get(instrument).getContractValue()))
                    .price(okexTrade.getPx())
                    .timestamp(okexTrade.getTs())
                    .type(adaptOkexOrderSideToOrderType(okexTrade.getSide()))
                    .build()));

    return new Trades(trades);
  }

  public static OrderType adaptOkexOrderSideToOrderType(String okexOrderSide) {

    return okexOrderSide.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

  private static Currency adaptCurrency(OkexCurrency currency) {
    return new Currency(currency.getCurrency());
  }

  private static int numberOfDecimals(BigDecimal value) {
    double d = value.doubleValue();
    return -(int) Math.round(Math.log10(d));
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      List<OkexInstrument> instruments, List<OkexCurrency> currs) {

    Map<Instrument, InstrumentMetaData> instrumentMetaData = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    for (OkexInstrument instrument : instruments) {
      if (!"live".equals(instrument.getState())) {
        continue;
      }

      Instrument pair = adaptOkexInstrumentId(instrument.getInstrumentId());
      /*
       TODO The Okex swap contracts with USD or USDC as counter currency
       have issue with the volume conversion (from contractSize to volumeInBaseCurrency and reverse)
       In order to fix the issue we need to change the convertContractSizeToVolume and convertVolumeToContractSize
       functions. Probably we need to add price on the function but it is not possible when we place a MarketOrder
       Because of that i think is best to leave this implementation in the future. (Critical)
      */
      if (pair instanceof FuturesContract
          && ((FuturesContract) pair).isPerpetual()
          && !pair.getCounter().equals(Currency.USDT)) {
        continue;
      }
      instrumentMetaData.put(
          pair,
          InstrumentMetaData.builder()
              .minimumAmount(
                  (instrument.getInstrumentType().equals(OkexInstType.SWAP.name()))
                      ? convertContractSizeToVolume(
                          new BigDecimal(instrument.getMinSize()),
                          pair,
                          new BigDecimal(instrument.getContractValue()))
                      : new BigDecimal(instrument.getMinSize()))
              .volumeScale(
                  (instrument.getInstrumentType().equals(OkexInstType.SWAP.name()))
                      ? convertContractSizeToVolume(
                              new BigDecimal(instrument.getMinSize()),
                              pair,
                              new BigDecimal(instrument.getContractValue()))
                          .scale()
                      : Math.max(numberOfDecimals(new BigDecimal(instrument.getMinSize())), 0))
              .amountStepSize(
                  BigDecimal.ONE.movePointLeft(
                      (instrument.getInstrumentType().equals(OkexInstType.SWAP.name()))
                          ? convertContractSizeToVolume(
                                  new BigDecimal(instrument.getLotSize()),
                                  pair,
                                  new BigDecimal(instrument.getContractValue()))
                              .scale()
                          : Math.max(numberOfDecimals(new BigDecimal(instrument.getLotSize())), 0)))
              .contractValue(
                  (instrument.getInstrumentType().equals(OkexInstType.SWAP.name()))
                      ? new BigDecimal(instrument.getContractValue())
                      : null)
              .priceScale(numberOfDecimals(new BigDecimal(instrument.getTickSize())))
              .priceStepSize(
                  BigDecimal.ONE.movePointLeft(
                      numberOfDecimals(new BigDecimal(instrument.getTickSize()))))
              .tradingFeeCurrency(Objects.requireNonNull(pair).getCounter())
              .marketOrderEnabled(true)
              .build());
    }

    if (currs != null) {
      currs.forEach(
          currency ->
              currencies.put(
                  adaptCurrency(currency),
                  new CurrencyMetaData(
                      null,
                      new BigDecimal(currency.getMaxFee()),
                      new BigDecimal(currency.getMinWd()),
                      currency.isCanWd() && currency.isCanDep()
                          ? WalletHealth.ONLINE
                          : WalletHealth.OFFLINE)));
    }

    return new ExchangeMetaData(instrumentMetaData, currencies, null, null, true);
  }

  public static Wallet adaptOkexBalances(List<OkexWalletBalance> okexWalletBalanceList) {
    List<Balance> balances = new ArrayList<>();
    if (!okexWalletBalanceList.isEmpty()) {
      OkexWalletBalance okexWalletBalance = okexWalletBalanceList.get(0);
      balances =
          Arrays.stream(okexWalletBalance.getDetails())
              .map(
                  detail ->
                      new Balance.Builder()
                          .currency(new Currency(detail.getCurrency()))
                          .total(new BigDecimal(detail.getCashBalance()))
                          .available(checkForEmpty(detail.getAvailableBalance()))
                          .timestamp(new Date())
                          .build())
              .collect(Collectors.toList());
    }

    return Wallet.Builder.from(balances)
        .id(TRADING_WALLET_ID)
        .features(new HashSet<>(Collections.singletonList(WalletFeature.TRADING)))
        .build();
  }

  public static Wallet adaptOkexAssetBalances(List<OkexAssetBalance> okexAssetBalanceList) {
    List<Balance> balances;
    balances =
        okexAssetBalanceList.stream()
            .map(
                detail ->
                    new Balance.Builder()
                        .currency(new Currency(detail.getCurrency()))
                        .total(new BigDecimal(detail.getBalance()))
                        .available(checkForEmpty(detail.getAvailableBalance()))
                        .timestamp(new Date())
                        .build())
            .collect(Collectors.toList());

    return Wallet.Builder.from(balances)
        .id(FOUNDING_WALLET_ID)
        .features(new HashSet<>(Collections.singletonList(WalletFeature.FUNDING)))
        .build();
  }

  private static BigDecimal checkForEmpty(String value) {
    return StringUtils.isEmpty(value) ? null : new BigDecimal(value);
  }

  public static CandleStickData adaptCandleStickData(
      List<OkexCandleStick> okexCandleStickList, CurrencyPair currencyPair) {
    CandleStickData candleStickData = null;
    if (!okexCandleStickList.isEmpty()) {
      List<CandleStick> candleStickList = new ArrayList<>();
      for (OkexCandleStick okexCandleStick : okexCandleStickList) {
        candleStickList.add(
            new CandleStick.Builder()
                .timestamp(new Date(okexCandleStick.getTimestamp()))
                .open(new BigDecimal(okexCandleStick.getOpenPrice()))
                .high(new BigDecimal(okexCandleStick.getHighPrice()))
                .low(new BigDecimal(okexCandleStick.getLowPrice()))
                .close(new BigDecimal(okexCandleStick.getClosePrice()))
                .volume(new BigDecimal(okexCandleStick.getVolume()))
                .quotaVolume(new BigDecimal(okexCandleStick.getVolumeCcy()))
                .build());
      }
      candleStickData = new CandleStickData(currencyPair, candleStickList);
    }
    return candleStickData;
  }

  public static OpenPositions adaptOpenPositions(
      List<OkexPosition> positions, ExchangeMetaData exchangeMetaData) {
    List<OpenPosition> openPositions = new ArrayList<>();

    positions.forEach(
        okexPosition ->
            openPositions.add(
                OpenPosition.builder()
                    .instrument(adaptOkexInstrumentId(okexPosition.getInstrumentId()))
                    .liquidationPrice(okexPosition.getLiquidationPrice())
                    .price(okexPosition.getAverageOpenPrice())
                    .type(adaptOpenPositionType(okexPosition))
                    .size(
                        okexPosition
                            .getPosition()
                            .abs()
                            .multiply(
                                exchangeMetaData
                                    .getInstruments()
                                    .get(adaptOkexInstrumentId(okexPosition.getInstrumentId()))
                                    .getContractValue()))
                    .unRealisedPnl(okexPosition.getUnrealizedPnL())
                    .build()));
    return new OpenPositions(openPositions);
  }

  public static Type adaptOpenPositionType(OkexPosition okexPosition) {
    switch (okexPosition.getPositionSide()) {
      case "long":
        return Type.LONG;
      case "short":
        return Type.SHORT;
      case "net":
        return (okexPosition.getPosition().compareTo(BigDecimal.ZERO) >= 0)
            ? Type.LONG
            : Type.SHORT;
      default:
        throw new UnsupportedOperationException();
    }
  }

  public static FundingRate adaptFundingRate(List<OkexFundingRate> okexFundingRate) {
    int interval =
        ((int)
                (okexFundingRate.get(0).getNextFundingTime().getTime()
                    - okexFundingRate.get(0).getFundingTime().getTime())
            / 3600000);
    BigDecimal fundingRate = okexFundingRate.get(0).getFundingRate();
    FundingRateInterval rateInterval = FundingRateInterval.H8;
    BigDecimal fundingRate1h = BigDecimal.ZERO;
    switch (interval) {
      case 1:
        {
          rateInterval = FundingRateInterval.H1;
          fundingRate1h = fundingRate;
          break;
        }
      case 2:
        {
          rateInterval = FundingRateInterval.H2;
          fundingRate1h =
              fundingRate.divide(
                  BigDecimal.valueOf(2), fundingRate.scale(), RoundingMode.HALF_EVEN);
          break;
        }
      case 4:
        {
          rateInterval = FundingRateInterval.H4;
          fundingRate1h =
              fundingRate.divide(
                  BigDecimal.valueOf(4), fundingRate.scale(), RoundingMode.HALF_EVEN);
          break;
        }
      case 6:
        {
          rateInterval = FundingRateInterval.H6;
          fundingRate1h =
              fundingRate.divide(
                  BigDecimal.valueOf(6), fundingRate.scale(), RoundingMode.HALF_EVEN);
          break;
        }
      case 8:
        {
          fundingRate1h =
              fundingRate.divide(
                  BigDecimal.valueOf(8), fundingRate.scale(), RoundingMode.HALF_EVEN);
          break;
        }
    }
    return new FundingRate.Builder()
        .instrument(adaptOkexInstrumentId(okexFundingRate.get(0).getInstId()))
        .fundingRate(fundingRate)
        .fundingRate1h(fundingRate1h)
        .fundingRateDate(okexFundingRate.get(0).getFundingTime())
        .fundingRateInterval(rateInterval)
        .build();
  }

  public static Wallet adaptOkexAccountPositionRisk(
      List<OkexAccountPositionRisk> accountPositionRiskData) {
    BigDecimal totalPositionValueInUsd = BigDecimal.ZERO;

    for (PositionData positionData : accountPositionRiskData.get(0).getPositionData()) {
      totalPositionValueInUsd = totalPositionValueInUsd.add(positionData.getNotionalUsdValue());
    }

    return new Wallet.Builder()
        .balances(
            Collections.singletonList(
                new Balance.Builder()
                    .currency(Currency.USD)
                    .total(accountPositionRiskData.get(0).getAdjustEquity())
                    .build()))
        .id(FUTURES_WALLET_ID)
        .currentLeverage(
            (totalPositionValueInUsd.compareTo(BigDecimal.ZERO) != 0)
                ? totalPositionValueInUsd.divide(
                    accountPositionRiskData.get(0).getAdjustEquity(), 3, RoundingMode.HALF_EVEN)
                : BigDecimal.ZERO)
        .features(new HashSet<>(Collections.singletonList(WalletFeature.FUTURES_TRADING)))
        .build();
  }

  public static Fee adaptTradingFee(
      OkexTradeFee okexTradeFee, OkexInstType okexInstType, Instrument instrument) {
    switch (okexInstType) {
      case SPOT:
        return adaptTradingFeeSPOT(okexTradeFee, instrument);
      case SWAP:
        return adaptTradingFeeSWAP(okexTradeFee, instrument);
    }
    return null;
  }

  private static Fee adaptTradingFeeSWAP(OkexTradeFee okexTradeFee, Instrument instrument) {
    if (instrument.getCounter().toString().equals("USDT")) {
      return new Fee(
          new BigDecimal(okexTradeFee.getMakerU()).negate(),
          new BigDecimal(okexTradeFee.getTakerU()).negate());
    } else {
      if (instrument.getCounter().toString().equals("USDC")) {
        return new Fee(
            new BigDecimal(okexTradeFee.getMakerUSDC()).negate(),
            new BigDecimal(okexTradeFee.getTakerUSDC()).negate());
      } else
        return new Fee(
            new BigDecimal(okexTradeFee.getMaker()).negate(),
            new BigDecimal(okexTradeFee.getTaker()).negate());
    }
  }

  private static Fee adaptTradingFeeSPOT(OkexTradeFee okexTradeFee, Instrument instrument) {
    // https://www.okx.com/docs-v5/en/#trading-account-rest-api-get-fee-rates
    if (instrument.getCounter().toString().equals("USDT")) {
      return new Fee(
          new BigDecimal(okexTradeFee.getMaker()).negate(),
          new BigDecimal(okexTradeFee.getTaker()).negate());
    } else {
      Fee tempFee = isContainsFiat(okexTradeFee.getFiatList(), instrument);
      if (tempFee != null) {
        return tempFee;
      } else {
        // represent the stablecoin besides USDT and USDC
        return new Fee(
            new BigDecimal(okexTradeFee.getMakerUSDC()).negate(),
            new BigDecimal(okexTradeFee.getTakerUSDC()).negate());
      }
    }
  }

  private static Fee isContainsFiat(List<FiatList> fiatList, Instrument instrument) {
    for (FiatList fiat : fiatList) {
      if (fiat.getCcy().equals(instrument.getCounter().toString())) {
        return new Fee(
            new BigDecimal(fiat.getMaker()).negate(), new BigDecimal(fiat.getTaker()).negate());
      }
    }
    return null;
  }

  public static List<OrderBookUpdate> adaptOrderBookUpdates(
      Instrument instrument,
      List<OkexPublicOrder> asks,
      List<OkexPublicOrder> bids,
      BigDecimal contractValue,
      Date date) {
    List<OrderBookUpdate> orderBookUpdates = new ArrayList<>();
    for (OkexPublicOrder ask : asks) {
      BigDecimal volume = convertContractSizeToVolume(ask.getVolume(), instrument, contractValue);
      OrderBookUpdate o =
          new OrderBookUpdate(OrderType.ASK, volume, instrument, ask.getPrice(), date, volume);
      orderBookUpdates.add(o);
    }
    for (OkexPublicOrder bid : bids) {
      BigDecimal volume = convertContractSizeToVolume(bid.getVolume(), instrument, contractValue);
      OrderBookUpdate o =
          new OrderBookUpdate(OrderType.BID, volume, instrument, bid.getPrice(), date, volume);
      orderBookUpdates.add(o);
    }
    return orderBookUpdates;
  }
}
