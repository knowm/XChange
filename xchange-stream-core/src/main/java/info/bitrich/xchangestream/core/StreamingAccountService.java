package info.bitrich.xchangestream.core;

import io.reactivex.Observable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;

public interface StreamingAccountService {

  /**
   * Get the changes of account balance for the logged-in user.
   *
   * <p><strong>Warning:</strong> there are currently no guarantees that messages will arrive in
   * order, that messages will not be skipped, or that any initial state message will be sent on
   * connection. Most exchanges have a recommended approach for managing this, involving timestamps,
   * sequence numbers and a separate REST API for re-sync when inconsistencies appear. You should
   * implement these approaches, if required, by combining calls to this method with {@link
   * AccountService#getAccountInfo()}.
   *
   * <p><strong>Emits</strong> {@link
   * info.bitrich.xchangestream.service.exception.NotConnectedException} When not connected to the
   * WebSocket API.
   *
   * <p><strong>Immediately throws</strong> {@link ExchangeSecurityException} if called without
   * authentication details
   *
   * @param currency Currency to monitor.
   * @return {@link Observable} that emits {@link Balance} when exchange sends the update.
   */
  default Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
    throw new NotYetImplementedForExchangeException();
  }
}
