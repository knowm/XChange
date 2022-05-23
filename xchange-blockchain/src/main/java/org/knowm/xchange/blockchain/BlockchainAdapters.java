package org.knowm.xchange.blockchain;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.blockchain.dto.account.*;
import org.knowm.xchange.blockchain.dto.marketdata.*;
import org.knowm.xchange.blockchain.dto.trade.BlockchainOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.knowm.xchange.blockchain.BlockchainConstants.*;

@UtilityClass
public class BlockchainAdapters {

    public static String toSymbol(CurrencyPair currencyPair) {
        return String.format(CURRENCY_PAIR_SYMBOL_FORMAT, currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
    }

    public static CurrencyPair toCurrencyPair(Instrument instrument){
        if(instrument instanceof CurrencyPair) {
            return (CurrencyPair) instrument;
        }
        throw new IllegalArgumentException(String.format("Unsupported instrument '%s'", instrument));
    }

    public static Ticker toTicker(BlockchainTicker blockchainTicker) {
        return new Ticker.Builder()
                .instrument(blockchainTicker.getSymbol())
                .last(blockchainTicker.getLastTradePrice())
                .volume(blockchainTicker.getVolume())
                .build();
    }

    public static OrderBook toOrderBook(BlockchainOrderBook blockchainOrderBook) {
        List<LimitOrder> asks = blockchainOrderBook.getAsks().stream()
                .map(limitOrder -> toLimitOrder(limitOrder, Order.OrderType.ASK, blockchainOrderBook.getSymbol()))
                .collect(Collectors.toList());
        List<LimitOrder> bids = blockchainOrderBook.getAsks().stream()
                .map(limitOrder -> toLimitOrder(limitOrder, Order.OrderType.BID, blockchainOrderBook.getSymbol()))
                .collect(Collectors.toList());

        return new OrderBook(null, asks, bids);
    }

    public static LimitOrder toLimitOrder(BlockchainMarketDataOrder blockchainMarketDataOrder, Order.OrderType orderType , CurrencyPair currencyPair) {
        return new LimitOrder.Builder(orderType, currencyPair)
                .instrument(currencyPair)
                .limitPrice(blockchainMarketDataOrder.getPrice())
                .originalAmount(blockchainMarketDataOrder.getQuantity())
                .build();
    }

    public static Trades toTrades(List<BlockchainTrade> blockchainTrades, CurrencyPair currencyPair) {
        List<Trade> trades = blockchainTrades.stream()
                .map(blockchainTrade -> {
                    Order.OrderType orderType = blockchainTrade.isBuyer() ? Order.OrderType.BID : Order.OrderType.ASK;
                    return new Trade.Builder()
                            .instrument(currencyPair)
                            .type(orderType)
                            .originalAmount(blockchainTrade.getQuantity())
                            .price(blockchainTrade.getPrice())
                            .timestamp(blockchainTrade.getTimestamp())
                            .id(blockchainTrade.getId())
                            .build();
                }).collect(Collectors.toList());
        return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
    }

    public static Integer currencyPairToId(CurrencyPair currencyPair) {
        if (CurrencyPair.ETH_BTC.equals(currencyPair)) {
            return 3;
        }
        throw new IllegalArgumentException(String.format("Unsupported currency pair '%s'", currencyPair));
    }


    public static AddressWithTag toAddressWithTag(BlockchainAccount blockchainAccount){
        return new AddressWithTag(blockchainAccount.getAddress(), null);
    }

    public static Order.OrderStatus toOrderStatus(String status) {
        switch (status.toUpperCase()) {
            case "NEW":
                return Order.OrderStatus.NEW;
            case "FILLED":
                return Order.OrderStatus.FILLED;
            case "EXPIRED":
                return Order.OrderStatus.EXPIRED;
            case "CANCELED":
                return Order.OrderStatus.CANCELED;
            case "REJECTED":
                return Order.OrderStatus.REJECTED;
            case "PENDING_CANCEL":
                return Order.OrderStatus.PENDING_CANCEL;
            case "PARTIALLY_FILLED":
                return Order.OrderStatus.PARTIALLY_FILLED;
            default:
                return Order.OrderStatus.UNKNOWN;
        }
    }

    public static OpenOrders toOpenOrders(List<BlockchainOrder> blockchainOrders){
        List<LimitOrder> limitOrders = new ArrayList<>(Collections.emptyList());
        List<Order> hiddenOrders = new ArrayList<>(Collections.emptyList());

        for(BlockchainOrder blockchainOrder : blockchainOrders) {
            final Order.OrderType orderType = blockchainOrder.isBuyer() ? Order.OrderType.BID : Order.OrderType.ASK;
            final CurrencyPair symbol = blockchainOrder.getSymbol();
            Order.Builder builder;

            if (blockchainOrder.isMarketOrder()) {
                builder = new MarketOrder.Builder(orderType, symbol);
            } else if (blockchainOrder.isLimitOrder()){
                builder = new LimitOrder.Builder(orderType, symbol).limitPrice(blockchainOrder.getPrice());
            } else {
                builder = new StopOrder.Builder(orderType, symbol).stopPrice(blockchainOrder.getPrice());
            }
            Order order = builder.orderStatus(toOrderStatus(blockchainOrder.getOrdStatus()))
                    .originalAmount(blockchainOrder.getCumQty())
                    .id(Long.toString(blockchainOrder.getExOrdId()))
                    .timestamp(blockchainOrder.getTimestamp())
                    .averagePrice(blockchainOrder.getAvgPx())
                    .build();

            if (order instanceof LimitOrder) {
                limitOrders.add((LimitOrder) order);
            } else {
                hiddenOrders.add(order);
            }
        }

        return new OpenOrders(limitOrders, hiddenOrders);
    }

    public static UserTrades toUserTrades(List<BlockchainOrder> blockchainTrades) {
        List<UserTrade> trades = blockchainTrades.stream()
                .map(blockchainTrade -> new UserTrade.Builder()
                        .type(blockchainTrade.isBuyer()? Order.OrderType.BID : Order.OrderType.ASK)
                        .originalAmount(blockchainTrade.getCumQty())
                        .currencyPair(blockchainTrade.getSymbol())
                        .price(blockchainTrade.getPrice())
                        .timestamp(blockchainTrade.getTimestamp())
                        .id(Long.toString(blockchainTrade.getExOrdId()))
                        .orderId(blockchainTrade.getClOrdId())
//                       .feeAmount(t.commission)
//                        .feeCurrency(Currency.getInstance(t.commissionAsset))
                        .build()
                ).collect(Collectors.toList());
        Long lastId = blockchainTrades.stream().map(BlockchainOrder::getExOrdId).max(Long::compareTo).orElse(0L);
        return new UserTrades(trades, lastId, Trades.TradeSortType.SortByTimestamp);
    }

    public static BlockchainOrder toBlockchainOrder(LimitOrder limitOrder){
        return BlockchainOrder.builder()
                .ordType(LIMIT)
                .symbol(toCurrencyPair(limitOrder.getInstrument()))
                .side(Order.OrderType.BID.equals(limitOrder.getType())? BUY.toUpperCase() : SELL.toUpperCase())
                .orderQty(limitOrder.getOriginalAmount())
                .price(limitOrder.getLimitPrice())
                .clOrdId(generateClOrdId())
                .build();
    }

    private static String generateClOrdId() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.substring(0, 16).replace("-", "");
        return uuid;
    }

    public static FundingRecord.Status toWithdrawStatus(String status) {
        switch (status.toUpperCase()) {
            case "REJECTED":
            case "REFUNDING":
                return FundingRecord.Status.CANCELLED;
            case "PENDING":
                return FundingRecord.Status.PROCESSING;
            case "FAILED":
                return FundingRecord.Status.FAILED;
            case "COMPLETED":
                return FundingRecord.Status.COMPLETE;
            default:
                throw new RuntimeException("Unknown withdraw status: " + status);
        }
    }
    public static FundingRecord.Status toDepositStatus(String status) {
        switch (status.toUpperCase()) {
            case "REJECTED":
            case "UNCONFIRMED":
                return FundingRecord.Status.CANCELLED;
            case "COMPLETED":
                return FundingRecord.Status.COMPLETE;
            default:
                throw new RuntimeException("Unknown withdraw status: " + status);
        }
    }

    public static FundingRecord toFundingWithdrawal(BlockchainWithdrawal w){
        return new FundingRecord(
                w.getBeneficiary(),
                null,
                w.getTimestamp(),
                w.getCurrency(),
                w.getAmount(),
                w.getWithdrawalId(),
                null,
                FundingRecord.Type.WITHDRAWAL,
                BlockchainAdapters.toWithdrawStatus(w.getState()),
                null,
                w.getFee(),
                null);
    }

    public  static  FundingRecord toFundingDeposit(BlockchainDeposit d){
        return new FundingRecord(
                d.getAddress(),
                null,
                d.getTimestamp(),
                d.getCurrency(),
                d.getAmount(),
                d.getDepositId(),
                d.getTxHash(),
                FundingRecord.Type.DEPOSIT,
                BlockchainAdapters.toDepositStatus(d.getState()),
                null,
                null,
                null);
    }

    public static BlockchainWithdrawalRequest toWithdrawalRequest(Currency currency, BigDecimal amount, String address){
        return BlockchainWithdrawalRequest.builder()
                .currency(currency)
                .amount(amount)
                .address(address)
                .build();
    }

   public static CurrencyPair toCurrencyPairBySymbol(BlockchainSymbols blockchainSymbol) {
        Currency baseSymbol = blockchainSymbol.getBase_currency();
        Currency counterSymbol = blockchainSymbol.getCounter_currency();
        return new CurrencyPair(baseSymbol, counterSymbol);
    }

}
