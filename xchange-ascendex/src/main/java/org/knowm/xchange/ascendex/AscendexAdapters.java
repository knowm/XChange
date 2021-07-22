package org.knowm.xchange.ascendex;

import org.knowm.xchange.ascendex.dto.account.AscendexCashAccountBalanceDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexAssetDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexMarketTradesDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexOrderbookDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexProductDto;
import org.knowm.xchange.ascendex.dto.trade.AscendexFlags;
import org.knowm.xchange.ascendex.dto.trade.AscendexOpenOrdersResponse;
import org.knowm.xchange.ascendex.dto.trade.AscendexPlaceOrderRequestPayload;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

import java.time.Instant;
import java.util.*;

public class AscendexAdapters {

  public static OrderBook adaptOrderBook(AscendexOrderbookDto ascendexOrderbookDto) {
    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();

    CurrencyPair currencyPair =
        CurrencyPairDeserializer.getCurrencyPairFromString(ascendexOrderbookDto.getSymbol());

    ascendexOrderbookDto
        .getData()
        .getAsks()
        .forEach(
            askOrder ->
                asks.add(
                    new LimitOrder.Builder(Order.OrderType.ASK, currencyPair)
                        .limitPrice(askOrder.getPrice())
                        .originalAmount(askOrder.getVolume())
                        .build()));

    ascendexOrderbookDto
        .getData()
        .getBids()
        .forEach(
            bidOrder ->
                bids.add(
                    new LimitOrder.Builder(Order.OrderType.BID, currencyPair)
                        .limitPrice(bidOrder.getPrice())
                        .originalAmount(bidOrder.getVolume())
                        .build()));

    return new OrderBook(ascendexOrderbookDto.getData().getTimestamp(), asks, bids);
  }

  public static AccountInfo adaptAccountInfo(
      List<AscendexCashAccountBalanceDto> ascendexCashAccountBalanceDtoList) {
    List<Balance> balances = new ArrayList<>();

    ascendexCashAccountBalanceDtoList.forEach(
            ascendexCashAccountBalanceDto ->
            balances.add(
                new Balance.Builder()
                    .currency(new Currency(ascendexCashAccountBalanceDto.getAsset()))
                    .available(ascendexCashAccountBalanceDto.getAvailableBalance())
                    .total(ascendexCashAccountBalanceDto.getTotalBalance())
                    .frozen(
                        ascendexCashAccountBalanceDto
                            .getTotalBalance()
                            .subtract(ascendexCashAccountBalanceDto.getAvailableBalance()))
                    .build()));

    return new AccountInfo(
        Wallet.Builder.from(balances)
            .id("spot")
            .features(new HashSet<>(Collections.singletonList(Wallet.WalletFeature.TRADING)))
            .build());
  }

  public static AscendexPlaceOrderRequestPayload adaptLimitOrderToBitmaxPlaceOrderRequestPayload(
      LimitOrder limitOrder) {
    return new AscendexPlaceOrderRequestPayload(
        limitOrder.getInstrument().toString(),
        Date.from(Instant.now()).toInstant().toEpochMilli(),
        limitOrder.getOriginalAmount().toString(),
        AscendexPlaceOrderRequestPayload.BitmaxOrderType.limit,
        limitOrder.getType().equals(Order.OrderType.ASK)
            ? AscendexPlaceOrderRequestPayload.BitmaxSide.sell
            : AscendexPlaceOrderRequestPayload.BitmaxSide.buy,
        null,
        limitOrder.getLimitPrice().toString(),
        null,
        limitOrder.hasFlag(AscendexFlags.POST_ONLY),
        null,
        null);
  }

  public static UserTrades adaptUserTrades(
      List<AscendexOpenOrdersResponse> bitmaxOrderHistoryResponse) {
    List<UserTrade> userTrades = new ArrayList<>();

    bitmaxOrderHistoryResponse.forEach(
        order ->
            userTrades.add(
                new UserTrade.Builder()
                    .feeAmount(order.getCumFee())
                    .orderId(order.getOrderId())
                    .price(order.getPrice())
                    .type(adaptBitmaxSideToOrderType(order.getSide()))
                    .originalAmount(order.getOrderQty())
                    .id(order.getOrderId())
                    .timestamp(order.getLastExecTime())
                    .currencyPair(
                        CurrencyPairDeserializer.getCurrencyPairFromString(order.getSymbol()))
                    .feeCurrency(new Currency(order.getFeeAsset()))
                    .instrument(
                        CurrencyPairDeserializer.getCurrencyPairFromString(order.getSymbol()))
                    .build()));

    return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);
  }

  public static OpenOrders adaptOpenOrders(
      List<AscendexOpenOrdersResponse> ascendexOpenOrdersRespons) {
    List<LimitOrder> openOrders = new ArrayList<>();

    ascendexOpenOrdersRespons.forEach(
            ascendexOpenOrdersResponse ->
            openOrders.add(
                new LimitOrder.Builder(
                        adaptBitmaxSideToOrderType(ascendexOpenOrdersResponse.getSide()),
                        CurrencyPairDeserializer.getCurrencyPairFromString(
                            ascendexOpenOrdersResponse.getSymbol()))
                    .originalAmount(ascendexOpenOrdersResponse.getOrderQty())
                    .limitPrice(ascendexOpenOrdersResponse.getPrice())
                    .fee(ascendexOpenOrdersResponse.getCumFee())
                    .averagePrice(ascendexOpenOrdersResponse.getAvgPx())
                    .id(ascendexOpenOrdersResponse.getOrderId())
                    .timestamp(ascendexOpenOrdersResponse.getLastExecTime())
                    .orderStatus(
                        Order.OrderStatus.valueOf(
                            ascendexOpenOrdersResponse.getStatus().toUpperCase()))
                    .remainingAmount(
                        ascendexOpenOrdersResponse
                            .getOrderQty()
                            .subtract(ascendexOpenOrdersResponse.getCumFilledQty()))
                    .flag(
                        (ascendexOpenOrdersResponse.getExecInst().equals("POST"))
                            ? AscendexFlags.POST_ONLY
                            : null)
                    .build()));

    return new OpenOrders(openOrders);
  }

  public static List<Order> adaptOpenOrderById(AscendexOpenOrdersResponse ascendexOpenOrdersResponse) {
    List<Order> openOrders = new ArrayList<>();

    openOrders.add(
        new LimitOrder.Builder(
                adaptBitmaxSideToOrderType(ascendexOpenOrdersResponse.getSide()),
                CurrencyPairDeserializer.getCurrencyPairFromString(
                    ascendexOpenOrdersResponse.getSymbol()))
            .originalAmount(ascendexOpenOrdersResponse.getOrderQty())
            .limitPrice(ascendexOpenOrdersResponse.getPrice())
            .fee(ascendexOpenOrdersResponse.getCumFee())
            .averagePrice(ascendexOpenOrdersResponse.getAvgPx())
            .id(ascendexOpenOrdersResponse.getOrderId())
            .timestamp(ascendexOpenOrdersResponse.getLastExecTime())
            .orderStatus(
                Order.OrderStatus.valueOf(ascendexOpenOrdersResponse.getStatus().toUpperCase()))
            .remainingAmount(
                ascendexOpenOrdersResponse
                    .getOrderQty()
                    .subtract(ascendexOpenOrdersResponse.getCumFilledQty()))
            .flag(
                (ascendexOpenOrdersResponse.getExecInst().equals("POST"))
                    ? AscendexFlags.POST_ONLY
                    : null)
            .build());

    return openOrders;
  }

  public static ExchangeMetaData adaptExchangeMetaData(
          List<AscendexAssetDto> ascendexAssetDtos, List<AscendexProductDto> ascendexProductDtos) {
    Map<Currency, CurrencyMetaData> currencyMetaDataMap = new HashMap<>();
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairMetaDataMap = new HashMap<>();

    ascendexAssetDtos.forEach(
        bitmaxAssetDto ->
            currencyMetaDataMap.put(
                new Currency(bitmaxAssetDto.getAssetCode()),
                new CurrencyMetaData(
                    bitmaxAssetDto.getPrecisionScale(),
                    bitmaxAssetDto.getWithdrawFee(),
                    bitmaxAssetDto.getMinWithdrawalAmt())));

    ascendexProductDtos.forEach(
            ascendexProductDto ->
            currencyPairMetaDataMap.put(
                CurrencyPairDeserializer.getCurrencyPairFromString(ascendexProductDto.getSymbol()),
                new CurrencyPairMetaData.Builder()
                    .tradingFee(ascendexProductDto.getCommissionReserveRate())
                    .priceScale(ascendexProductDto.getTickSize().scale())
                    .baseScale(ascendexProductDto.getLotSize().scale())
                    .counterMaximumAmount(ascendexProductDto.getMinNotional())
                    .counterMaximumAmount(ascendexProductDto.getMaxNotional())
                    .amountStepSize(ascendexProductDto.getTickSize())
                    .build()));

    return new ExchangeMetaData(currencyPairMetaDataMap, currencyMetaDataMap, null, null, null);
  }

  public static Order.OrderType adaptBitmaxSideToOrderType(
      AscendexPlaceOrderRequestPayload.BitmaxSide bitmaxSide) {
    if (bitmaxSide.equals(AscendexPlaceOrderRequestPayload.BitmaxSide.buy)) {
      return Order.OrderType.BID;
    } else {
      return Order.OrderType.ASK;
    }
  }

  public static Trades adaptTrades(AscendexMarketTradesDto marketTradesDto) {
    List<Trade> trades = new ArrayList<>();

    marketTradesDto
        .getData()
        .forEach(
            bitmaxMarketTradesData ->
                trades.add(
                    new Trade.Builder()
                        .price(bitmaxMarketTradesData.getPrice())
                        .originalAmount(bitmaxMarketTradesData.getQuantity())
                        .timestamp(bitmaxMarketTradesData.getTimestamp())
                        .instrument(new CurrencyPair(marketTradesDto.getSymbol()))
                        .type(
                            bitmaxMarketTradesData.isBuyerMaker()
                                ? Order.OrderType.ASK
                                : Order.OrderType.BID)
                        .build()));

    return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
  }
}
