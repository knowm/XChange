package org.knowm.xchange.blockchain;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.blockchain.dto.account.BlockchainDeposit;
import org.knowm.xchange.blockchain.dto.account.BlockchainDeposits;
import org.knowm.xchange.blockchain.dto.account.BlockchainSymbol;
import org.knowm.xchange.blockchain.dto.account.BlockchainWithdrawal;
import org.knowm.xchange.blockchain.dto.trade.BlockchainOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.RateLimit;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
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

    public static AddressWithTag toAddressWithTag(BlockchainDeposit blockchainDeposit){
        return new AddressWithTag(blockchainDeposit.getAddress(), null);
    }

    public static FundingRecord.Status toWithdrawStatus(String status) {
        switch (status.toUpperCase()) {
            case REJECTED:
            case REFUNDING:
                return FundingRecord.Status.CANCELLED;
            case PENDING:
                return FundingRecord.Status.PROCESSING;
            case FAILED:
                return FundingRecord.Status.FAILED;
            case COMPLETED:
                return FundingRecord.Status.COMPLETE;
            default:
                throw new RuntimeException(STATUS_INVALID + status);
        }
    }
    public static FundingRecord.Status toDepositStatus(String status) {
        switch (status.toUpperCase()) {
            case REJECTED:
            case UNCONFIRMED:
                return FundingRecord.Status.CANCELLED;
            case COMPLETED:
                return FundingRecord.Status.COMPLETE;
            default:
                throw new RuntimeException(STATUS_INVALID + status);
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

    public  static  FundingRecord toFundingDeposit(BlockchainDeposits d){
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

   public static CurrencyPair toCurrencyPairBySymbol(BlockchainSymbol blockchainSymbol) {
        Currency baseSymbol = blockchainSymbol.getBaseCurrency();
        Currency counterSymbol = blockchainSymbol.getCounterCurrency();
        return new CurrencyPair(baseSymbol, counterSymbol);
    }

    public static OpenOrders toOpenOrders(List<BlockchainOrder> blockchainOrders){
        List<LimitOrder> limitOrders = new ArrayList<>();
        List<Order> hiddenOrders = new ArrayList<>();

        for(BlockchainOrder blockchainOrder : blockchainOrders) {
            Order.Builder builder = blockchainOrder.getOrderBuilder();

            Order order = builder.orderStatus(toOrderStatus(blockchainOrder.getOrdStatus()))
                    .originalAmount(blockchainOrder.getCumQty().add(blockchainOrder.getLeavesQty()))
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

    public static Order toOpenOrdersById(BlockchainOrder blockchainOrder){
        Order.Builder builder = blockchainOrder.getOrderBuilder();

        return builder.originalAmount(blockchainOrder.getCumQty().add(blockchainOrder.getLeavesQty()))
                      .id(Long.toString(blockchainOrder.getExOrdId()))
                      .timestamp(blockchainOrder.getTimestamp())
                      .averagePrice(blockchainOrder.getAvgPx())
                      .cumulativeAmount(blockchainOrder.getCumQty())
                      .orderStatus(toOrderStatus(blockchainOrder.getOrdStatus()))
                      .userReference(blockchainOrder.getClOrdId())
                      .build();
    }

    public static Order.OrderStatus toOrderStatus(String status) {
        switch (status.toUpperCase()) {
            case OPEN:
                return Order.OrderStatus.OPEN;
            case REJECTED:
                return Order.OrderStatus.REJECTED;
            case CANCELED:
                return Order.OrderStatus.CANCELED;
            case FILLED:
                return Order.OrderStatus.FILLED;
            case PART_FILLED:
                return Order.OrderStatus.PARTIALLY_FILLED;
            case EXPIRED:
                return Order.OrderStatus.EXPIRED;
            case PENDING:
                return Order.OrderStatus.PENDING_NEW;
            default:
                return Order.OrderStatus.UNKNOWN;
        }
    }

    public static BlockchainOrder toBlockchainLimitOrder(LimitOrder limitOrder){
        return BlockchainOrder.builder()
                .ordType(LIMIT)
                .symbol(toCurrencyPair(limitOrder.getInstrument()))
                .side(Order.OrderType.BID.equals(limitOrder.getType())? BUY.toUpperCase() : SELL.toUpperCase())
                .orderQty(limitOrder.getOriginalAmount())
                .price(limitOrder.getLimitPrice())
                .clOrdId(generateClOrdId())
                .build();
    }

    public static BlockchainOrder toBlockchainMarketOrder(MarketOrder marketOrder){
        return BlockchainOrder.builder()
                .ordType(MARKET)
                .symbol(toCurrencyPair(marketOrder.getInstrument()))
                .side(Order.OrderType.BID.equals(marketOrder.getType())? BUY.toUpperCase() : SELL.toUpperCase())
                .orderQty(marketOrder.getOriginalAmount())
                .price(marketOrder.getCumulativeAmount())
                .clOrdId(generateClOrdId())
                .build();
    }

    public static BlockchainOrder toBlockchainStopOrder(StopOrder stopOrder){
        return BlockchainOrder.builder()
                .ordType(STOP)
                .symbol(toCurrencyPair(stopOrder.getInstrument()))
                .side(Order.OrderType.BID.equals(stopOrder.getType())? BUY.toUpperCase() : SELL.toUpperCase())
                .orderQty(stopOrder.getOriginalAmount())
                .price(stopOrder.getLimitPrice())
                .stopPx(stopOrder.getStopPrice())
                .clOrdId(generateClOrdId())
                .build();
    }

    private static String generateClOrdId() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.substring(0, 16).replace("-", "");
        return uuid;
    }

    public static UserTrades toUserTrades(List<BlockchainOrder> blockchainTrades) {
        List<UserTrade> trades = blockchainTrades.stream()
                .map(blockchainTrade -> new UserTrade.Builder()
                                .type(blockchainTrade.getOrderType())
                                .originalAmount(blockchainTrade.getCumQty())
                                .currencyPair(blockchainTrade.getSymbol())
                                .price(blockchainTrade.getPrice())
                                .timestamp(blockchainTrade.getTimestamp())
                                .id(Long.toString(blockchainTrade.getExOrdId()))
                                .orderId(blockchainTrade.getClOrdId())
                                .build()
                ).collect(Collectors.toList());
        Long lastId = blockchainTrades.stream().map(BlockchainOrder::getExOrdId).max(Long::compareTo).orElse(0L);
        return new UserTrades(trades, lastId, Trades.TradeSortType.SortByTimestamp);
    }

   public static ExchangeMetaData adaptMetaData(Map<String, BlockchainSymbol> markets) {
       Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
       Map<Currency, CurrencyMetaData> currency = new HashMap<>();

       for (Map.Entry<String, BlockchainSymbol> entry : markets.entrySet()) {
           CurrencyPair pair = BlockchainAdapters.toCurrencyPairBySymbol(entry.getValue());
           BigDecimal min_scale = BigDecimal.valueOf(Math.pow(10, (entry.getValue().getMinOrderSizeScale())*-1));
           BigDecimal minAmount = entry.getValue().getMinOrderSize().multiply(min_scale);
           CurrencyPairMetaData currencyPairMetaData =
                   new CurrencyPairMetaData.Builder()
                           .baseScale(entry.getValue().getBaseCurrencyScale())
                           .minimumAmount(minAmount)
                           .maximumAmount(entry.getValue().getMaxOrderSize())
                           .build();
           currencyPairs.put(pair, currencyPairMetaData);
           currency.put(entry.getValue().getBaseCurrency(), null);
       }

       RateLimit[] rateLimits = {new RateLimit(30, 1, TimeUnit.SECONDS)};

       return new ExchangeMetaData(currencyPairs, currency, rateLimits, rateLimits, false);
    }
}
