package info.bitrich.xchangestream.binance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.BinanceWebsocketBalance;
import info.bitrich.xchangestream.binance.dto.OutboundAccountInfoBinanceWebsocketTransaction;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.toList;

public class BinanceStreamingAccountService implements StreamingAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(BinanceStreamingAccountService.class);

    private final Subject<OutboundAccountInfoBinanceWebsocketTransaction> accountInfoPublisher = BehaviorSubject.<OutboundAccountInfoBinanceWebsocketTransaction>create().toSerialized();
    private final Subject<BalancesAndTimestamp> manuallyFetchedBalancesPublisher = BehaviorSubject.<BalancesAndTimestamp>create().toSerialized();
    private final Observable<Balance> balanceChanges;

    private volatile Disposable accountInfo;
    private volatile BinanceUserDataStreamingService binanceUserDataStreamingService;
    private final BinanceAccountService accountService;
    private final Runnable onApiCall;

    private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    public BinanceStreamingAccountService(BinanceUserDataStreamingService binanceUserDataStreamingService, BinanceAccountService accountService, Runnable onApiCall) {
        this.binanceUserDataStreamingService = binanceUserDataStreamingService;
        this.accountService = accountService;
        this.onApiCall = onApiCall;

        Date beginningOfTime = new Date(0);
        AtomicReference<Date> lastTimestamp = new AtomicReference<>(beginningOfTime);

        // Post a fresh balance snapshot from REST every time the websocket reconnects since
        // we have no idea what we missed.
        if (binanceUserDataStreamingService != null) {
            binanceUserDataStreamingService.subscribeConnectionSuccess()
                    .subscribe(x -> {
                        lastTimestamp.set(beginningOfTime);
                        postInitialAccountSnapshot();
                    });
        }

        // Combine the websocket data and any manually fetched snapshots into a single
        // stream
        this.balanceChanges = accountInfoPublisher
                .map(a -> new BalancesAndTimestamp(
                        a.getBalances().stream()
                                .map(BinanceWebsocketBalance::toBalance)
                                .collect(toList()),
                        a.getEventTime())
                )
                .mergeWith(manuallyFetchedBalancesPublisher)
                .filter(a -> lastTimestamp.getAndAccumulate(a.timestamp, (x, y) -> x.before(y) ? y : x)
                        .before(a.timestamp))
                .flatMap(a -> Observable.fromIterable(a.balances))
                .share();
    }

    public Observable<OutboundAccountInfoBinanceWebsocketTransaction> getRawAccountInfo() {
        checkConnected();
        return accountInfoPublisher;
    }

    public Observable<Balance> getBalanceChanges() {
        checkConnected();
        return balanceChanges;
    }

    private void checkConnected() {
        if (binanceUserDataStreamingService == null || !binanceUserDataStreamingService.isSocketOpen())
            throw new ExchangeSecurityException("Not authenticated");
    }

    @Override
    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
        return getBalanceChanges().filter(t -> t.getCurrency().equals(currency));
    }

    /**
     * Registers subsriptions with the streaming service for the given products.
     *
     * As we receive messages as soon as the connection is open, we need to register subscribers to handle these before the
     * first messages arrive.
     */
    public void openSubscriptions() {
        if (binanceUserDataStreamingService != null) {
            accountInfo = binanceUserDataStreamingService
                .subscribeChannel(BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.OUTBOUND_ACCOUNT_INFO)
                .map(this::accountInfo)
                .subscribe(accountInfoPublisher::onNext);
        }
    }

    /**
     * Pushes the current account balances to the stream any time the socket
     * (re)connects.
     */
    private void postInitialAccountSnapshot() {
        try {
            LOG.info("Fetching initial balance snapshot");
            onApiCall.run();
            AccountInfo info = accountService.getAccountInfo();
            BalancesAndTimestamp balancesAndTimestamp = new BalancesAndTimestamp(
                    info.getWallet().getBalances().values(),
                    info.getTimestamp());
            manuallyFetchedBalancesPublisher.onNext(balancesAndTimestamp);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * User data subscriptions may have to persist across multiple socket connections to different
     * URLs and therefore must act in a publisher fashion so that subscribers get an uninterrupted
     * stream.
     */
    void setUserDataStreamingService(BinanceUserDataStreamingService binanceUserDataStreamingService) {
        if (accountInfo != null && !accountInfo.isDisposed())
            accountInfo.dispose();
        this.binanceUserDataStreamingService = binanceUserDataStreamingService;
        openSubscriptions();
    }

    private OutboundAccountInfoBinanceWebsocketTransaction accountInfo(JsonNode json) {
        try {
            return mapper.treeToValue(json, OutboundAccountInfoBinanceWebsocketTransaction.class);
        } catch (Exception e) {
            throw new ExchangeException("Unable to parse account info", e);
        }
    }

    private static final class BalancesAndTimestamp {
        final Collection<Balance> balances;
        final Date timestamp;

        BalancesAndTimestamp(Collection<Balance> balances, Date timestamp) {
            this.balances = balances;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "BalancesAndTimestamp [balances=" + balances + ", timestamp=" + timestamp + "]";
        }
    }
}