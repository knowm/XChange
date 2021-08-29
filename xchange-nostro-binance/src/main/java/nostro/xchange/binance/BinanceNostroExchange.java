package nostro.xchange.binance;

import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import info.bitrich.xchangestream.binance.BinanceStreamingTradeService;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction.ExecutionType;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.Transaction;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Optional;

public class BinanceNostroExchange extends BinanceStreamingExchange {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceNostroExchange.class);

    private TransactionFactory txFactory;
    private BinanceNostroTradeService nostroTradeService;
    private Disposable tradeSubscription;
    private Disposable connectionStateSubscription;

    @Override
    public TradeService getTradeService() {
        return nostroTradeService;
    }

    @Override
    public StreamingTradeService getStreamingTradeService() {
        return nostroTradeService;
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification inner = super.getDefaultExchangeSpecification();

        ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
        spec.setSslUri(inner.getSslUri());
        spec.setHost(inner.getHost());
        spec.setPort(inner.getPort());
        spec.setExchangeName(inner.getExchangeName());
        spec.setExchangeDescription(inner.getExchangeDescription());
        AuthUtils.setApiAndSecretKey(spec, "binance");
        
        return inner;
    }

    @Override
    protected void initServices() {
        super.initServices();
        
        if (isAuthenticated()) {
            try {
                this.txFactory = TransactionFactory.get(exchangeSpecification.getExchangeName(), exchangeSpecification.getUserName());
                this.nostroTradeService = new BinanceNostroTradeService((BinanceTradeService) this.tradeService, this.txFactory);
            } catch (Exception e) {
                throw new ExchangeException("Unable to init", e);
            }
        }
    }

    private boolean isAuthenticated() {
        return exchangeSpecification != null
                && exchangeSpecification.getApiKey() != null
                && exchangeSpecification.getSecretKey() != null;
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        return super.connect(args).doOnComplete(() -> {
            if (isAuthenticated()) {
                tradeSubscription = ((BinanceStreamingTradeService) super.getStreamingTradeService())
                        .getRawExecutionReports()
                        .doOnNext(r -> txFactory.execute(tx -> saveReport(tx, r)))
                        .doOnSubscribe(r -> LOG.info("Connected to BinanceUserDataStreaming"))
                        .doOnError(th -> LOG.error("Error while handling BinanceUserDataStreaming", th))
                        .retry()
                        .subscribe();

                nostroTradeService.synchronizeOpenOrders();

                connectionStateSubscription = connectionStateFlowable()
                        .doOnNext(this::connectionStateChanged)
                        .doOnSubscribe(r -> LOG.info("Connected to BinanceConnectionState"))
                        .doOnError(th -> LOG.error("Error while handling BinanceConnectionState", th))
                        .retry()
                        .subscribe();
            }
        });
    }

    @Override
    public Completable disconnect() {
        return super.disconnect().doOnComplete(() -> {
            
            if (connectionStateSubscription != null) {
                connectionStateSubscription.dispose();
                connectionStateSubscription = null;
                LOG.info("Disconnected from BinanceConnectionState");
            }

            if (tradeSubscription != null) {
                tradeSubscription.dispose();
                tradeSubscription = null;
                LOG.info("Disconnected from BinanceUserDataStreaming");
            }
        });
    }

    private void saveReport(Transaction tx, ExecutionReportBinanceUserTransaction report) throws Exception {
        LOG.info("Received execution report, client_id={}", report.getClientOrderId());
        
        Order order = report.toOrder();
        String orderId = order.getUserReference();
        String document = NostroUtils.writeOrderDocument(order);
        boolean terminal = isTerminal(report.getCurrentOrderStatus());
        
        Optional<OrderEntity> orderEntity = tx.getOrderRepository().lockById(orderId);
        if (orderEntity.isPresent()) {
            if (ExecutionType.REJECTED != report.getExecutionType()) {
                tx.getOrderRepository().updateById(orderId, document, terminal);
            }
        } else {
            LOG.warn("Received transaction of non-existing order: id={}, externalId={}", orderId, report.getOrderId());
            tx.getOrderRepository().insert(new OrderEntity.Builder()
                    .id(orderId)
                    .externalId(Long.toString(report.getOrderId()))
                    .terminal(terminal)
                    .document(document)
                    .build());
        }
        nostroTradeService.orderPublisher.offer(order);
        
        if (ExecutionType.TRADE == report.getExecutionType()) {
            UserTrade trade = report.toUserTrade();
            tx.getTradeRepository().insert(
                    orderId,
                    Long.toString(report.getTradeId()),
                    new Timestamp(report.getTimestamp()),
                    NostroUtils.writeTradeDocument(trade));
            
            nostroTradeService.tradePublisher.offer(trade);
        }
    }

    private static boolean isTerminal(OrderStatus orderStatus) {
        return orderStatus == OrderStatus.CANCELED ||
                orderStatus == OrderStatus.FILLED ||
                orderStatus == OrderStatus.EXPIRED;
    }
    
    private void connectionStateChanged(ConnectionStateModel.State state) {
        LOG.info("Connection state change: {}", state);
        
        // TODO: update order status and publish to nostroTradeService.orderPublisher
    }
}
