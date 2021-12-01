package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.NostroBinanceUtils;
import nostro.xchange.binance.futures.NostroBinanceFuturesUtils;
import nostro.xchange.persistence.BalanceEntity;
import nostro.xchange.sync.SyncTask;
import nostro.xchange.utils.InstrumentUtils;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAccountInformation;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAsset;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesPosition;
import org.knowm.xchange.derivative.Derivative;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class BinanceFuturesBalanceSyncTask extends SyncTask<Void, BinanceFuturesSyncService> {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceFuturesBalanceSyncTask.class);

    BinanceFuturesBalanceSyncTask(BinanceFuturesSyncService syncService) {
        super(syncService);
    }

    @Override
    public Void call() throws Exception {
        LOG.info("Starting task");

        Map<String, BalanceEntity> dbBalances = getSyncService().getTXFactory().executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).stream()
                .collect(Collectors.toMap(BalanceEntity::getAsset, b -> b));

        AtomicInteger updated = new AtomicInteger();

        BinanceFuturesAccountInformation account = getSyncService().getFuturesAccountInfo();
        Optional.ofNullable(account.assets).orElseGet(Collections::emptyList).stream()
                .map(BinanceFuturesAdapter::adaptBalance)
                .forEach(balance -> {
                    if (!NostroBinanceUtils.isZeroBalance(balance)) {
                        LOG.debug("balance {}", balance);
                        String key = balance.getCurrency().getCurrencyCode();
                        dbBalances.remove(key);
                        if (sync(balance, key)) {
                            getSyncService().getPublisher().publish(balance);
                            updated.incrementAndGet();
                        }
                    }
                });
        Optional.ofNullable(account.positions).orElseGet(Collections::emptyList).stream()
                .map(BinanceFuturesAdapter::adaptPosition)
                .forEach(position -> {
                    if (!NostroBinanceUtils.isZeroPosition(position)) {
                        LOG.debug("position {}", position);
                        String key = position.getInstrument().toString();
                        dbBalances.remove(key);
                        if (sync(position, key)) {
                            getSyncService().getPublisher().publish(position);
                            updated.incrementAndGet();
                        }
                    }
                });

        // case when db contains non-negative value for the asset
        // exchange response contains 0 for that currency,
        // assuming that balance is zero we need to update DB
        for (Map.Entry<String, BalanceEntity> e : dbBalances.entrySet()) {
            long updateTime = account.updateTime;

            // sometimes for a test xchanges updateTime may be 0
            if (updateTime == 0) {
                LOG.warn("account update time 0 - skip balances sanity");
                break;
            }

            if (e.getValue().getTimestamp().getTime() < updateTime) {
                Instrument instrument = NostroBinanceFuturesUtils.adaptInstrument(e.getKey());
                if (instrument instanceof Derivative) {
                    String key = BinanceAdapters.toSymbol(InstrumentUtils.getCurrencyPair(instrument));
                    final BinanceFuturesPosition futuresPosition = new BinanceFuturesPosition(key, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, false, BigDecimal.ZERO, BigDecimal.ZERO, null, BigDecimal.ZERO, updateTime);
                    OpenPosition position = BinanceFuturesAdapter.adaptPosition(futuresPosition);
                    LOG.debug("insert zero position {}", position);
                    if (sync(position, e.getKey())) {
                        getSyncService().getPublisher().publish(position);
                        updated.incrementAndGet();
                    }
                } else {
                    final BinanceFuturesAsset futuresAsset = new BinanceFuturesAsset(e.getKey(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, false, updateTime);
                    final Balance balance = BinanceFuturesAdapter.adaptBalance(futuresAsset);
                    LOG.debug("insert zero balance {}", balance);
                    if (sync(balance, e.getKey())) {
                        getSyncService().getPublisher().publish(balance);
                        updated.incrementAndGet();
                    }
                }
            }
        }

        LOG.info("Finished task: updated={}", updated);
        return null;
    }

    private boolean sync(Balance balance, String asset) {
        return getSyncService().getTXFactory().executeAndGet(tx -> {
            tx.getBalanceRepository().lock();

            final Optional<BalanceEntity> o = tx.getBalanceRepository().findLatestByAsset(asset);

            if (o.isPresent()) {
                if (!NostroBinanceUtils.updateRequired(o.get(), balance)) {
                    LOG.debug("Skip sync - update not required for balance (asset={}): {}", asset, balance);
                    return false;
                }
                LOG.info("Updating balance (asset={}): {}", asset, balance);
            } else {
                if (NostroBinanceUtils.isZeroBalance(balance)) {
                    LOG.debug("Skip sync - zero balance (asset={}): {}", asset, balance);
                    return false;
                }
                LOG.info("Inserting new balance (asset={}): {}", asset, balance);
            }

            tx.getBalanceRepository().insert(NostroBinanceUtils.toEntity(balance));
            return true;
        });
    }

    private boolean sync(OpenPosition position, String asset) {
        return getSyncService().getTXFactory().executeAndGet(tx -> {
            tx.getBalanceRepository().lock();

            final Optional<BalanceEntity> o = tx.getBalanceRepository().findLatestByAsset(asset);

            if (o.isPresent()) {
                if (!NostroBinanceUtils.updateRequired(o.get(), position)) {
                    LOG.debug("Skip sync - update not required for position (asset={}): {}", asset, position);
                    return false;
                }
                LOG.info("Updating position (asset={}): {}", asset, position);
            } else {
                if (NostroBinanceUtils.isZeroPosition(position)) {
                    LOG.debug("Skip sync - zero position (asset={}): {}", asset, position);
                    return false;
                }
                LOG.info("Inserting new position (asset={}): {}", asset, position);
            }
            tx.getBalanceRepository().insert(NostroBinanceUtils.toEntity(position));
            return true;
        });
    }
}
