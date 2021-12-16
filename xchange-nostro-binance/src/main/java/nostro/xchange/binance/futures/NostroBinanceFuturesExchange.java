package nostro.xchange.binance.futures;

import info.bitrich.xchangestream.binance.BinanceFuturesStreamingExchange;
import info.bitrich.xchangestream.binance.futures.BinanceFuturesStreamingAccountService;
import info.bitrich.xchangestream.binance.futures.BinanceFuturesStreamingTradeService;
import info.bitrich.xchangestream.binance.futures.dto.AccountUpdateBinanceWebsocketTransaction;
import info.bitrich.xchangestream.binance.futures.dto.OrderTradeUpdateBinanceUserTransaction;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import nostro.xchange.binance.futures.sync.BinanceFuturesSyncService;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.AccountDocument;
import nostro.xchange.utils.InstrumentUtils;
import nostro.xchange.utils.NostroStreamingPublisher;
import nostro.xchange.utils.NostroUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.futures.service.BinanceFuturesAccountService;
import org.knowm.xchange.binance.futures.service.BinanceFuturesTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class NostroBinanceFuturesExchange extends BinanceFuturesStreamingExchange {
    private static final Logger LOG = LoggerFactory.getLogger(NostroBinanceFuturesExchange.class);

    private static final String P_SYNC_DELAY = "syncDelay";

    private volatile TransactionFactory txFactory;
    private volatile NostroStreamingPublisher publisher;
    private volatile NostroBinanceFuturesAccountService nostroAccountService;
    private volatile NostroBinanceFuturesTradeService nostroTradeService;
    private volatile BinanceFuturesSyncService syncService;

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
        spec.setExchangeName("NostroBinancefutures");
        spec.setExchangeDescription(inner.getExchangeDescription());
        spec.setShouldLoadRemoteMetaData(inner.isShouldLoadRemoteMetaData());
        AuthUtils.setApiAndSecretKey(spec, "binance");

        spec.setExchangeSpecificParametersItem(P_SYNC_DELAY, "300");

        return spec;
    }

    @Override
    public String getMetaDataFileName(ExchangeSpecification exchangeSpecification) {
        return "binancefutures";
    }

    @Override
    protected void initServices() {
        super.initServices();

        if (isAuthenticated()) {
            try {
                this.publisher = new NostroStreamingPublisher();
                this.txFactory = TransactionFactory.get(exchangeSpecification.getExchangeName(), exchangeSpecification.getUserName());
                this.nostroAccountService = new NostroBinanceFuturesAccountService((BinanceFuturesAccountService) this.accountService, this.txFactory);
                this.nostroTradeService = new NostroBinanceFuturesTradeService((BinanceFuturesTradeService) this.tradeService, this.txFactory);
                this.syncService = new BinanceFuturesSyncService(txFactory, publisher, (BinanceFuturesAccountService) this.accountService, (BinanceFuturesTradeService) this.tradeService, getSyncDelay());
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

                tradeSubscription = ((BinanceFuturesStreamingTradeService) super.getStreamingTradeService())
                        .getRawExecutionReports()
                        .doOnNext(this::onExecutionReport)
                        .doOnSubscribe(r -> LOG.info("Connected to BinanceExecutionReport"))
                        .doOnError(th -> LOG.error("Error while handling BinanceExecutionReport", th))
                        .retry()
                        .subscribe();

                accountSubscription = ((BinanceFuturesStreamingAccountService) super.getStreamingAccountService())
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

    private void onExecutionReport(OrderTradeUpdateBinanceUserTransaction report) {
        try {
            LOG.info("Received execution report, client_id={}, reason={}", report.getOrderTradeUpdate().getClientOrderId(), report.getEventType());
            Pair<Order, UserTrade> pair = nostroTradeService.saveExecutionReport(report);

            tryUpdateSubscription(pair.getLeft());

            publisher.publish(pair.getLeft());
            if (pair.getRight() != null) {
                publisher.publish(pair.getRight());
            }
        } catch (Throwable th) {
            LOG.error("Error saving execution report", th);
        }
    }

    private void onAccountInfo(AccountUpdateBinanceWebsocketTransaction accountInfo) {
        try {
            LOG.info("Received account info ts={} reason={}", new Timestamp(accountInfo.getTransactionTime()), accountInfo.getEventType());
            Pair<List<Balance>, List<OpenPosition>> pair = nostroAccountService.saveAccountInfo(accountInfo);
            pair.getLeft().forEach(publisher::publish);
            pair.getRight().forEach(publisher::publish);
        } catch (Throwable th) {
            LOG.error("Error saving account info", th);
        }
    }

    private void tryUpdateSubscription(Order order) {
        Instrument instrument = order.getInstrument();
        if (!(instrument instanceof FuturesContract)) {
            throw new IllegalStateException(
                    "The instrument of this type is not supported: " + instrument);
        }
        CurrencyPair currencyPair = InstrumentUtils.getCurrencyPair(instrument);
        if (!account.getSubscriptions().getOrders().contains(currencyPair.toString())) {
            LOG.info("Adding new order subscription to account: symbol={}", currencyPair.toString());
            updateSubscription(ProductSubscription.create().addOrders(currencyPair).build());
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
