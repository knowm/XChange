package info.bitrich.xchangestream.bitfinex;

import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthBalance;
import info.bitrich.xchangestream.core.StreamingAccountService;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import org.knowm.xchange.bitfinex.v1.BitfinexAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lukas Zaoralek on 7.11.17.
 */
public class BitfinexStreamingAccountService implements StreamingAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(BitfinexStreamingAccountService.class);

    private final BitfinexStreamingService service;
    private final BitfinexStreamingTradeService tradeService;

    public BitfinexStreamingAccountService(BitfinexStreamingService service,
                                           BitfinexStreamingTradeService tradeService) {
        this.service = service;
        this.tradeService = tradeService;
    }

    @Override
    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
        if (args.length == 0 || !String.class.isInstance(args[0])) {
            throw new ExchangeException("Specify wallet id to monitor balance stream");
        }
        String walletId = (String) args[0];

        // Ensure we schedule a calculated balance fetch whenever an order change or
        // trade occurs.
        Disposable orders = service.getAuthenticatedOrders()
                .filter(o -> o.getId() != 0)
                .subscribe(o -> {
                    CurrencyPair currencyPair = BitfinexAdapters.adaptCurrencyPair(o.getSymbol().substring(1));
                    service.scheduleCalculatedBalanceFetch(currencyPair.base.getCurrencyCode());
                    service.scheduleCalculatedBalanceFetch(currencyPair.counter.getCurrencyCode());
                });
        Disposable trades = service.getAuthenticatedTrades()
                .filter(o -> o.getId() != 0)
                .subscribe(t -> {
                    CurrencyPair currencyPair = BitfinexAdapters.adaptCurrencyPair(t.getPair().substring(1));
                    service.scheduleCalculatedBalanceFetch(currencyPair.base.getCurrencyCode());
                    service.scheduleCalculatedBalanceFetch(currencyPair.counter.getCurrencyCode());
                });

        // And whenever the connection goes idle
        Disposable idle = service.getIdleAlerts()
                .subscribe(i -> service.scheduleCalculatedBalanceFetch(currency.getCurrencyCode()));

        // Forward all the fully calculated balance to the caller, requesting calculated balances
        // when we get uncalculated ones.
        return getRawAuthenticatedBalances()
                .filter(b -> b.getWalletType().equalsIgnoreCase(walletId))
                .filter(b -> currency.getCurrencyCode().equals(b.getCurrency()))
                .filter(b -> {
                    if (b.getBalanceAvailable() == null) {
                        LOG.debug("Ignoring uncalculated balance on {}-{}, scheduling calculated fetch", walletId, b.getCurrency());
                        service.scheduleCalculatedBalanceFetch(b.getCurrency());
                        return false;
                    }
                    return true;
                })
                .map(BitfinexStreamingAdapters::adaptBalance)
                .doOnComplete(() -> {
                    orders.dispose();
                    trades.dispose();
                    idle.dispose();
                });
    }

    public Observable<BitfinexWebSocketAuthBalance> getRawAuthenticatedBalances() {
        if (!service.isAuthenticated()) {
            throw new ExchangeSecurityException("Not authenticated");
        }
        return service.getAuthenticatedBalances();
    }
}
