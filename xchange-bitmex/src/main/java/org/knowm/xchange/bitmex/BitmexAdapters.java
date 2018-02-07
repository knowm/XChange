package org.knowm.xchange.bitmex;

import com.google.common.collect.BiMap;
import org.knowm.xchange.bitmex.dto.BitmexInstrument;
import org.knowm.xchange.bitmex.dto.BitmexOrder;
import org.knowm.xchange.bitmex.dto.BitmexOrderBookL2;

import org.knowm.xchange.bitmex.dto.BitmexTrade;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexDepth;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexTicker;
import org.knowm.xchange.bitmex.dto.trade.BitmexOrderResponse;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;
import org.knowm.xchange.bitmex.dto.trade.BitmexUserTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class BitmexAdapters {

    public static OrderBook adaptOrderBook(BitmexDepth bitmexDepth, CurrencyPair currencyPair) {

        OrdersContainer asksOrdersContainer = adaptOrders(bitmexDepth.getAsks(), currencyPair, OrderType.ASK);
        OrdersContainer bidsOrdersContainer = adaptOrders(bitmexDepth.getBids(), currencyPair, OrderType.BID);

        return new OrderBook(new Date(Math.max(asksOrdersContainer.getTimestamp(), bidsOrdersContainer.getTimestamp())), asksOrdersContainer.getLimitOrders(), bidsOrdersContainer.getLimitOrders());
    }

    public static BitmexDepth adaptDepth(List<BitmexOrderBookL2> orders, CurrencyPair currencyPair) {

        BitmexDepth bitmexDepth = new BitmexDepth(new ArrayList<BitmexOrderBookL2>(), new ArrayList<BitmexOrderBookL2>());

        for (BitmexOrderBookL2 bitmexOrder : orders) {
            if (bitmexOrder.getSide().equalsIgnoreCase("BUY"))
                bitmexDepth.getBids().add(bitmexOrder);
            else if (bitmexOrder.getSide().equals(BitmexSide.SELL))
                bitmexDepth.getAsks().add(bitmexOrder);
        }

        return bitmexDepth;
    }

    public static OrdersContainer adaptOrders(List<BitmexOrderBookL2> orders, CurrencyPair currencyPair, OrderType orderType) {

        // bitmex does not provide timestamps on order book
        long maxTimestamp = System.currentTimeMillis();
        List<LimitOrder> limitOrders = new ArrayList<>(orders.size());

        for (BitmexOrderBookL2 order : orders) {

            limitOrders.add(adaptOrder(order, orderType, currencyPair));
        }
        return new OrdersContainer(maxTimestamp, limitOrders);

    }

    public static Trades adaptTrades(List<org.knowm.xchange.bitmex.dto.BitmexTrade> trades, CurrencyPair currencyPair) {

        List<Trade> tradeList = new ArrayList<>(trades.size());
        for (int i = 0; i < trades.size(); i++) {
            org.knowm.xchange.bitmex.dto.BitmexTrade trade = trades.get(i);
            tradeList.add(adaptTrade(trade, currencyPair));
        }
        long lastTid = trades.size() > 0 ? (trades.get(0).getTimestamp().toEpochSecond()) : 0;
        // long lastTid = 0L;
        return new Trades(tradeList, lastTid, Trades.TradeSortType.SortByTimestamp);
    }

    public static LimitOrder adaptOrder(BitmexOrderBookL2 order, OrderType orderType, CurrencyPair currencyPair) {

        return new LimitOrder(orderType, order.getSize(), currencyPair, "", null, BigDecimal.valueOf(order.getPrice()));
    }

    public static Ticker adaptTicker(BitmexInstrument bitmexTicker, CurrencyPair currencyPair) {

        Ticker.Builder builder = new Ticker.Builder();
        builder.open(BigDecimal.valueOf(bitmexTicker.getPrevClosePrice()));
        builder.ask(BigDecimal.valueOf(bitmexTicker.getAskPrice()));
        builder.bid(BigDecimal.valueOf(bitmexTicker.getBidPrice()));
        builder.last(BigDecimal.valueOf(bitmexTicker.getLastPrice()));
        builder.high(BigDecimal.valueOf(bitmexTicker.getHighPrice()));
        builder.low(BigDecimal.valueOf(bitmexTicker.getLowPrice()));
        builder.vwap(new BigDecimal(bitmexTicker.getVwap().longValue()));
        builder.volume(bitmexTicker.getVolume24h());
        builder.currencyPair(currencyPair);
        return builder.build();
    }

    public static Trade adaptTrade(BitmexTrade bitmexPublicTrade, CurrencyPair currencyPair) {

        OrderType type = adaptOrderType(bitmexPublicTrade.getSide());
        BigDecimal originalAmount = bitmexPublicTrade.getSize();
        OffsetDateTime timestamp = bitmexPublicTrade.getTimestamp();
        // Date timestamp = adaptTimestamp(bitmexPublicTrade.getTime());
        // new Date((long) (bitmexPublicTrade.getTime()));

        return new Trade(type, originalAmount, currencyPair, bitmexPublicTrade.getPrice(), Date.from(timestamp.toInstant()), "" + timestamp);
    }

    public static Wallet adaptWallet(Map<String, BigDecimal> bitmexWallet) {

        List<Balance> balances = new ArrayList<>(bitmexWallet.size());
        for (Entry<String, BigDecimal> balancePair : bitmexWallet.entrySet()) {
            Currency currency = adaptCurrency(balancePair.getKey());
            Balance balance = new Balance(currency, balancePair.getValue());
            balances.add(balance);
        }
        return new Wallet(balances);
    }

    public static Set<CurrencyPair> adaptCurrencyPairs(Collection<String> bitmexCurrencyPairs) {

        Set<CurrencyPair> currencyPairs = new HashSet<>();
        for (String bitmexCurrencyPair : bitmexCurrencyPairs) {
            CurrencyPair currencyPair = adaptCurrencyPair(bitmexCurrencyPair);
            if (currencyPair != null) {
                currencyPairs.add(currencyPair);
            }
        }
        return currencyPairs;
    }

    public static Currency adaptCurrency(String bitmexCurrencyCode) {

        return BitmexUtils.translateBitmexCurrencyCode(bitmexCurrencyCode);
    }

    public static CurrencyPair adaptCurrencyPair(String bitmexCurrencyPair) {

        return BitmexUtils.translateBitmexCurrencyPair(bitmexCurrencyPair);
    }

    public static OpenOrders adaptOpenOrders(Map<String, BitmexOrder> bitmexOrders) {

        List<LimitOrder> limitOrders = new ArrayList<>();
        for (Entry<String, org.knowm.xchange.bitmex.dto.BitmexOrder> bitmexOrderEntry : bitmexOrders.entrySet()) {
            org.knowm.xchange.bitmex.dto.BitmexOrder bitmexOrder = bitmexOrderEntry.getValue();

//            jn:I must be late to the party, but there is no desc in the bitmex schema or docs or explorer.
//            BitmexOrderDescription orderDescription = bitmexOrder.getOrderDescription();

            if (!"limit".equalsIgnoreCase(bitmexOrder.getOrdType().toString())) {
                // how to handle stop-loss, take-profit, stop-loss-limit, and so on orders?
                // ignore anything but a plain limit order for now
                continue;
            }

            limitOrders.add(adaptLimitOrder(bitmexOrder, bitmexOrderEntry.getKey()));
        }
        return new OpenOrders(limitOrders);

    }

    public static LimitOrder adaptLimitOrder(org.knowm.xchange.bitmex.dto.BitmexOrder bitmexOrder, String id) {

//        BitmexOrderDescription orderDescription = bitmexOrder.getOrderDescription();
        String type1 = bitmexOrder.getOrdType().toString();
        OrderType type = adaptOrderType(type1);

        BigDecimal originalAmount = bitmexOrder.getOrderQty();
        BigDecimal filledAmount = bitmexOrder.getOrderQty();
        BigDecimal remainingAmount = originalAmount.min(filledAmount);
        CurrencyPair pair = adaptCurrencyPair(bitmexOrder.getCurrency());
        Date timestamp = Date.from(bitmexOrder.getTimestamp().toInstant());//todo: test, test, test

        OrderStatus status = adaptOrderStatus(bitmexOrder.getOrdStatus());

        if (status == OrderStatus.NEW && filledAmount.compareTo(BigDecimal.ZERO) > 0 && filledAmount.compareTo(originalAmount) < 0) {
            status = OrderStatus.PARTIALLY_FILLED;
        }

        //todo: figure out fees from bitmex api
        return new LimitOrder.Builder(type, pair).originalAmount(originalAmount).id(id).timestamp(timestamp).limitPrice(BigDecimal.valueOf(bitmexOrder.getPrice())).averagePrice(BigDecimal.valueOf(bitmexOrder.getAvgPx())).cumulativeAmount(filledAmount).orderStatus(status).build();
    }

    public static UserTrades adaptTradesHistory(Map<String, org.knowm.xchange.bitmex.dto.BitmexTrade> bitmexTrades) {

        return new UserTrades(bitmexTrades.entrySet().stream().map(bitmexTradeEntry -> (adaptTrade(bitmexTradeEntry.getValue(), bitmexTradeEntry.getKey()))).collect(Collectors.toList()), Trades.TradeSortType.SortByID);
    }

    public static BitmexUserTrade adaptTrade(org.knowm.xchange.bitmex.dto.BitmexTrade bitmexTrade, String tradeId) {

        OrderType orderType = adaptOrderType(bitmexTrade.getSide());
        BigDecimal originalAmount = bitmexTrade.getSize();
        String bitmexAssetPair = bitmexTrade.getSymbol();
        CurrencyPair pair = adaptCurrencyPair(bitmexAssetPair);
        BigDecimal price = bitmexTrade.getPrice();

        return new BitmexUserTrade(orderType, originalAmount, pair, price, null, tradeId, bitmexTrade.getTrdMatchID(), BigDecimal.ONE, pair.counter, BigDecimal.ONE);
    }

    public static OrderType adaptOrderType(String bitmexType) {

        return bitmexType.equals(BitmexSide.BUY) ? OrderType.BID : OrderType.ASK;
    }

    public static String adaptOrderId(BitmexOrderResponse orderResponse) {

        List<String> orderIds = orderResponse.getTransactionIds();
        return (orderIds == null || orderIds.isEmpty()) ? "" : orderIds.get(0);
    }

    public static ExchangeMetaData adaptToExchangeMetaData(ExchangeMetaData originalMetaData, List<BitmexInstrument> tickers, BiMap<BitmexPrompt, String> contracts) {

        // So we will create 3 maps.
        // A pairs map ( "ETC/BTC" -> price_scale:, min_amount:)
        // A currencies map : "BTC"->"scale": 5,"withdrawal_fee": 0.001
        // A bitmexContracts Map XMRZ17->XMR.BTC.MONTHLY
        Map<CurrencyPair, CurrencyPairMetaData> pairs = new HashMap<>();
        Map<Currency, CurrencyMetaData> currencies = new HashMap<>();
        BitmexUtils.setBitmexAssetPairs(tickers);

        pairs.putAll(originalMetaData.getCurrencyPairs());
        currencies.putAll(originalMetaData.getCurrencies());

        for (BitmexInstrument ticker : tickers) {
            String quote = ticker.getQuoteCurrency();
            String base = ticker.getRootSymbol();
            Currency baseCurrencyCode = BitmexAdapters.adaptCurrency(base);
            Currency quoteCurrencyCode = BitmexAdapters.adaptCurrency(quote);

            CurrencyPair pair = new CurrencyPair(baseCurrencyCode, quoteCurrencyCode);
            pairs.put(pair, adaptPair(ticker, pairs.get(adaptCurrencyPair(pair.toString()))));
            if (!BitmexUtils.bitmexCurrencies.containsKey(baseCurrencyCode) && !BitmexUtils.bitmexCurrencies.containsValue(base))
                BitmexUtils.bitmexCurrencies.put(baseCurrencyCode, base);
            if (!BitmexUtils.bitmexCurrencies.containsKey(quoteCurrencyCode) && !BitmexUtils.bitmexCurrencies.containsValue(quote))
                BitmexUtils.bitmexCurrencies.put(quoteCurrencyCode, quote);

            int scale = Math.max(0, new BigDecimal(ticker.getTickSize()).stripTrailingZeros().scale());
            BigDecimal baseWithdrawalFee = originalMetaData.getCurrencies().get(baseCurrencyCode) == null ? null : originalMetaData.getCurrencies().get(baseCurrencyCode).getWithdrawalFee();
            BigDecimal quoteWithdrawalFee = originalMetaData.getCurrencies().get(quoteCurrencyCode) == null ? null : originalMetaData.getCurrencies().get(quoteCurrencyCode).getWithdrawalFee();

            currencies.put(baseCurrencyCode, new CurrencyMetaData(scale, baseWithdrawalFee));
            currencies.put(quoteCurrencyCode, new CurrencyMetaData(scale, quoteWithdrawalFee));
            BitmexPrompt prompt = contracts.inverse().get(ticker.getSymbol().replaceFirst(ticker.getRootSymbol(), "")) != null ? contracts.inverse().get(ticker.getSymbol().replaceFirst(ticker
                    .getRootSymbol(), "")) : BitmexPrompt.PERPETUAL;

            BitmexContract contract = new BitmexContract(pair, prompt);
            if (!BitmexUtils.bitmexContracts.containsKey(ticker.getSymbol()) && !BitmexUtils.bitmexContracts.containsValue(contract))
                BitmexUtils.bitmexContracts.put(ticker.getSymbol(), contract);

        }

        return new ExchangeMetaData(pairs, currencies, originalMetaData == null ? null : originalMetaData.getPublicRateLimits(), originalMetaData == null ? null : originalMetaData.getPrivateRateLimits(),
                originalMetaData == null ? null : originalMetaData.isShareRateLimits());
    }

    private static CurrencyPairMetaData adaptPair(BitmexInstrument ticker, CurrencyPairMetaData OriginalMeta) {

        BigDecimal tickSize = BigDecimal.valueOf(ticker.getTickSize());
        if (OriginalMeta != null) {
            return new CurrencyPairMetaData(BigDecimal.valueOf(ticker.getTakerFee()), OriginalMeta.getMinimumAmount(), OriginalMeta.getMaximumAmount(), Math.max(0, tickSize.stripTrailingZeros().scale()));
        } else {
            return new CurrencyPairMetaData(BigDecimal.valueOf(ticker.getTakerFee()), null, null, Math.max(0, tickSize.stripTrailingZeros().scale()));
        }
    }

    public static OrderStatus adaptOrderStatus(String status) {

        switch (status.toUpperCase()) {
            case "PENDING":
                return OrderStatus.PENDING_NEW;
            case "OPEN":
                return OrderStatus.NEW;
            case "CLOSED":
                return OrderStatus.FILLED;
            case "CANCELED":
                return OrderStatus.CANCELED;
            case "EXPIRED":
                return OrderStatus.EXPIRED;
            default:
                return null;
        }
    }

    public static CurrencyPair adaptCurrencyPair(CurrencyPair currencyPair) {

        return currencyPair;
    }

    public static class OrdersContainer {

        private final long timestamp;
        private final List<LimitOrder> limitOrders;

        /**
         * Constructor
         *
         * @param timestamp
         * @param limitOrders
         */
        public OrdersContainer(long timestamp, List<LimitOrder> limitOrders) {

            this.timestamp = timestamp;
            this.limitOrders = limitOrders;
        }

        public long getTimestamp() {

            return timestamp;
        }

        public List<LimitOrder> getLimitOrders() {

            return limitOrders;
        }
    }
}
