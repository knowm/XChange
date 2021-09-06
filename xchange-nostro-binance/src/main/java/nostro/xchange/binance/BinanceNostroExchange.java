package nostro.xchange.binance;

import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import info.bitrich.xchangestream.binance.BinanceStreamingTradeService;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction.ExecutionType;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import nostro.xchange.binance.sync.*;
import nostro.xchange.persistence.*;
import nostro.xchange.utils.AccountDocument;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class BinanceNostroExchange extends BinanceStreamingExchange {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceNostroExchange.class);
    
    private volatile TransactionFactory txFactory;
    private volatile BinanceNostroPublisher publisher;
    private volatile BinanceNostroTradeService nostroTradeService;
    private volatile BinanceSyncService syncService;
    
    private volatile AccountDocument account = null;
    
    private Disposable tradeSubscription;
    private Disposable connectionStateSubscription;

    @Override
    public TradeService getTradeService() {
        return nostroTradeService;
    }

    @Override
    public StreamingTradeService getStreamingTradeService() {
        return publisher;
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
                this.publisher = new BinanceNostroPublisher();
                this.txFactory = TransactionFactory.get(exchangeSpecification.getExchangeName(), exchangeSpecification.getUserName());
                this.nostroTradeService = new BinanceNostroTradeService((BinanceTradeService) this.tradeService, this.txFactory);
                this.syncService = new BinanceSyncService(txFactory, publisher, (BinanceTradeService) this.tradeService);
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
                updateSubscription(args[0]);

                tradeSubscription = ((BinanceStreamingTradeService) super.getStreamingTradeService())
                        .getRawExecutionReports()
                        .doOnNext(r -> txFactory.execute(tx -> saveReport(tx, r)))
                        .doOnSubscribe(r -> LOG.info("Connected to BinanceUserDataStreaming"))
                        .doOnError(th -> LOG.error("Error while handling BinanceUserDataStreaming", th))
                        .retry()
                        .subscribe();

                syncService.init();

                connectionStateSubscription = userDataStreamingService.subscribeConnectionState()
                        .doOnNext(s -> syncService.connectionStateChanged(ConnectionStateModel.State.OPEN == s))
                        .doOnSubscribe(s -> LOG.info("Connected to BinanceConnectionState"))
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
        boolean terminal = BinanceNostroUtils.isTerminal(report.getCurrentOrderStatus());
        Timestamp created = new Timestamp(report.getOrderCreationTime());
        Timestamp updated = new Timestamp(report.getTimestamp());
        
        Optional<OrderEntity> orderEntity = tx.getOrderRepository().lockById(orderId);
        if (orderEntity.isPresent()) {
            if (ExecutionType.REJECTED != report.getExecutionType()) {
                tx.getOrderRepository().updateById(orderId, document, terminal, updated);
            }
        } else {
            LOG.warn("Received transaction of non-existing order: id={}, externalId={}", orderId, report.getOrderId());
            tx.getOrderRepository().insert(new OrderEntity.Builder()
                    .id(orderId)
                    .externalId(Long.toString(report.getOrderId()))
                    .instrument(order.getInstrument().toString())
                    .terminal(terminal)
                    .document(document)
                    .created(created)
                    .updated(updated)
                    .build());
        }
        publisher.publish(order);
        
        if (ExecutionType.TRADE == report.getExecutionType()) {
            UserTrade trade = report.toUserTrade();
            tx.getTradeRepository().insert(
                    orderId,
                    Long.toString(report.getTradeId()),
                    updated,
                    NostroUtils.writeTradeDocument(trade));
            
            publisher.publish(trade);
        }
        
        String symbol = order.getCurrencyPair().toString();
        if (!account.getSubscriptions().getOrders().contains(symbol)) {
            LOG.info("Adding new order subscription to account: symbol={}", symbol);
            updateSubscription(ProductSubscription.create().addOrders(order.getCurrencyPair()).build());
        }
    }

    private void updateSubscription(ProductSubscription subscriptions) {
        account = txFactory.executeAndGet(tx -> {
            AccountDocument a = NostroUtils.readAccountDocument(tx.getAccountRepository().lock());

            a.getSubscriptions().getBalances().addAll(
                    subscriptions.getBalances().stream()
                            .map(Currency::getCurrencyCode)
                            .collect(Collectors.toList())
            );

            a.getSubscriptions().getOrders().addAll(
                    subscriptions.getOrders().stream()
                            .map(CurrencyPair::toString)
                            .collect(Collectors.toList())
            );

            a.getSubscriptions().getOrders().addAll(
                    subscriptions.getUserTrades().stream()
                            .map(CurrencyPair::toString)
                            .collect(Collectors.toList())
            );

            tx.getAccountRepository().update(NostroUtils.writeAccountDocument(a));
            return a;
        });
    }
}
