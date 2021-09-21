package nostro.xchange.binance.sync;

import nostro.xchange.binance.BinanceNostroUtils;
import nostro.xchange.persistence.BalanceEntity;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.dto.account.BinanceBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class BalanceSyncTask implements Callable<Void> {
    private static final Logger LOG = LoggerFactory.getLogger(BalanceSyncTask.class);

    private final BinanceSyncService syncService;

    public BalanceSyncTask(BinanceSyncService syncService) {
        this.syncService = syncService;
    }

    @Override
    public Void call() throws Exception {
        LOG.info("Starting BalanceSyncTask");

        Map<String, BalanceEntity> dbBalances = syncService.txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).stream()
                .collect(Collectors.toMap(b -> b.getAsset(), b -> b));

        int updated = 0;
        BinanceAccountInformation account = syncService.getAccountInformation();
        for (BinanceBalance binanceBalance : account.balances) {
            if (!BinanceNostroUtils.isZeroBalance(binanceBalance)) {
                dbBalances.remove(binanceBalance.getCurrency().getCurrencyCode());

                if (syncBalance(binanceBalance, account.updateTime)) {
                    syncService.publisher.publish(BinanceNostroUtils.adapt(binanceBalance, account.updateTime));
                    ++updated;
                }
            }
        }
        
        for(Map.Entry<String, BalanceEntity> e : dbBalances.entrySet()) {
            if (e.getValue().getTimestamp().getTime() < account.updateTime) {
                BinanceBalance binanceBalance = new BinanceBalance(e.getKey(), BigDecimal.ZERO, BigDecimal.ZERO);
                if (syncBalance(binanceBalance, account.updateTime)) {
                    syncService.publisher.publish(BinanceNostroUtils.adapt(binanceBalance, account.updateTime));
                    ++updated;
                }
            }
        }

        LOG.info("Finished BalanceSyncTask: updated={}", updated);
        return null;
    }

    private boolean syncBalance(BinanceBalance binanceBalance, long updateTime) {
        return syncService.txFactory.executeAndGet(tx -> {
            tx.getBalanceRepository().lock();
            
            String asset = binanceBalance.getCurrency().getCurrencyCode();
            Optional<BalanceEntity> o = tx.getBalanceRepository().findLatestByAsset(asset);
            if (o.isPresent()) {
                if (!BinanceNostroUtils.updateRequired(o.get(), binanceBalance, updateTime)) {
                    return false;
                }
                LOG.info("Updating balance (asset={}): {}", asset, binanceBalance);
            } else {
                if (BinanceNostroUtils.isZeroBalance(binanceBalance)) {
                    return false;
                }
                LOG.info("Inserting new balance (asset={}): {}", asset, binanceBalance);
            }
            tx.getBalanceRepository().insert(BinanceNostroUtils.toEntity(binanceBalance, updateTime));
            return true;
        });
    }
}
