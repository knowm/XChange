package org.knowm.xchange.poloniex;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

/** @author walec51 */
public class PoloniexErrorAdapterTest {

  @Test
  public void throwIfErrorResponse_invalidPair() {
    PoloniexException e = new PoloniexException("Invalid currency pair");
    // Poloniex actualy return 200 on this with an error
    e.setHttpStatusCode(200);
    ExchangeException adapted = PoloniexErrorAdapter.adapt(e);
    Assert.assertEquals(CurrencyPairNotValidException.class, adapted.getClass());
  }

  @Test
  public void throwIfErrorResponse_unauthorizedStatus() {
    PoloniexException e = new PoloniexException("Some error msg");
    e.setHttpStatusCode(403);
    ExchangeException adapted = PoloniexErrorAdapter.adapt(e);
    Assert.assertEquals(ExchangeSecurityException.class, adapted.getClass());
  }

  @Test
  public void throwIfErrorResponse_unrecognizedStatusAndErrorMessage() {
    PoloniexException e = new PoloniexException("Some error msg");
    e.setHttpStatusCode(123);
    ExchangeException adapted = PoloniexErrorAdapter.adapt(e);
    Assert.assertEquals(ExchangeException.class, adapted.getClass());
  }
}
