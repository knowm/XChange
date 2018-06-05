package org.knowm.xchange.poloniex;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.poloniex.dto.PoloniexException;

/** @author walec51 */
public class PoloniexErrorAdapterTest {

  @Test
  public void throwIfErrorResponse_invalidPair() {
    PoloniexException e = new PoloniexException();
    e.setError("Invalid currency pair");
    // Poloniex actualy returns 200 on this with an error
    e.setHttpStatusCode(200);
    ExchangeException adapted = PoloniexErrorAdapter.adapt(e);
    assertThat(adapted).isExactlyInstanceOf(CurrencyPairNotValidException.class);
  }

  @Test
  public void throwIfErrorResponse_unauthorizedStatus() {
    PoloniexException e = new PoloniexException();
    e.setError("Some error msg");
    e.setHttpStatusCode(403);
    ExchangeException adapted = PoloniexErrorAdapter.adapt(e);
    assertThat(adapted).isExactlyInstanceOf(ExchangeSecurityException.class);
  }

  @Test
  public void throwIfErrorResponse_unrecognizedStatusAndErrorMessage() {
    PoloniexException e = new PoloniexException();
    e.setError("Some error msg");
    e.setHttpStatusCode(123);
    ExchangeException adapted = PoloniexErrorAdapter.adapt(e);
    assertThat(adapted).isExactlyInstanceOf(ExchangeException.class);
  }
}
