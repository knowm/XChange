package org.knowm.xchange.binance.futures;

import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAccountInformation;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.PositionSide;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;

import java.math.BigDecimal;
import java.math.MathContext;
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
                                BinanceAdapters.adaptSymbol(p.symbol),
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
        CurrencyPair currencyPair = BinanceAdapters.adaptSymbol(order.symbol);
        Order.Builder builder;
        if (order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.MARKET)) {
            builder = new MarketOrder.Builder(type, currencyPair);
        } else if (order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.LIMIT)
                || order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.LIMIT_MAKER)) {
            builder = new LimitOrder.Builder(type, currencyPair).limitPrice(order.price);
        } else {
            builder = new StopOrder.Builder(type, currencyPair).stopPrice(order.stopPrice);
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
}
