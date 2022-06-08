package org.knowm.xchange.blockchain;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.blockchain.dto.account.*;
import org.knowm.xchange.blockchain.dto.trade.BlockchainOrder;
import org.knowm.xchange.blockchain.params.BlockchainOrderParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.instrument.Instrument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.knowm.xchange.blockchain.BlockchainConstants.*;
import static org.knowm.xchange.blockchain.BlockchainConstants.SELL;

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

    public static BlockchainOrderParams toBlockchainOrder(String orderType, LimitOrder limitOrder, MarketOrder marketOrder, StopOrder stopOrder){

        switch (orderType){
            case LIMIT:
                return BlockchainOrderParams.builder()
                        .ordType(orderType)
                        .symbol(toCurrencyPair(limitOrder.getInstrument()))
                        .side(Order.OrderType.BID.equals(limitOrder.getType())? BUY.toUpperCase() : SELL.toUpperCase())
                        .orderQty(limitOrder.getOriginalAmount())
                        .price(limitOrder.getLimitPrice())
                        .clOrdId(generateClOrdId())
                        .build();
            case MARKET:
                return BlockchainOrderParams.builder()
                        .ordType(orderType)
                        .symbol(toCurrencyPair(marketOrder.getInstrument()))
                        .side(Order.OrderType.BID.equals(marketOrder.getType())? BUY.toUpperCase() : SELL.toUpperCase())
                        .orderQty(marketOrder.getOriginalAmount())
                        .price(marketOrder.getCumulativeAmount())
                        .clOrdId(generateClOrdId())
                        .build();
            case STOP_ORDER:
                return BlockchainOrderParams.builder()
                        .ordType(orderType)
                        .symbol(toCurrencyPair(stopOrder.getInstrument()))
                        .side(Order.OrderType.BID.equals(stopOrder.getType())? BUY.toUpperCase() : SELL.toUpperCase())
                        .orderQty(stopOrder.getOriginalAmount())
                        .price(stopOrder.getStopPrice())
                        .clOrdId(generateClOrdId())
                        .build();
            default:
                return null;

        }
    }

    private static String generateClOrdId() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.substring(0, 16).replace("-", "");
        return uuid;
    }
}
