package info.bitrich.xchangestream.core;

import io.reactivex.Observable;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public interface StreamingAccountService {

    /**
     * Get the changes of account balance for the logged-in user.
     *
     * <p>Emits {@link info.bitrich.xchangestream.service.exception.NotConnectedException} When
     * not connected to the WebSocket API <strong>and</strong> authenticated.</p>
     *
     * @param currency Currency to monitor.
     * @return {@link Observable} that emits {@link Balance} when exchange sends the update.
     */
    default Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
      throw new NotYetImplementedForExchangeException();
    }
}