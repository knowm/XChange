package nostro.xchange.binance;

import info.bitrich.xchangestream.binance.BinanceStreamingAccountService;
import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import info.bitrich.xchangestream.binance.BinanceStreamingTradeService;
import info.bitrich.xchangestream.binance.dto.BinanceWebsocketBalance;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction.ExecutionType;
import info.bitrich.xchangestream.binance.dto.OutboundAccountInfoBinanceWebsocketTransaction;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import nostro.xchange.binance.sync.BinanceSyncService;
import nostro.xchange.persistence.BalanceEntity;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.Transaction;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.AccountDocument;
import nostro.xchange.utils.NostroStreamingPublisher;
import nostro.xchange.utils.NostroUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NostroBinanceExchange extends BinanceStreamingExchange {
    private static final Logger LOG = LoggerFactory.getLogger(NostroBinanceExchange.class);
    
    private static final String P_SYNC_DELAY = "syncDelay";
    
    private volatile TransactionFactory txFactory;
    private volatile NostroStreamingPublisher publisher;
    private volatile NostroBinanceAccountService nostroAccountService;
    private volatile NostroBinanceTradeService nostroTradeService;
    private volatile BinanceSyncService syncService;
    
    private volatile AccountDocument account = null;
    
    private Disposable tradeSubscription;
    private Disposable accountSubscription;
    private Disposable connectionStateSubscription;

    @Override
    public AccountService getAccountService() {
        return nostroAccountService;
    }

    @Override
    public TradeService getTradeService() {
        return nostroTradeService;
    }

    @Override
    public StreamingAccountService getStreamingAccountService() {
        return publisher;
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
        spec.setShouldLoadRemoteMetaData(inner.isShouldLoadRemoteMetaData());
        AuthUtils.setApiAndSecretKey(spec, "binance");
        
        spec.setExchangeSpecificParametersItem(P_SYNC_DELAY, "300");
        
        return spec;
    }

    @Override
    protected void initServices() {
        super.initServices();
        
        if (isAuthenticated()) {
            try {
                this.publisher = new NostroStreamingPublisher();
                this.txFactory = TransactionFactory.get(exchangeSpecification.getExchangeName(), exchangeSpecification.getUserName());
                this.nostroAccountService = new NostroBinanceAccountService((BinanceAccountService) this.accountService, this.txFactory);
                this.nostroTradeService = new NostroBinanceTradeService((BinanceTradeService) this.tradeService, this.txFactory);
                this.syncService = new BinanceSyncService(txFactory, publisher, (BinanceAccountService) this.accountService, (BinanceTradeService) this.tradeService, getSyncDelay());
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

    private long getSyncDelay() {
        try {
            return Long.valueOf((String) exchangeSpecification.getExchangeSpecificParametersItem(P_SYNC_DELAY));
        } catch (Exception e) {
            return 300;
        }
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        return super.connect(args).doOnComplete(() -> {
            if (isAuthenticated()) {
                updateSubscription(args[0]);

                tradeSubscription = ((BinanceStreamingTradeService) super.getStreamingTradeService())
                        .getRawExecutionReports()
                        .doOnNext(this::onExecutionReport)
                        .doOnSubscribe(r -> LOG.info("Connected to BinanceExecutionReport"))
                        .doOnError(th -> LOG.error("Error while handling BinanceExecutionReport", th))
                        .retry()
                        .subscribe();

                accountSubscription = ((BinanceStreamingAccountService) super.getStreamingAccountService())
                        .getRawAccountInfo()
                        .doOnNext(this::onAccountInfo)
                        .doOnSubscribe(r -> LOG.info("Connected to BinanceAccountInfo"))
                        .doOnError(th -> LOG.error("Error while handling BinanceAccountInfo", th))
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
                LOG.info("Disconnected from BinanceExecutionReport");
            }

            if (accountSubscription != null) {
                accountSubscription.dispose();
                accountSubscription = null;
                LOG.info("Disconnected from BinanceAccountInfo");
            }
        });
    }
    
    private void onExecutionReport(ExecutionReportBinanceUserTransaction report) {
        try {
            LOG.info("Received execution report, client_id={}", report.getClientOrderId());
            Pair<Order, UserTrade> pair = txFactory.executeAndGet(tx -> saveExecutionReport(tx, report));
            publisher.publish(pair.getLeft());
            if (pair.getRight() != null) {
                publisher.publish(pair.getRight());
            }
        } catch (Throwable th) {
            LOG.error("Error saving execution report", th);
        }
    }

    private Pair<Order, UserTrade> saveExecutionReport(Transaction tx, ExecutionReportBinanceUserTransaction report) throws Exception {
        Order order = report.toOrder();
        String orderId = order.getUserReference();
        String document = NostroUtils.writeOrderDocument(order);
        boolean terminal = NostroBinanceUtils.isTerminal(report.getCurrentOrderStatus());
        Timestamp created = new Timestamp(report.getOrderCreationTime());
        Timestamp updated = new Timestamp(report.getTimestamp());
        
        Optional<OrderEntity> orderEntity = tx.getOrderRepository().lockById(orderId);
        if (orderEntity.isPresent()) {
            if (ExecutionType.REJECTED != report.getExecutionType()) {
                if (orderEntity.get().getCreated().before(created)) {
                    tx.getOrderRepository().updateCreatedById(orderId, created);
                }
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
        
        UserTrade trade = null;
        if (ExecutionType.TRADE == report.getExecutionType()) {
            trade = report.toUserTrade();
            tx.getTradeRepository().insert(
                    orderId,
                    Long.toString(report.getTradeId()),
                    updated,
                    NostroUtils.writeTradeDocument(trade));
        }
        
        String symbol = order.getCurrencyPair().toString();
        if (!account.getSubscriptions().getOrders().contains(symbol)) {
            LOG.info("Adding new order subscription to account: symbol={}", symbol);
            updateSubscription(ProductSubscription.create().addOrders(order.getCurrencyPair()).build());
        }
        
        return Pair.of(order, trade);
    }

    private void onAccountInfo(OutboundAccountInfoBinanceWebsocketTransaction accountInfo) {
        try {
            List<Balance> list = txFactory.executeAndGet(tx -> saveAccountInfo(tx, accountInfo));
            for(Balance b : list) {
                publisher.publish(b);
            }
        } catch (Throwable th) {
            LOG.error("Error saving account info", th);
        }
    }

    private List<Balance> saveAccountInfo(Transaction tx, OutboundAccountInfoBinanceWebsocketTransaction accountInfo) throws Exception {
        tx.getBalanceRepository().lock();

        long timestamp = accountInfo.getLastUpdateTimestamp();
        List<Balance> updated = new ArrayList<>();
        
        for (BinanceWebsocketBalance bb : accountInfo.getBalances()) {
            Optional<BalanceEntity> o = tx.getBalanceRepository().findLatestByAsset(bb.getCurrency().getCurrencyCode());
            if (o.isPresent()) {
                if (NostroBinanceUtils.updateRequired(o.get(), bb, timestamp)) {
                    updated.add(NostroBinanceUtils.adapt(bb, timestamp));
                }
            } else if (!NostroBinanceUtils.isZeroBalance(bb)) {
                updated.add(NostroBinanceUtils.adapt(bb, timestamp));
            }
        }
        
        LOG.info("Received account info, ts={}, balances={}", new Timestamp(timestamp), updated);

        for(Balance b: updated) {
            tx.getBalanceRepository().insert(NostroBinanceUtils.toEntity(b));
        }
        
        return updated;
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
