package org.knowm.xchange.ftx;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.*;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.account.FtxAccountDto;
import org.knowm.xchange.ftx.dto.account.FtxPositionDto;
import org.knowm.xchange.ftx.dto.account.FtxWalletBalanceDto;
import org.knowm.xchange.ftx.dto.marketdata.*;
import org.knowm.xchange.ftx.dto.trade.*;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FtxAdapters {
  private static final String IMPLIED_COUNTER = "USD";
  public static final String PERPETUAL = "PERP";

  public static OrderBook adaptOrderBook(
      FtxResponse<FtxOrderbookDto> ftxOrderbookDto, Instrument instrument) {

    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();

    ftxOrderbookDto
        .getResult()
        .getAsks()
        .forEach(
            ftxAsk ->
                asks.add(
                    adaptOrderbookOrder(
                        ftxAsk.getVolume(), ftxAsk.getPrice(), instrument, Order.OrderType.ASK)));

    ftxOrderbookDto
        .getResult()
        .getBids()
        .forEach(
            ftxBid ->
                bids.add(
                    adaptOrderbookOrder(
                        ftxBid.getVolume(), ftxBid.getPrice(), instrument, Order.OrderType.BID)));

    return new OrderBook(Date.from(Instant.now()), asks, bids);
  }

  public static LimitOrder adaptOrderbookOrder(
      BigDecimal amount, BigDecimal price, Instrument instrument, Order.OrderType orderType) {

    return new LimitOrder(orderType, amount, instrument, "", null, price);
  }

  public static AccountInfo adaptAccountInfo(
      FtxResponse<FtxAccountDto> ftxAccountDto,
      FtxResponse<List<FtxWalletBalanceDto>> ftxBalancesDto,
      Collection<OpenPosition> openPositions) {

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
        openPositions,
        Date.from(Instant.now()));
  }

  public static ExchangeMetaData adaptExchangeMetaData(FtxMarketsDto marketsDto) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currency = new HashMap<>();
    Map<FuturesContract, DerivativeMetaData> futures = new HashMap<>();

    marketsDto
        .getMarketList()
        .forEach(
            ftxMarketDto -> {
              if ("spot".equals(ftxMarketDto.getType())) {
                CurrencyPair currencyPair =
                    new CurrencyPair(
                        ftxMarketDto.getBaseCurrency(), ftxMarketDto.getQuoteCurrency());

                CurrencyPairMetaData currencyPairMetaData =
                    new CurrencyPairMetaData.Builder()
                        .amountStepSize(ftxMarketDto.getPriceIncrement())
                        .minimumAmount(ftxMarketDto.getSizeIncrement())
                        .priceScale(ftxMarketDto.getPriceIncrement().scale())
                        .baseScale(ftxMarketDto.getSizeIncrement().scale())
                        .build();

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
              } else if ("future".equals(ftxMarketDto.getType())) {
                Instrument instrument = adaptFtxMarketToInstrument(ftxMarketDto.getName());
                if (instrument instanceof FuturesContract) {
                  DerivativeMetaData futuresContractMetaData =
                      new DerivativeMetaData.Builder()
                          .minimumAmount(ftxMarketDto.getMinProvideSize())
                          .amountStepSize(ftxMarketDto.getSizeIncrement())
                          .amountScale(ftxMarketDto.getSizeIncrement().scale())
                          .priceStepSize(ftxMarketDto.getPriceIncrement())
                          .priceScale(ftxMarketDto.getPriceIncrement().scale())
                          .build();

                  futures.put((FuturesContract) instrument, futuresContractMetaData);
                }
              }
            });

    RateLimit[] rateLimits = {new RateLimit(30, 1, TimeUnit.SECONDS)};

    return new ExchangeMetaData(
        currencyPairs, currency, futures, null, rateLimits, rateLimits, true);
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
        adaptInstrumentToFtxMarket(order.getInstrument()),
        adaptOrderTypeToFtxOrderSide(order.getType()),
        price,
        type,
        order.getOriginalAmount(),
        order.hasFlag(FtxOrderFlags.REDUCE_ONLY),
        order.hasFlag(FtxOrderFlags.IOC),
        order.hasFlag(FtxOrderFlags.POST_ONLY),
        order.getUserReference());
  }

  public static Trades adaptTrades(List<FtxTradeDto> ftxTradeDtos, Instrument instrument) {
    List<Trade> trades = new ArrayList<>();

    ftxTradeDtos.forEach(
        ftxTradeDto ->
            trades.add(
                new Trade.Builder()
                    .id(ftxTradeDto.getId())
                    .instrument(instrument)
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
                    .instrument(adaptFtxMarketToInstrument(ftxOrderDto.getMarket()))
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
            adaptFtxMarketToInstrument(ftxOrderDto.getMarket()))
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
        .cumulativeAmount(ftxOrderDto.getFilledSize())
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

  public static String adaptInstrumentToFtxMarket(Instrument instrument) {
    if (instrument instanceof FuturesContract) {
      FuturesContract futuresContract = (FuturesContract) instrument;
      String date;
      if (futuresContract.isPerpetual()) {
        date = PERPETUAL; 
      } else {
        ZonedDateTime zdt = futuresContract.getExpireDate().toInstant().atZone(TimeZone.getDefault().toZoneId());
        date = String.format("%02d%02d", zdt.getMonthValue(), zdt.getDayOfMonth());
      }
      return futuresContract.getCurrencyPair().base + "-" + date;
    }
    return instrument.toString();
  }

  public static Instrument adaptFtxMarketToInstrument(String marketName) {
    long count = marketName.chars().filter(ch -> ch == '/').count();
    if (count == 1) {
      return new CurrencyPair(marketName);
    }
    count = marketName.chars().filter(ch -> ch == '-').count();
    if (count == 1) {
      CurrencyPair currencyPair = new CurrencyPair(marketName.split("-")[0], IMPLIED_COUNTER);
      return new FuturesContract(currencyPair, parseFuturesContractDate(marketName));
    }
    return null;
  }

  public static OpenPositions adaptOpenPositions(List<FtxPositionDto> ftxPositionDtos) {
    List<OpenPosition> openPositionList = new ArrayList<>();

    ftxPositionDtos.forEach(
        ftxPositionDto -> {
          if (ftxPositionDto.getSize().compareTo(BigDecimal.ZERO) > 0) {
            openPositionList.add(
                new OpenPosition.Builder()
                    .instrument(adaptFtxMarketToInstrument(ftxPositionDto.getFuture()))
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
      Instrument instrument) {

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
        .instrument(instrument)
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

  private static Date parseFuturesContractDate(String name) {
    try {
      String[] split = name.split("-");
      if (PERPETUAL.equals(split[1])) {
        return null;
      }
      int m = Integer.parseInt(split[1].substring(0, 2));
      int d = Integer.parseInt(split[1].substring(2, 4));
      Instant instant =
              Instant.now().atZone(TimeZone.getDefault().toZoneId()).withMonth(m).withDayOfMonth(d).toInstant();
      if (instant.isBefore(Instant.now())) {
        instant = instant.atZone(TimeZone.getDefault().toZoneId()).plus(1, ChronoUnit.YEARS).toInstant();
      }
      return Date.from(instant);
    } catch (Exception e) {
      throw new IllegalArgumentException(
              "Could not parse futures contract from name '" + name + "'");
    }
  }
}
