package info.bitrich.xchangestream.bitfinex;

import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthBalance;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.service.exception.NotConnectedException;

import io.reactivex.Observable;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * Created by Lukas Zaoralek on 7.11.17.
 */
public class BitfinexStreamingAccountService implements StreamingAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(BitfinexStreamingAccountService.class);

    private final BitfinexStreamingService service;

    public BitfinexStreamingAccountService(BitfinexStreamingService service) {
        this.service = service;
    }

    @Override
    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
        if (args.length == 0 || !String.class.isInstance(args[0])) {
            throw new ExchangeException("Specify wallet id to monitor balance stream");
        }
        String walletId = (String) args[0];
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
                .map(BitfinexStreamingAdapters::adaptBalance);
    }

    public Observable<BitfinexWebSocketAuthBalance> getRawAuthenticatedBalances() {
        return withAuthenticatedService(BitfinexStreamingService::getAuthenticatedBalances);
    }

    private <T> Observable<T> withAuthenticatedService(Function<BitfinexStreamingService, Observable<T>> serviceConsumer) {
        if (!service.isAuthenticated()) {
            return Observable.error(new NotConnectedException());
        }
        return serviceConsumer.apply(service);
    }
}
