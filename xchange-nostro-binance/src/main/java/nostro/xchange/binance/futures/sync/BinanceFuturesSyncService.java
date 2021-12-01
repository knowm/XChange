package nostro.xchange.binance.futures.sync;

import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.sync.SyncService;
import nostro.xchange.utils.AccountDocument;
import nostro.xchange.utils.NostroStreamingPublisher;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAccountInformation;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesTrade;
import org.knowm.xchange.binance.futures.service.BinanceFuturesAccountService;
import org.knowm.xchange.binance.futures.service.BinanceFuturesTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class BinanceFuturesSyncService extends SyncService {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceFuturesSyncService.class);

    private final BinanceFuturesAccountService accountService;
    private final BinanceFuturesTradeService tradeService;

    public BinanceFuturesSyncService(TransactionFactory txFactory,
                                     NostroStreamingPublisher publisher,
                                     BinanceFuturesAccountService accountService,
                                     BinanceFuturesTradeService tradeService,
                                     long syncDelay) {
        super(txFactory, publisher, syncDelay);
        this.accountService = accountService;
        this.tradeService = tradeService;
    }

    @Override
    public List<Callable<?>> generateTasks() {
        List<Callable<?>> tasks = new ArrayList<>();
        tasks.add(new BinanceFuturesBalanceSyncTask(this));

        AccountDocument account = NostroUtils.readAccountDocument(getTXFactory().executeAndGet(tx -> tx.getAccountRepository().get()));
        tasks.add(new BinanceFuturesSyncSubscriptionsTask(this, account.getSubscriptions().getOrders()));

        return tasks;
    }

    BinanceFuturesAccountInformation getFuturesAccountInfo() throws IOException {
        try {
            return accountService.getFuturesAccountInfo();
        } catch (Throwable th) {
            LOG.error("Error while querying account information", th);
            throw th;
        }
    }

    List<BinanceFuturesOrder> getOpenOrders(CurrencyPair pair) throws IOException {
        try {
            return tradeService.futuresOpenOrders(pair);
        } catch (Throwable th) {
            LOG.error("Error while querying open orders", th);
            throw th;
        }
    }

    BinanceFuturesOrder getLastOrder(CurrencyPair pair) throws IOException {
        try {
            List<BinanceFuturesOrder> orders = tradeService.futuresAllOrders(pair, 1, null, null, null);
            return !orders.isEmpty() ? orders.get(0) : null;
        } catch (Throwable th) {
            LOG.error("Error while querying orders", th);
            throw th;
        }
    }

    BinanceFuturesOrder getOrder(CurrencyPair pair, long binanceId) throws IOException {
        try {
            return tradeService.futuresOrderStatus(pair, binanceId, null);
        } catch (Throwable th) {
            LOG.error("Error while querying order status", th);
            throw th;
        }
    }

    BinanceFuturesOrder getOrder(CurrencyPair pair, String id) throws IOException {
        try {
            return tradeService.futuresOrderStatus(pair, null, id);
        } catch (Throwable th) {
            String msg = th.getMessage();
            if (msg != null && msg.contains("Order does not exist")) {
                return null;
            }
            LOG.error("Error while querying order status", th);
            throw th;
        }
    }

    BinanceFuturesTrade getFirstTrade(CurrencyPair pair, Long startTime) throws IOException {
        try {
            List<BinanceFuturesTrade> trades = tradeService.myFuturesTrades(pair, 1, startTime, null, null);
            return !trades.isEmpty() ? trades.get(0) : null;
        } catch (Throwable th) {
            LOG.error("Error while querying trades", th);
            throw th;
        }
    }

    List<BinanceFuturesOrder> getOrders(CurrencyPair pair, long fromId, int limit) throws IOException {
        try {
            List<BinanceFuturesOrder> orders = tradeService.futuresAllOrders(pair, limit, null, null, fromId);
            LOG.info("Service returned {} orders", orders.size());
            return orders;
        } catch (Throwable th) {
            LOG.error("Error while querying orders", th);
            throw th;
        }
    }

    List<BinanceFuturesTrade> getTrades(CurrencyPair pair, long fromId, int limit) throws IOException {
        try {
            List<BinanceFuturesTrade> trades = tradeService.myFuturesTrades(pair, limit, null, null, fromId);
            LOG.info("Service returned {} trades", trades.size());
            return trades;
        } catch (Throwable th) {
            LOG.error("Error while querying trades", th);
            throw th;
        }
    }
}

