package org.knowm.xchange.ftx;

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
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.RateLimit;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.account.FtxAccountDto;
import org.knowm.xchange.ftx.dto.account.FtxPositionDto;
import org.knowm.xchange.ftx.dto.account.FtxWalletBalanceDto;
import org.knowm.xchange.ftx.dto.marketdata.FtxCandleDto;
import org.knowm.xchange.ftx.dto.marketdata.FtxMarketDto;
import org.knowm.xchange.ftx.dto.marketdata.FtxMarketsDto;
import org.knowm.xchange.ftx.dto.marketdata.FtxOrderbookDto;
import org.knowm.xchange.ftx.dto.marketdata.FtxTradeDto;
import org.knowm.xchange.ftx.dto.trade.FtxModifyOrderRequestPayload;
import org.knowm.xchange.ftx.dto.trade.FtxOrderDto;
import org.knowm.xchange.ftx.dto.trade.FtxOrderFlags;
import org.knowm.xchange.ftx.dto.trade.FtxOrderRequestPayload;
import org.knowm.xchange.ftx.dto.trade.FtxOrderSide;
import org.knowm.xchange.ftx.dto.trade.FtxOrderType;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public class FtxAdapters {

  public static OrderBook adaptOrderBook(
      FtxResponse<FtxOrderbookDto> ftxOrderbookDto, CurrencyPair currencyPair) {

    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();

    ftxOrderbookDto
        .getResult()
        .getAsks()
        .forEach(
            ftxAsk ->
                asks.add(
                    adaptOrderbookOrder(
                        ftxAsk.getVolume(), ftxAsk.getPrice(), currencyPair, Order.OrderType.ASK)));

    ftxOrderbookDto
        .getResult()
        .getBids()
        .forEach(
            ftxBid ->
                bids.add(
                    adaptOrderbookOrder(
                        ftxBid.getVolume(), ftxBid.getPrice(), currencyPair, Order.OrderType.BID)));

    return new OrderBook(Date.from(Instant.now()), asks, bids);
  }

  public static LimitOrder adaptOrderbookOrder(
      BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, Order.OrderType orderType) {

    return new LimitOrder(orderType, amount, currencyPair, "", null, price);
  }

  public static AccountInfo adaptAccountInfo(
      FtxResponse<FtxAccountDto> ftxAccountDto,
      FtxResponse<List<FtxWalletBalanceDto>> ftxBalancesDto) {

    List<Balance> ftxAccountInfo = new ArrayList<>();
    List<Balance> ftxSpotBalances = new ArrayList<>();

    FtxAccountDto result = ftxAccountDto.getResult();
    ftxAccountInfo.add(
        new Balance(Currency.USD, result.getCollateral(), result.getFreeCollateral()));

    ftxBalancesDto
        .getResult()
        .forEach(
            ftxWalletBalanceDto ->
                ftxSpotBalances.add(
                    new Balance(
                        ftxWalletBalanceDto.getCoin(),
                        ftxWalletBalanceDto.getTotal(),
                        ftxWalletBalanceDto.getFree())));

    BigDecimal totalPositionSize = result.getTotalPositionSize();
    BigDecimal totalAccountValue = result.getTotalAccountValue();

    BigDecimal currentLeverage =
        totalPositionSize.compareTo(BigDecimal.ZERO) == 0
            ? BigDecimal.ZERO
            : totalPositionSize.divide(totalAccountValue, 3, RoundingMode.HALF_EVEN);
    Wallet accountWallet =
        Wallet.Builder.from(ftxAccountInfo)
            .features(
                Collections.unmodifiableSet(
                    new HashSet<>(
                        Arrays.asList(
                            Wallet.WalletFeature.MARGIN_TRADING,
                            Wallet.WalletFeature.MARGIN_FUNDING))))
            .maxLeverage(result.getLeverage())
            .currentLeverage(currentLeverage)
            .id("margin")
            .build();

    Wallet spotWallet =
        Wallet.Builder.from(ftxSpotBalances)
            .features(
                Collections.unmodifiableSet(
                    new HashSet<>(
                        Arrays.asList(Wallet.WalletFeature.FUNDING, Wallet.WalletFeature.TRADING))))
            .id("spot")
            .build();

    return new AccountInfo(
        result.getUsername(),
        result.getTakerFee(),
        Collections.unmodifiableList(Arrays.asList(accountWallet, spotWallet)),
        Date.from(Instant.now()));
  }

  public static ExchangeMetaData adaptExchangeMetaData(FtxMarketsDto marketsDto) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currency = new HashMap<>();

    marketsDto
        .getMarketList()
        .forEach(
            ftxMarketDto -> {
              CurrencyPairMetaData currencyPairMetaData =
                  new CurrencyPairMetaData.Builder()
                      .amountStepSize(ftxMarketDto.getSizeIncrement())
                      .minimumAmount(ftxMarketDto.getSizeIncrement())
                      .priceScale(ftxMarketDto.getPriceIncrement().scale())
                      .baseScale(ftxMarketDto.getSizeIncrement().scale())
                      .build();

              if ("spot".equals(ftxMarketDto.getType())) {
                CurrencyPair currencyPair =
                    new CurrencyPair(
                        ftxMarketDto.getBaseCurrency(), ftxMarketDto.getQuoteCurrency());
                currencyPairs.put(currencyPair, currencyPairMetaData);
                if (!currency.containsKey(currencyPair.base)) {
                  currency.put(
                      currencyPair.base,
                      new CurrencyMetaData(
                          ftxMarketDto.getSizeIncrement().scale(), BigDecimal.ZERO));
                }
                if (!currency.containsKey(currencyPair.counter)) {
                  currency.put(
                      currencyPair.counter,
                      new CurrencyMetaData(
                          ftxMarketDto.getPriceIncrement().scale(), BigDecimal.ZERO));
                }
              } else if ("future".equals(ftxMarketDto.getType())
                  && ftxMarketDto.getName().contains("-")) {
                CurrencyPair futuresContract = new CurrencyPair(ftxMarketDto.getName());
                currencyPairs.put(futuresContract, currencyPairMetaData);
              }
            });

    RateLimit[] rateLimits = {new RateLimit(30, 1, TimeUnit.SECONDS)};

    return new ExchangeMetaData(currencyPairs, currency, rateLimits, rateLimits, true);
  }

  public static FtxOrderRequestPayload adaptMarketOrderToFtxOrderPayload(MarketOrder marketOrder) {
    return adaptOrderToFtxOrderPayload(FtxOrderType.market, marketOrder, null);
  }

  public static FtxOrderRequestPayload adaptLimitOrderToFtxOrderPayload(LimitOrder limitOrder) {
    return adaptOrderToFtxOrderPayload(FtxOrderType.limit, limitOrder, limitOrder.getLimitPrice());
  }

  public static FtxModifyOrderRequestPayload adaptModifyOrderToFtxOrderPayload(
      LimitOrder limitOrder) {
    return new FtxModifyOrderRequestPayload(
        limitOrder.getLimitPrice(), limitOrder.getOriginalAmount(), limitOrder.getUserReference());
  }

  private static FtxOrderRequestPayload adaptOrderToFtxOrderPayload(
      FtxOrderType type, Order order, BigDecimal price) {
    return new FtxOrderRequestPayload(
        adaptCurrencyPairToFtxMarket(order.getCurrencyPair()),
        adaptOrderTypeToFtxOrderSide(order.getType()),
        price,
        type,
        order.getOriginalAmount(),
        order.hasFlag(FtxOrderFlags.REDUCE_ONLY),
        order.hasFlag(FtxOrderFlags.IOC),
        order.hasFlag(FtxOrderFlags.POST_ONLY),
        order.getUserReference());
  }

  public static Trades adaptTrades(List<FtxTradeDto> ftxTradeDtos, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<>();

    ftxTradeDtos.forEach(
        ftxTradeDto ->
            trades.add(
                new Trade.Builder()
                    .id(ftxTradeDto.getId())
                    .instrument(currencyPair)
                    .originalAmount(ftxTradeDto.getSize())
                    .price(ftxTradeDto.getPrice())
                    .timestamp(ftxTradeDto.getTime())
                    .type(adaptFtxOrderSideToOrderType(ftxTradeDto.getSide()))
                    .build()));

    return new Trades(trades);
  }

  public static UserTrades adaptUserTrades(List<FtxOrderDto> ftxUserTrades) {
    List<UserTrade> userTrades = new ArrayList<>();

    ftxUserTrades.forEach(
        ftxOrderDto -> {
          if (ftxOrderDto.getFilledSize().compareTo(BigDecimal.ZERO) != 0) {
            userTrades.add(
                new UserTrade.Builder()
                    .instrument(
                        CurrencyPairDeserializer.getCurrencyPairFromString(ftxOrderDto.getMarket()))
                    .currencyPair(
                        CurrencyPairDeserializer.getCurrencyPairFromString(ftxOrderDto.getMarket()))
                    .timestamp(ftxOrderDto.getCreatedAt())
                    .id(ftxOrderDto.getId())
                    .orderId(ftxOrderDto.getId())
                    .orderUserReference(ftxOrderDto.getClientId())
                    .originalAmount(ftxOrderDto.getFilledSize())
                    .type(adaptFtxOrderSideToOrderType(ftxOrderDto.getSide()))
                    .price(
                        ftxOrderDto.getAvgFillPrice() == null
                            ? ftxOrderDto.getPrice()
                            : ftxOrderDto.getAvgFillPrice())
                    .build());
          }
        });

    return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);
  }

  public static LimitOrder adaptLimitOrder(FtxOrderDto ftxOrderDto) {

    return new LimitOrder.Builder(
            adaptFtxOrderSideToOrderType(ftxOrderDto.getSide()),
            CurrencyPairDeserializer.getCurrencyPairFromString(ftxOrderDto.getMarket()))
        .originalAmount(ftxOrderDto.getSize())
        .limitPrice(ftxOrderDto.getPrice())
        .averagePrice(ftxOrderDto.getAvgFillPrice())
        .userReference(ftxOrderDto.getClientId())
        .timestamp(ftxOrderDto.getCreatedAt())
        .flags(
            Collections.unmodifiableSet(
                new HashSet<>(
                    Arrays.asList(
                        (ftxOrderDto.isIoc() ? FtxOrderFlags.IOC : null),
                        (ftxOrderDto.isPostOnly() ? FtxOrderFlags.POST_ONLY : null),
                        (ftxOrderDto.isReduceOnly() ? FtxOrderFlags.REDUCE_ONLY : null)))))
        .remainingAmount(ftxOrderDto.getRemainingSize())
        .orderStatus(ftxOrderDto.getStatus())
        .id(ftxOrderDto.getId())
        .build();
  }

  public static OpenOrders adaptOpenOrders(FtxResponse<List<FtxOrderDto>> ftxOpenOrdersResponse) {
    List<LimitOrder> openOrders = new ArrayList<>();

    ftxOpenOrdersResponse
        .getResult()
        .forEach(ftxOrderDto -> openOrders.add(adaptLimitOrder(ftxOrderDto)));

    return new OpenOrders(openOrders);
  }

  public static FtxOrderSide adaptOrderTypeToFtxOrderSide(Order.OrderType orderType) {

    switch (orderType) {
      case ASK:
        return FtxOrderSide.sell;
      case BID:
        return FtxOrderSide.buy;
      case EXIT_ASK:
        return FtxOrderSide.buy;
      case EXIT_BID:
        return FtxOrderSide.sell;
      default:
        return null;
    }
  }

  public static Order.OrderType adaptFtxOrderSideToOrderType(FtxOrderSide ftxOrderSide) {

    return ftxOrderSide == FtxOrderSide.buy ? Order.OrderType.BID : Order.OrderType.ASK;
  }

  private static final Pattern FUTURES_PATTERN = Pattern.compile("PERP|[0-9]+");

  public static String adaptCurrencyPairToFtxMarket(CurrencyPair currencyPair) {
    if (FUTURES_PATTERN.matcher(currencyPair.counter.getCurrencyCode()).matches()) {
      return currencyPair.base + "-" + currencyPair.counter;
    } else {
      return currencyPair.toString();
    }
  }

  public static OpenPositions adaptOpenPositions(List<FtxPositionDto> ftxPositionDtos) {
    List<OpenPosition> openPositionList = new ArrayList<>();

    ftxPositionDtos.forEach(
        ftxPositionDto -> {
          if (ftxPositionDto.getSize().compareTo(BigDecimal.ZERO) > 0) {
            openPositionList.add(
                new OpenPosition.Builder()
                    .instrument(new CurrencyPair(ftxPositionDto.getFuture()))
                    .price(ftxPositionDto.getEntryPrice())
                    .size(ftxPositionDto.getSize())
                    .type(
                        ftxPositionDto.getSide() == FtxOrderSide.buy
                            ? OpenPosition.Type.LONG
                            : OpenPosition.Type.SHORT)
                    .build());
          }
        });

    return new OpenPositions(openPositionList);
  }

  public static BigDecimal lendingRounding(BigDecimal value) {
    return value.setScale(4, RoundingMode.DOWN);
  }

  public static Ticker adaptTicker(
      FtxResponse<FtxMarketDto> ftxMarketResp,
      FtxResponse<List<FtxCandleDto>> ftxCandlesResp,
      CurrencyPair currencyPair) {

    FtxCandleDto lastCandle = ftxCandlesResp.getResult().get(ftxCandlesResp.getResult().size() - 1);

    BigDecimal open = lastCandle.getOpen();
    BigDecimal last = ftxMarketResp.getResult().getLast();
    BigDecimal bid = ftxMarketResp.getResult().getBid();
    BigDecimal ask = ftxMarketResp.getResult().getAsk();
    BigDecimal high = lastCandle.getHigh();
    BigDecimal low = lastCandle.getLow();
    BigDecimal volume = lastCandle.getVolume();
    Date timestamp = lastCandle.getStartTime();

    return new Ticker.Builder()
        .instrument(currencyPair)
        .open(open)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }
}
