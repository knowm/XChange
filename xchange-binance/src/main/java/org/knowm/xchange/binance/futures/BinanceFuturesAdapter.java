package org.knowm.xchange.binance.futures;

import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAccountInformation;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.PositionSide;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BinanceFuturesAdapter {
    public static AccountInfo adaptAccountInfo(BinanceFuturesAccountInformation account) {
        List<Balance> balances =
                account.assets.stream()
                        .map(b -> new Balance(
                                Currency.getInstance(b.asset),
                                b.walletBalance,
                                b.availableBalance))
                        .collect(Collectors.toList());

        List<OpenPosition> openPositions =
                account.positions.stream()
                        .map(p -> new OpenPosition(
                                new FuturesContract(BinanceAdapters.adaptSymbol(p.symbol), null),
                                adaptPositionType(p.positionSide),
                                p.positionAmt,
                                p.entryPrice
                        ))
                        .collect(Collectors.toList());

        return new AccountInfo(null, null, Collections.singleton(Wallet.Builder.from(balances).build()), openPositions, new Date(account.updateTime));
    }

    public static OpenPosition.Type adaptPositionType(PositionSide positionSide) {
        return positionSide == PositionSide.LONG ? OpenPosition.Type.LONG : OpenPosition.Type.SHORT;
    }

    public static Order adaptOrder(BinanceFuturesOrder order) {
        Order.OrderType type = BinanceAdapters.convert(order.side);
        FuturesContract futuresContract = new FuturesContract(BinanceAdapters.adaptSymbol(order.symbol), null);
        Order.Builder builder;
        if (order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.MARKET)) {
            builder = new MarketOrder.Builder(type, futuresContract);
        } else if (order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.LIMIT)
                || order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.LIMIT_MAKER)) {
            builder = new LimitOrder.Builder(type, futuresContract).limitPrice(order.price);
        } else {
            builder = new StopOrder.Builder(type, futuresContract).stopPrice(order.stopPrice);
        }
        builder
                .orderStatus(BinanceAdapters.adaptOrderStatus(order.status))
                .originalAmount(order.origQty)
                .id(Long.toString(order.orderId))
                .timestamp(order.getTime())
                .cumulativeAmount(order.executedQty);
        builder.averagePrice(order.avgPrice);
        if (order.clientOrderId != null) {
            builder.userReference(order.clientOrderId);
            builder.flag(BinanceTradeService.BinanceOrderFlags.withClientId(order.clientOrderId)); // backward compatibility
        }
        return builder.build();
    }

  public static Ticker replaceInstrument(Ticker ticker, FuturesContract futuresContract) {
    return new Ticker.Builder()
        .instrument(futuresContract)
        .open(ticker.getOpen())
        .last(ticker.getLast())
        .bid(ticker.getBid())
        .ask(ticker.getAsk())
        .high(ticker.getHigh())
        .low(ticker.getLow())
        .vwap(ticker.getVwap())
        .volume(ticker.getVolume())
        .quoteVolume(ticker.getQuoteVolume())
        .timestamp(ticker.getTimestamp())
        .bidSize(ticker.getBidSize())
        .askSize(ticker.getAskSize())
        .percentageChange(ticker.getPercentageChange())
        .build();
  }

  public static OrderBook replaceInstrument(OrderBook orderBook, FuturesContract futuresContract) {
    return new OrderBook(
        orderBook.getTimeStamp(),
        orderBook.getAsks().stream()
            .map(order -> LimitOrder.Builder.from(order).instrument(futuresContract).build()),
        orderBook.getBids().stream()
            .map(order -> LimitOrder.Builder.from(order).instrument(futuresContract).build()),
        false);
  }

  public static Trades replaceInstrument(Trades trades, FuturesContract futuresContract) {
    return new Trades(
        trades.getTrades().stream()
            .map(t -> Trade.Builder.from(t).instrument(futuresContract).build())
            .collect(Collectors.toList()),
        trades.getTradeSortType());
  }

    public static MarketOrder replaceInstrument(MarketOrder market, CurrencyPair pair) {
        return MarketOrder.Builder.from(market).instrument(pair).build();
    }

    public static LimitOrder replaceInstrument(LimitOrder limit, CurrencyPair pair) {
        return LimitOrder.Builder.from(limit).instrument(pair).build();
    }

    public static StopOrder replaceInstrument(StopOrder stop, CurrencyPair pair) {
        return StopOrder.Builder.from(stop).instrument(pair).build();
    }
}
