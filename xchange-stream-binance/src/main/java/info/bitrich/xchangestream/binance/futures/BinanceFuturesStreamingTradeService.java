package info.bitrich.xchangestream.binance.futures;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.BinanceUserDataStreamingService;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction;
import info.bitrich.xchangestream.binance.futures.dto.OrderTradeUpdateBinanceUserTransaction;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import io.reactivex.rxjava3.processors.PublishProcessor;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.instrument.Instrument;

import java.io.IOException;

public class BinanceFuturesStreamingTradeService implements StreamingTradeService {

    protected final FlowableProcessor<OrderTradeUpdateBinanceUserTransaction> executionReportsPublisher =
            PublishProcessor.<OrderTradeUpdateBinanceUserTransaction>create().toSerialized();

    protected volatile Disposable executionReports;
    protected volatile BinanceUserDataStreamingService binanceUserDataStreamingService;

    protected final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    public BinanceFuturesStreamingTradeService(BinanceUserDataStreamingService binanceUserDataStreamingService) {
        this.binanceUserDataStreamingService = binanceUserDataStreamingService;
    }

    public Flowable<OrderTradeUpdateBinanceUserTransaction> getRawExecutionReports() {
        if (binanceUserDataStreamingService == null || !binanceUserDataStreamingService.isSocketOpen())
            throw new ExchangeSecurityException("Not authenticated");
        return executionReportsPublisher;
    }

    public Flowable<Order> getOrderChanges() {
        return getRawExecutionReports()
                .filter(r -> !r.getOrderTradeUpdate().getExecutionType().equals(ExecutionReportBinanceUserTransaction.ExecutionType.REJECTED))
                .map(OrderTradeUpdateBinanceUserTransaction::toOrder);
    }

    @Override
    public Flowable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
        throw new NotAvailableFromExchangeException("getOrderChanges");
    }

    @Override
    public Flowable<Order> getOrderChanges(Instrument instrument, Object... args) {
        if (instrument instanceof FuturesContract) {
            return getOrderChanges().filter(oc -> instrument.equals(oc.getInstrument()));
        }
        throw new NotAvailableFromExchangeException("getOrderChanges");
    }

    public Flowable<UserTrade> getUserTrades() {
        return getRawExecutionReports()
                .filter(r -> r.getOrderTradeUpdate().getExecutionType().equals(ExecutionReportBinanceUserTransaction.ExecutionType.TRADE))
                .map(OrderTradeUpdateBinanceUserTransaction::toUserTrade);
    }

    @Override
    public Flowable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
        throw new NotAvailableFromExchangeException("getUserTrades");
    }

    @Override
    public Flowable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
        if (instrument instanceof FuturesContract) {
            return getUserTrades().filter(t -> instrument.equals(t.getInstrument()));
        }
        throw new NotAvailableFromExchangeException("getUserTrades");
    }

    /**
     * User data subscriptions may have to persist across multiple socket connections to different
     * URLs and therefore must act in a publisher fashion so that subscribers get an uninterrupted
     * stream.
     */
    public void setUserDataStreamingService(
            BinanceUserDataStreamingService binanceUserDataStreamingService) {
        if (executionReports != null && !executionReports.isDisposed()) executionReports.dispose();
        this.binanceUserDataStreamingService = binanceUserDataStreamingService;
        openSubscriptions();
    }

    public void openSubscriptions() {
        if (binanceUserDataStreamingService != null) {
            executionReports =
                    binanceUserDataStreamingService
                            .subscribeChannel(
                                    BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.FUTURES_ORDER_UPDATE)
                            .map(this::executionReport)
                            .subscribe(executionReportsPublisher::onNext);
        }
    }

    protected OrderTradeUpdateBinanceUserTransaction executionReport(JsonNode json) {
        try {
            return mapper.treeToValue(json, OrderTradeUpdateBinanceUserTransaction.class);
        } catch (IOException e) {
            throw new ExchangeException("Unable to parse execution report", e);
        }
    }
}
