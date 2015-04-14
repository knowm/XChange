package com.xeiam.xchange.independentreserve;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.independentreserve.dto.account.IndependentReserveAccount;
import com.xeiam.xchange.independentreserve.dto.account.IndependentReserveBalance;
import com.xeiam.xchange.independentreserve.dto.marketdata.IndependentReserveOrderBook;
import com.xeiam.xchange.independentreserve.dto.marketdata.OrderBookOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Kamil Zbikowski
 * Date: 4/10/15
 */
public class IndependentReserveAdapters {

    private IndependentReserveAdapters(){}


    public static OrderBook adaptOrderBook(IndependentReserveOrderBook independentReserveOrderBook) {
        List<LimitOrder> bids = adaptOrders(independentReserveOrderBook.getBuyOrders(),
                Order.OrderType.BID,
                new CurrencyPair(independentReserveOrderBook.getPrimaryCurrencyCode(), independentReserveOrderBook.getSecondaryCurrencyCode()));
        List<LimitOrder> asks = adaptOrders(independentReserveOrderBook.getBuyOrders(),
                Order.OrderType.ASK,
                new CurrencyPair(independentReserveOrderBook.getPrimaryCurrencyCode(), independentReserveOrderBook.getSecondaryCurrencyCode()));
        Date timestamp = new Date(independentReserveOrderBook.getCreatedTimestampUtc());

        return new OrderBook(timestamp, asks, bids);
    }

    private static List<LimitOrder> adaptOrders(List<OrderBookOrder> buyOrders, Order.OrderType type, CurrencyPair currencyPair) {
        final List<LimitOrder> orders = new ArrayList<LimitOrder>();
        for(OrderBookOrder obo : buyOrders){
            LimitOrder limitOrder = new LimitOrder(type, obo.getVolume(), currencyPair, null, null, obo.getPrice());
            orders.add(limitOrder);
        }
        return orders;
    }

    public static AccountInfo adaptAccountInfo(IndependentReserveBalance independentReserveBalance, String userName) {
        List<Wallet> wallets = new ArrayList<Wallet>();

        for (IndependentReserveAccount balanceAccount : independentReserveBalance.getIndependentReserveAccounts()) {
            wallets.add(new Wallet(balanceAccount.getCurrencyCode(), balanceAccount.getTotalBalance()));
        }
        return new AccountInfo(userName, wallets);
    }
}
